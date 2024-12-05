package dha.libapp.services.admin;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.util.Date;

public class BorrowService {
    private BorrowService() {}
    private final static BorrowService instance = new BorrowService();
    public static BorrowService getInstance() {
        return instance;
    }

    public void addBorrowRecord(int userId, int bookId, Date borrowDate, Date dueDate,
                                 BorrowStatus status, Date returnDate) throws Exception {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                BorrowRecordDAO.addNewBorrowRecord(userId, bookId, borrowDate, dueDate, status, returnDate);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("add record success");
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("add record failed");
            }
        };
        new Thread(task).start();
    }

    public void updateBorrowRecord(BorrowRecord borrowRecord) throws Exception {
        DAOUpdateCallback callback = new DAOUpdateCallback() {

            @Override
            public void onSuccess() {
                System.out.println("update record success");
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException("error on update record",e);
            }
        };
        BorrowRecordSyncDAO.updateBorrowRecordSync(borrowRecord, callback);
    }

    public void acceptBorrow(BorrowRecord borrowRecord, DAOUpdateCallback callback) {

        Task<Boolean> bookTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (borrowRecord == null) {
                    return false;
                }

                Book book = BookDAO.getBookById(borrowRecord.getBookId());
                if (book == null) {
                    callback.onError(new RuntimeException("Book is null"));
                    return false;
                }

                if (book.getQuantity() < 1) {
                    callback.onError(new RuntimeException("Book quantity < 1"));
                    return false;
                }

                borrowRecord.setStatus(BorrowStatus.BORROWED);
                BorrowRecordDAO.updateBorrowRecord(borrowRecord);
                return true;
            }

            @Override
            protected void succeeded() {
                if (getValue()) {
                    callback.onSuccess();
                    return;
                }
                callback.onError(new RuntimeException("Accept borrow successfully"));
            }

            @Override
            protected void failed() {
                callback.onError(new RuntimeException("Accept borrow failed"));
            }
        };

        new Thread(bookTask).start();

    }

}
