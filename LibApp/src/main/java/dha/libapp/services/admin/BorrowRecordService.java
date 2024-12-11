package dha.libapp.services.admin;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Service class responsible for managing borrow records, including adding, searching, accepting, denying, and returning borrow records.
 */
public class BorrowRecordService {
    private static BorrowRecordService instance = new BorrowRecordService();

    private BorrowRecordService() {
    }

    /**
     * Returns the singleton instance of the BorrowRecordService.
     *
     * @return The singleton instance of BorrowRecordService.
     */
    public static BorrowRecordService getInstance() {
        if (instance == null) {
            instance = new BorrowRecordService();
        }
        return instance;
    }

    /**
     * Adds a new borrow record for a user and a book.
     *
     * @param userId   The user ID of the person borrowing the book.
     * @param bookId   The book ID of the borrowed book.
     * @param status   The status of the borrow record.
     * @param callback The callback to be invoked when the operation completes.
     */
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

    /**
     * Searches for borrow records based on the username and borrow status.
     *
     * @param username The username of the borrower.
     * @param status   The borrow status (e.g., borrowed, returned, etc.).
     * @param callback The callback to be invoked when the operation completes.
     */
    public void getSearchBorrowRecordsByUsernameAndStatus(String username, BorrowStatus status,
                                                          DAOExecuteCallback<List<BorrowRecord>> callback) {
        BorrowRecordSyncDAO.searchBorrowRecordsByUsernameAndStatusSync(username, status,
                new DAOExecuteCallback<>() {
                    @Override
                    public void onSuccess(List<BorrowRecord> result) {
                        System.out.println("search borrow records successfully");
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("failed to search borrow records");
                        callback.onError(e);
                    }
                });
    }

    /**
     * Accepts a borrow request and updates the borrow record, reducing the book quantity.
     *
     * @param borrowRecord The borrow record to be accepted.
     * @param callback     The callback to be invoked when the operation completes.
     */
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

        Task<Boolean> bookTask = new Task<>() {
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

                book.setQuantity(book.getQuantity() - 1);
                BookDAO.updateBook(book);
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

    /**
     * Denies a borrow request and updates the borrow record status to canceled.
     *
     * @param borrowRecord The borrow record to be denied.
     * @param callback     The callback to be invoked when the operation completes.
     */
    public void deniedBorrow(BorrowRecord borrowRecord, DAOUpdateCallback callback) {
        if (borrowRecord == null) {
            callback.onError(new RuntimeException("borrow record is null"));
            return;
        }

        borrowRecord.setStatus(BorrowStatus.CANCELED);
        BorrowRecordSyncDAO.updateBorrowRecordSync(borrowRecord, new DAOUpdateCallback() {

            @Override
            public void onSuccess() {
                System.out.println("denied borrow record successfully");
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("failed to denied borrow record");
                callback.onError(new RuntimeException("failed to denied borrow record"));
            }
        });
    }

    /**
     * Marks a borrow record as returned, updates the status, and adjusts the book quantity.
     *
     * @param borrowRecord The borrow record to be marked as returned.
     * @param callback     The callback to be invoked when the operation completes.
     */
    public void returnedBorrow(BorrowRecord borrowRecord, DAOUpdateCallback callback) {
        if (borrowRecord == null) {
            callback.onError(new RuntimeException("borrow record is null"));
            return;
        }

        borrowRecord.setStatus(BorrowStatus.RETURNED);
        borrowRecord.setReturnDate(new Date());
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                BorrowRecordDAO.updateBorrowRecord(borrowRecord);
                Book book = BookDAO.getBookById(borrowRecord.getBookId());

                if (book == null) {
                    book = BookDAO.getDeletedBookById(borrowRecord.getBookId());
                    if (book == null) {
                        callback.onError(new RuntimeException("Book never exist"));
                        return null;
                    }

                    book.setQuantity(1);
                } else {
                    book.setQuantity(book.getQuantity() + 1);
                }

                BookDAO.updateBook(book);
                return null;
            }

            @Override
            protected void failed() {
                callback.onError(new RuntimeException("Returned borrow failed"));
            }

            @Override
            protected void succeeded() {
                callback.onSuccess();
            }
        };
        new Thread(task).start();
    }
}
