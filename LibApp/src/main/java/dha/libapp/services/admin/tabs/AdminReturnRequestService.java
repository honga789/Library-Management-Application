package dha.libapp.services.admin.tabs;

import dha.libapp.controllers.admin.tabs.AdminReturnRequestController;
import dha.libapp.dao.BookDAO;
import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import javafx.concurrent.Task;

import java.util.List;

/**
 * Service class responsible for handling the return requests in the admin panel.
 */
public class AdminReturnRequestService {

    /**
     * Renders the list of books that have been returned, based on borrow records
     * with the "BORROWED" status.
     * It fetches borrow records and updates the view accordingly.
     */
    public static void renderReturnedBooks() {
        AdminReturnRequestController.getInstance().setReturnedListViewVisible(true);

        BorrowRecordSyncDAO.getAllBorrowRecordsByStatusSync(BorrowStatus.BORROWED, new DAOExecuteCallback<>() {
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

    /**
     * Retrieves detailed information about a borrow record, including the associated
     * book and user information.
     * It fetches the data asynchronously and calls the provided callback with the results.
     *
     * @param borrowRecord The borrow record to retrieve information for.
     * @param callback The callback to be called with the fetched borrow info.
     */
    public static void getInfoBorrow(BorrowRecord borrowRecord, DAOExecuteCallback<AdminApproveRequestService.BorrowInfo> callback) {
        AdminApproveRequestService.BorrowInfo borrowInfo = new AdminApproveRequestService.BorrowInfo();

        Task<Void> task = new Task<>() {
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
