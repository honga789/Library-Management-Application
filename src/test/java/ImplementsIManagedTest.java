import org.junit.Test;
import static org.junit.Assert.*;

public class ImplementsIManagedTest {

    @Test
    public void testBookGetId() {
        Book book = new Book("tmp", "tmp", "tmp", "tmp");
        System.out.println(book.getId());
        assertEquals(book.getDocumentId(), book.getId());
    }

    @Test
    public void testMagazineGetId() {
        Magazine magazine = new Magazine("tmp", "tmp", "tmp", "tmp");
        System.out.println(magazine.getId());
        assertEquals(magazine.getDocumentId(), magazine.getId());
    }

    @Test
    public void testUserGetId() {
        Member member = new Member("123", "123");
        System.out.println(member.getId());
        assertEquals(member.getUserId(), member.getId());
    }
}
