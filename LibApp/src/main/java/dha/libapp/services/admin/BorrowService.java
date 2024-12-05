package dha.libapp.services.admin;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.Calendar;
import java.util.Date;

public class BorrowService {
    private static BorrowService instance;

    private BorrowService() {}

    public static BorrowService getInstance() {
        if (instance == null) {
            BorrowService instance = new BorrowService();
        }
        return instance;
    }

    public void addBorrowRecord(int userId, int bookId, BorrowStatus status, DAOUpdateCallback callback) {

        Date borrowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DATE, 30);
        Date dueDate = calendar.getTime();

        BorrowRecordSyncDAO.addNewBorrowRecordSync(userId, bookId, borrowDate, dueDate, status,
                null, new DAOUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("added new borrow record successfully");
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("failed to add new borrow record");
                        callback.onError(new RuntimeException("failed to add new borrow record"));
                    }
                });
    }

    public void acceptBorrow(BorrowRecord borrowRecord, DAOUpdateCallback callback) {
        if (borrowRecord == null) {
            callback.onError(new RuntimeException("borrow record is null"));
            return;
        }

        borrowRecord.setBorrowDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowRecord.getBorrowDate());
        calendar.add(Calendar.DATE, 30);
        borrowRecord.setDueDate(calendar.getTime());
        borrowRecord.setStatus(BorrowStatus.BORROWED);
        borrowRecord.setReturnDate(null);

        Task<Boolean> bookTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Book book = BookDAO.getBookById(borrowRecord.getBookId());
                if (book == null) {
                    callback.onError(new RuntimeException("Run out of book"));
                    return false;
                }

                if (book.getQuantity() < 1) {
                    callback.onError(new RuntimeException("Run out of book"));
                    return false;
                }

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
