package dha.libapp.syncdao;

import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.Date;
import java.util.List;

public class BorrowRecordSyncDAO {
    public static void getAllBorrowRecordsSync(DAOExecuteCallback<List<BorrowRecord>> callback) {
        Task<List<BorrowRecord>> task = new Task<List<BorrowRecord>>() {

            @Override
            protected List<BorrowRecord> call() throws Exception {
                return BorrowRecordDAO.getAllBorrowRecords();
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getBorrowRecordByIdSync(int borrow_id, DAOExecuteCallback<BorrowRecord> callback) {
        Task<BorrowRecord> task = new Task<>() {

            @Override
            protected BorrowRecord call() throws Exception {
                return BorrowRecordDAO.getBorrowRecordById(borrow_id);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getAllBorrowRecordsByUserIdSync(int user_id, DAOExecuteCallback<List<BorrowRecord>> callback) {
        Task<List<BorrowRecord>> task = new Task<>() {

            @Override
            protected List<BorrowRecord> call() throws Exception {
                return BorrowRecordDAO.getAllBorrowRecordsByUserId(user_id);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getAllBorrowRecordsByBookIdSync(int book_id, DAOExecuteCallback<List<BorrowRecord>> callback) {
        Task<List<BorrowRecord>> task = new Task<>() {

            @Override
            protected List<BorrowRecord> call() throws Exception {
                return BorrowRecordDAO.getAllBorrowRecordsByBookId(book_id);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getAllBorrowRecordsByStatusSync(BorrowStatus status, DAOExecuteCallback<List<BorrowRecord>> callback) {
        Task<List<BorrowRecord>> task = new Task<>() {

            @Override
            protected List<BorrowRecord> call() throws Exception {
                return BorrowRecordDAO.getAllBorrowRecordsByStatus(status);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getAllBorrowRecordsByUserIdAndStatusSync(int user_id, BorrowStatus status,
                                                                DAOExecuteCallback<List<BorrowRecord>> callback) {
        Task<List<BorrowRecord>> task = new Task<>() {

            @Override
            protected List<BorrowRecord> call() throws Exception {
                return BorrowRecordDAO.getAllBorrowRecordsByUserIdAndStatus(user_id, status);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void addNewBorrowRecordSync(int userId, int bookId, Date borrowDate, Date dueDate,
                                              BorrowStatus status, Date returnDate, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BorrowRecordDAO.addNewBorrowRecord(userId, bookId, borrowDate, dueDate, status, returnDate);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void updateBorrowRecordSync(int borrow_id, Date borrowDate, Date dueDate,
                                              BorrowStatus status, Date returnDate, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BorrowRecordDAO.updateBorrowRecord(borrow_id, borrowDate, dueDate, status, returnDate);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void updateBorrowRecordSync(BorrowRecord borrowRecord, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BorrowRecordDAO.updateBorrowRecord(borrowRecord);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }
}
