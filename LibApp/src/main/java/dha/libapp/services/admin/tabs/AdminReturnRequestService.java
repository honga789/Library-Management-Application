package dha.libapp.services.admin.tabs;

import dha.libapp.cache.Cache;
import dha.libapp.cache.members.BorrowedTabCache;
import dha.libapp.cache.members.ReturnedTabCache;
import dha.libapp.controllers.admin.tabs.AdminApproveRequestController;
import dha.libapp.controllers.admin.tabs.AdminReturnRequestController;
import dha.libapp.controllers.members.tabs.MemberReturnedTabController;
import dha.libapp.dao.BookDAO;
import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AdminReturnRequestService {
    public static void renderReturnedBooks() {
        AdminReturnRequestController.getInstance().setReturnedListViewVisible(true);

        BorrowRecordSyncDAO.getAllBorrowRecordsByStatusSync(BorrowStatus.BORROWED, new DAOExecuteCallback<List<BorrowRecord>>() {
            @Override
            public void onSuccess(List<BorrowRecord> result) {
                AdminReturnRequestController.getInstance().renderReturnedBooks(result);

                AdminReturnRequestController.getInstance().setLoadingPaneVisible(false);
                AdminReturnRequestController.getInstance().setReturnedListViewVisible(true);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException();
            }
        });
    }

    public static class BorrowInfo {
        public BorrowRecord borrowRecord;
        public User user;
        public Book book;
    }

    public static void getInfoBorrow(BorrowRecord borrowRecord, DAOExecuteCallback<AdminApproveRequestService.BorrowInfo> callback) {
        AdminApproveRequestService.BorrowInfo borrowInfo = new AdminApproveRequestService.BorrowInfo();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Book book = BookDAO.getBookById(borrowRecord.getBookId());
                if (book == null) {
                    book = BookDAO.getDeletedBookById(borrowRecord.getBookId());
                }
                User user = UserDAO.getUserById(borrowRecord.getUserId());
                if (user == null) {
                    user = DeletedUserDAO.getDeletedUserById(borrowRecord.getUserId());
                }
                borrowInfo.book = book;
                borrowInfo.user = user;
                return null;
            }

            @Override
            protected void succeeded() {
                callback.onSuccess(borrowInfo);
            }

            @Override
            protected void failed() {
                callback.onError(new RuntimeException("Error when load borrow info"));
            }
        };
        new Thread(task).start();
    }
}
