import org.junit.Test;
import static org.junit.Assert.*;

public class DocumentTest {
    public static void main(String[] args) {
        Book book = new Book("camnangph..", "duy", "duy", "123-456");

        book.setEdition("123")
                .setAuthor("duy")
                .setTitle("duy");
        System.out.println(book);

        Book book1 = new Book(book);

        System.out.println(book.equals(book1));
        book1.setISBN("123-457");
        System.out.println(book.equals(book1));

        Magazine magazine = new Magazine("123", "duy", "duy", "123-456");
        magazine.setFrequency("1234")
                .setTitle("1243")
                .setLanguage("english")
                .setPublisher("123");

        System.out.println(magazine);

        Magazine magazine1 = new Magazine(magazine);
        System.out.println(magazine.equals(magazine1));
        magazine1.setIssueNumber("123-457");
        System.out.println(magazine.equals(magazine1));

    }
}
