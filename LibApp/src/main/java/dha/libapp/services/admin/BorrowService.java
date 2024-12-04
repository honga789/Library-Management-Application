package dha.libapp.services.admin;

import dha.libapp.dao.BorrowRecordDAO;
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

}
