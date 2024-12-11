package dha.libapp.services.members.tabs;

import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.CacheItem;
import dha.libapp.cache.members.BorrowedTabCache;
import dha.libapp.controllers.members.tabs.MemberBorrowedTabController;
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
 * Service class responsible for managing borrowed books and rendering the list of borrowed books for a member.
 * It interacts with the cache, retrieves borrow records, and fetches related book details.
 */
public class MemberBorrowedTabService {

    /**
     * Renders the list of borrowed books for the currently logged-in member.
     * If the borrowed books are cached, it uses the cache; otherwise, it fetches borrow records from the database.
     * <p>
     * The service performs asynchronous fetching of book data using `CompletableFuture` and renders the results
     * when all book details have been retrieved.
     */
    public static void renderBorrowedBooks() {
        MemberBorrowedTabController.getInstance().setBorrowedListViewVisible(true);

//        CacheItem<List<Book>> borrowedCache = BorrowedTabCache.getInstance().getBorrowedBookList();
        CacheItem<List<Book>> borrowedCache = CacheFactory.getCache(BorrowedTabCache.class).getBorrowedBookList();

        if (borrowedCache.isSaved()) {
            MemberBorrowedTabController.getInstance().setLoadingPaneVisible(false);
            MemberBorrowedTabController.getInstance().renderBorrowedBooks(borrowedCache.getData());
        } else {
            BorrowRecordSyncDAO.getAllBorrowRecordsByUserIdSync(SessionService.getInstance().getUser().getUserId(),
                    new DAOExecuteCallback<>() {

                        @Override
                        public void onSuccess(List<BorrowRecord> result) {
                            List<BorrowRecord> borrowedRecords = result.stream()
                                    .filter(record -> record.getStatus().toString().equals("BORROWED"))
                                    .toList();

                            List<CompletableFuture<Book>> bookFutures = borrowedRecords.stream()
                                    .map(record -> {
                                        CompletableFuture<Book> future = new CompletableFuture<>();
                                        BookSyncDAO.getBookByIdSync(record.getBookId(), new DAOExecuteCallback<>() {
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

                                        MemberBorrowedTabController.getInstance().renderBorrowedBooks(books);
                                        borrowedCache.setData(books);

                                        MemberBorrowedTabController.getInstance().setLoadingPaneVisible(false);
                                        MemberBorrowedTabController.getInstance().setBorrowedListViewVisible(true);
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
