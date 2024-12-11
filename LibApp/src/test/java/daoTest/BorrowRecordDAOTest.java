package daoTest;

import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.BorrowRecord;

import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowRecordDAOTest {
    static List<BorrowRecord> borrowRecordList = new ArrayList<>();
    static int index = 0;

    @BeforeAll
    static void setupBeforeClass() throws Exception {
        System.out.println("BorrowRecordDAOTest setupBeforeClass - Start");

        borrowRecordList = BorrowRecordDAO.getAllBorrowRecords();
        assertNotNull(borrowRecordList);
        assertFalse(borrowRecordList.isEmpty());
        System.out.println("borrowRecordList: " + borrowRecordList.subList(0, Math.min(borrowRecordList.size(), 5)));

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }

    @BeforeEach
    void setup() throws Exception {
        assertNotNull(borrowRecordList);
        assertFalse(borrowRecordList.isEmpty());
    }

    @Test
    @Order(1)
    void getBorrowRecordById() {
        System.out.println("\nTest getBorrowRecordById - Start");

        index = (int) (Math.random() * borrowRecordList.size());
        BorrowRecord borrowRecord1 = borrowRecordList.get(index);
        BorrowRecord borrowRecord2 = BorrowRecordDAO.getBorrowRecordById(borrowRecord1.getBorrowId());
        assertEquals(borrowRecord1, borrowRecord2);

        System.out.println("Test getBorrowRecordById - End");
    }

    @Test
    @Order(2)
    void getAllBorrowRecordsByUserId() {
        System.out.println("\nTest getAllBorrowRecordsByUserId - Start");

        index = (int) (Math.random() * borrowRecordList.size());
        int userId = borrowRecordList.get(index).getUserId();
        List<BorrowRecord> borrowRecordList1 = BorrowRecordDAO.getAllBorrowRecordsByUserId(userId);
        assertNotNull(borrowRecordList1);
        assertFalse(borrowRecordList1.isEmpty());
        System.out.println("userId: " + userId);
        System.out.println(borrowRecordList1.subList(0, Math.min(borrowRecordList1.size(), 5)));

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }

    @Test
    @Order(3)
    void getAllBorrowRecordsByStatus() {
        System.out.println("\nTest getAllBorrowRecordsByStatus - Start");

        index = (int) (Math.random() * borrowRecordList.size());
        BorrowStatus status = borrowRecordList.get(index).getStatus();
        List<BorrowRecord> borrowRecordList1 = BorrowRecordDAO.getAllBorrowRecordsByStatus(status);
        assertNotNull(borrowRecordList1);
        assertFalse(borrowRecordList1.isEmpty());
        System.out.println("status: " + status);
        System.out.println(borrowRecordList1.subList(0, Math.min(borrowRecordList1.size(), 5)));

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }

    @Test
    @Order(4)
    void addNewBorrowRecord() {
        System.out.println("\nTest addNewBorrowRecord - Start");

        index = (int) (Math.random() * borrowRecordList.size());
        BorrowRecord borrowRecord1 = borrowRecordList.get(index);
        BorrowRecordDAO.addNewBorrowRecord(
                borrowRecord1.getUserId(), borrowRecord1.getBookId(),
                borrowRecord1.getBorrowDate(), borrowRecord1.getDueDate(), borrowRecord1.getStatus(),
                borrowRecord1.getReturnDate()
        );
        index = borrowRecordList.getLast().getBorrowId() + 1;
        BorrowRecord borrowRecord2 = BorrowRecordDAO.getBorrowRecordById(index);
        assertNotNull(borrowRecord2);
        System.out.println(borrowRecord2 + borrowRecord2.getStatus().toString());

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }

    @Test
    @Order(5)
    void updateBorrowRecord() {
        System.out.println("\nTest updateBorrowRecord - Start");

        BorrowRecord borrowRecord1 = BorrowRecordDAO.getBorrowRecordById(index);
        assertNotNull(borrowRecord1);
        borrowRecord1.setStatus(BorrowStatus.CANCELED);
        BorrowRecordDAO.updateBorrowRecord(borrowRecord1);
        System.out.println(borrowRecord1);

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }

    @Test
    @Order(6)
    void searchBorrowRecordsByUsernameAndStatus() {
        System.out.println("\nTest searchBorrowRecordsByUsernameAndStatus - Start");

        index = (int) (Math.random() * borrowRecordList.size());
        BorrowStatus status = borrowRecordList.get(index).getStatus();
        int userId = borrowRecordList.get(index).getUserId();
        User user = UserDAO.getUserById(userId);
        if (user == null) {
            user = DeletedUserDAO.getDeletedUserById(userId);
        }
        assertNotNull(user);
        List<BorrowRecord> borrowRecordList1 = BorrowRecordDAO.searchBorrowRecordsByUsernameAndStatus(
                user.getUserName(),status
        );
        assertNotNull(borrowRecordList1);
        assertFalse(borrowRecordList1.isEmpty());
        System.out.println("username: " + user.getUserName());
        System.out.println("status: " + status);
        System.out.println(borrowRecordList1);

        System.out.println("BorrowRecordDAOTest setupAfterClass - End");
    }
}
