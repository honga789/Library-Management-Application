package daoTest;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.models.BorrowRecord;

import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowRecordDAOTest {

    @Test
    public void testSearchBorrowRecordsByUsernameAndStatus() throws InterruptedException {
        List<BorrowRecord> records = BorrowRecordDAO.searchBorrowRecordsByUsernameAndStatus(
                "j",
                BorrowStatus.BORROWED
        );
        assertNotNull(records);
        assertFalse(records.isEmpty());
        System.out.println(records.size());
        System.out.println(records);
    }
}
