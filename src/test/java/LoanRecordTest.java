import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class LoanRecordTest {
    @Test
    public void testConstructor() {
        LocalDate dueDate = LocalDate.now().minusDays(15);
        LocalDateTime borrowDate = LocalDateTime.now();
        LoanStatus status = LoanStatus.PENDING;
        LoanRecord test = new LoanRecord(123, 321, borrowDate, dueDate, status);
        assertEquals(test.getUserId(),123);
        assertEquals(test.getDocumentId(),321);
        assertEquals(test.getDueDate(), dueDate);
        assertEquals(test.getBorrowDate(), borrowDate);
        assertEquals(test.getLoanStatus(), status);
        assertEquals(test.getRecordId(),test.genId());
    }
    @Test
    public void testConstructorCopy() {
        LocalDate dueDate = LocalDate.now().plusDays(15);
        LocalDateTime borrowDate = LocalDateTime.now();
        LoanStatus status = LoanStatus.PENDING;
        LoanRecord test = new LoanRecord(123, 321, borrowDate, dueDate, status);
        LoanRecord testCopy = new LoanRecord(test);
        assertEquals(testCopy.getUserId(),test.getUserId());
        assertEquals(testCopy.getDocumentId(),test.getDocumentId());
        assertEquals(testCopy.getDueDate(),test.getDueDate());
        assertEquals(testCopy.getBorrowDate(),test.getBorrowDate());
        assertEquals(testCopy.getLoanStatus(),test.getLoanStatus());
        assertEquals(testCopy.getRecordId(),test.getRecordId());
    }
    @Test
    public void testToString() {
        LocalDate dueDate = LocalDate.now().plusDays(15);
        LocalDateTime borrowDate = LocalDateTime.now();
        LoanStatus status = LoanStatus.PENDING;
        LoanRecord test = new LoanRecord(123, 321, borrowDate, dueDate, status);
        System.out.println(test.toString());
    }
}
