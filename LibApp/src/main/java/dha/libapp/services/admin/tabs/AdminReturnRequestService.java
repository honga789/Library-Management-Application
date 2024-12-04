package dha.libapp.services.admin.tabs;

import dha.libapp.cache.Cache;
import dha.libapp.cache.members.BorrowedTabCache;
import dha.libapp.cache.members.ReturnedTabCache;
import dha.libapp.controllers.admin.tabs.AdminReturnRequestController;
import dha.libapp.controllers.members.tabs.MemberReturnedTabController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AdminReturnRequestService {
    public static void renderReturnedBooks() {
        AdminReturnRequestController.getInstance().setReturnedListViewVisible(true);

        BorrowRecordSyncDAO.getAllBorrowRecordsByStatusSync(BorrowStatus.RETURNED, new DAOExecuteCallback<List<BorrowRecord>>() {
            @Override
            public void onSuccess(List<BorrowRecord> result) {
                List<CompletableFuture<Book>> bookFutures = result.stream()
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

                            AdminReturnRequestController.getInstance().renderReturnedBooks(books);

                            AdminReturnRequestController.getInstance().setLoadingPaneVisible(false);
                            AdminReturnRequestController.getInstance().setReturnedListViewVisible(true);
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
