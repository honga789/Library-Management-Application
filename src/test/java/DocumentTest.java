import org.junit.Test;
import static org.junit.Assert.*;

public class DocumentTest {

    @Test
    public void testBookEquals() {
        Book book = new Book("camnangph..", "duy", "duy", "123-456");
        book.setEdition("123")
                .setAuthor("duy")
                .setTitle("duy");

        Book book1 = new Book(book);
        assertEquals(book, book1);

        book1.setISBN("123-457");
        assertNotEquals(book, book1);
    }

    @Test
    public void testMagazineEquals() {
        Magazine magazine = new Magazine("camnangph...", "hieu",
                    "hieu", "is12");
        magazine.setFrequency("1weeks")
                .setPublisher("bao nhan dan")
                .setLocation("A1");

        Magazine magazine1 = new Magazine(magazine);
        assertEquals(magazine, magazine1);

        magazine1.setTitle("not camnangph...");
        assertNotEquals(magazine, magazine1);
    }

    @Test
    public void testNewspaperEquals() {
        Newspaper newspaper = new Newspaper("camnangpho..", "dung",
                            "dung", "123AN");
        newspaper.setFrequency("1weeks")
                .setPublisher("bao nhan dan")
                .setLocation("A1");

        Newspaper newspaper1 = new Newspaper(newspaper);
        assertEquals(newspaper, newspaper1);

        newspaper.setTitle("not camnangph...");
        assertNotEquals(newspaper, newspaper1);
    }

    @Test
    public void testBookCheckValidInit() {
        try {
            Book book = new Book("camnangph..", "duy", "duy", null);
            Book book1 = new Book("camnangph..", "duy", "duy", "");
            Book book2 = new Book("", "duy", "duy", "123");
        }
        catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                assert true;
            }
        }
    }

    @Test
    public void testMagazineCheckValidInit() {
        try {
            Magazine magazine = new Magazine("camnangph...", "hieu",
                    "hieu", null);
            Magazine magazine1 = new Magazine("camnangph...", "hieu",
                    "hieu", "");
            Magazine magazine2 = new Magazine("", "hieu",
                    "hieu", "is12");
        }
        catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                assert true;
            }
        }
    }

    @Test
    public void testNewspaperCheckValidInit() {
        try {
            Newspaper newspaper = new Newspaper("camnangpho..", "dung",
                    "dung", null);
            Newspaper newspaper1 = new Newspaper("camnangpho..", "dung",
                    "dung", "");
            Newspaper newspaper2 = new Newspaper("", "dung",
                    "dung", "123AN");
        }
        catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                assert true;
            }
        }
    }
}
