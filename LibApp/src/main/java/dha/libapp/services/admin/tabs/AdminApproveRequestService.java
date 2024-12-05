package dha.libapp.services.admin.tabs;

import dha.libapp.controllers.admin.tabs.AdminApproveRequestController;
import dha.libapp.controllers.admin.tabs.AdminReturnRequestController;
import dha.libapp.dao.BookDAO;
import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AdminApproveRequestService {
    public static void renderApproveBooks() {
        AdminApproveRequestController.getInstance().setApproveListViewVisible(true);

        BorrowRecordSyncDAO.getAllBorrowRecordsByStatusSync(BorrowStatus.PENDING, new DAOExecuteCallback<List<BorrowRecord>>() {
            @Override
            public void onSuccess(List<BorrowRecord> result) {
                AdminApproveRequestController.getInstance().renderApproveBooks(result);

                AdminApproveRequestController.getInstance().setLoadingPaneVisible(false);
                AdminApproveRequestController.getInstance().setApproveListViewVisible(true);
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

    public static void getInfoBorrow(BorrowRecord borrowRecord, DAOExecuteCallback<BorrowInfo> callback) {
        BorrowInfo borrowInfo = new BorrowInfo();

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
