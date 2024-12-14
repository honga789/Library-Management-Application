package dha.libapp.services.members.tabs;

import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.CacheItem;
import dha.libapp.cache.members.PendingTabCache;
import dha.libapp.controllers.members.tabs.MemberPendingTabController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service class responsible for rendering the pending books tab for a member.
 * It provides functionality for rendering books that are pending for approval or return.
 */
public class MemberPendingTabService {

    /**
     * Renders the list of books that are pending for the currently logged-in member.
     * If the pending books are cached, it uses the cache; otherwise, it fetches the pending borrow records from the database.
     * <p>
     * The service retrieves borrow records with a status of "PENDING" and displays the related books.
     */
    public static void renderPendingBooks() {
        MemberPendingTabController.getInstance().setPendingListViewVisible(true);

//        CacheItem<List<Book>> pendingCache = PendingTabCache.getInstance().getPendingBookList();
        CacheItem<List<Book>> pendingCache = CacheFactory.getCache(PendingTabCache.class).getPendingBookList();

        if (pendingCache.isSaved()) {
            MemberPendingTabController.getInstance().setLoadingPaneVisible(false);
            MemberPendingTabController.getInstance().renderPendingBooks(pendingCache.getData());
        } else {
            BorrowRecordSyncDAO.getAllBorrowRecordsByUserIdSync(SessionService.getInstance().getUser().getUserId(),
                    new DAOExecuteCallback<>() {

                        @Override
                        public void onSuccess(List<BorrowRecord> result) {
                            List<BorrowRecord> borrowedRecords = result.stream()
                                    .filter(record -> record.getStatus().toString().equals("PENDING"))
                                    .toList();

                            List<CompletableFuture<Book>> bookFutures = borrowedRecords.stream()
                                    .map(record -> {
                                        CompletableFuture<Book> future = new CompletableFuture<>();
                                        BookSyncDAO.getBookOrDeletedBookByIdSync(record.getBookId(), new DAOExecuteCallback<>() {
                                            @Override
                                            public void onSuccess(Book bookResult) {
                                                future.complete(bookResult);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                future.completeExceptionally(e);
                                            }
                                        });
                                        return future;
                                    })
                                    .toList();

                            CompletableFuture.allOf(bookFutures.toArray(new CompletableFuture[0]))
                                    .thenRun(() -> {
                                        List<Book> books = bookFutures.stream()
                                                .map(CompletableFuture::join)
                                                .collect(Collectors.toList());

                                        MemberPendingTabController.getInstance().renderPendingBooks(books);
                                        pendingCache.setData(books);

                                        MemberPendingTabController.getInstance().setLoadingPaneVisible(false);
                                        MemberPendingTabController.getInstance().setPendingListViewVisible(true);
                                    })
                                    .exceptionally(ex -> {
                                        System.out.println(ex.getMessage());
                                        return null;
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            throw new RuntimeException();
                        }
                    });
        }
    }
}
