package dha.libapp.services.members.tabs;

import dha.libapp.cache.Cache;
import dha.libapp.cache.members.ReturnedTabCache;
import dha.libapp.controllers.members.tabs.MemberReturnedTabController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MemberReturnedTabService {
    public static void renderReturnedBooks() {
        MemberReturnedTabController.getInstance().setReturnedListViewVisible(true);

        Cache<List<Book>> returnedCache = ReturnedTabCache.getInstance().getReturnedBookList();

        if (returnedCache.isSaved()) {
            MemberReturnedTabController.getInstance().setLoadingPaneVisible(false);
            MemberReturnedTabController.getInstance().renderReturnedBooks(returnedCache.getData());
        } else {
            BorrowRecordSyncDAO.getAllBorrowRecordsByUserIdSync(SessionService.getInstance().getUser().getUserId(),
                    new DAOExecuteCallback<List<BorrowRecord>>() {

                        @Override
                        public void onSuccess(List<BorrowRecord> result) {
                            List<BorrowRecord> returnedRecords = result.stream()
                                    .filter(record -> record.getStatus().toString().equals("RETURNED"))
                                    .toList();

                            List<CompletableFuture<Book>> bookFutures = returnedRecords.stream()
                                    .map(record -> {
                                        CompletableFuture<Book> future = new CompletableFuture<>();
                                        BookSyncDAO.getBookByIdSync(record.getBookId(), new DAOExecuteCallback<Book>() {
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

                                        MemberReturnedTabController.getInstance().renderReturnedBooks(books);
                                        returnedCache.setData(books);

                                        MemberReturnedTabController.getInstance().setLoadingPaneVisible(false);
                                        MemberReturnedTabController.getInstance().setReturnedListViewVisible(true);
                                    })
                                    .exceptionally(ex -> {
                                        ex.printStackTrace();
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
