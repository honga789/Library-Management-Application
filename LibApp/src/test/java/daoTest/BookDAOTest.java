package daoTest;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;

import dha.libapp.models.GenreType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDAOTest {
    static List<Book> bookList = new ArrayList<>();
    static int index = 0;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("BookDAOTest setUpBeforeClass - Start");

        bookList = BookDAO.getAllBook();
        assertNotNull(bookList);
        assertFalse(bookList.isEmpty());
        System.out.println("BookList: " + bookList.subList(0, Math.min(bookList.size(), 5)));

        System.out.println("BookDAOTest setUpAfterClass - End");
    }

    @BeforeEach
    void setUp() throws Exception {
        assertNotNull(bookList);
        assertFalse(bookList.isEmpty());
    }

    @Test
    @Order(1)
    void getBookById() {
        System.out.println("\nTest getBookById start");

        index = (int) (Math.random() * bookList.size());
        Book book1 = bookList.get(index);
        Book book2 = BookDAO.getBookById(book1.getBookId());
        assertEquals(book1, book2);

        System.out.println("Test getBookById end");
    }

    @Test
    @Order(2)
    void getBookByISBN() {
        System.out.println("\nTest getBookByISBN start");

        index = (int) (Math.random() * bookList.size());
        Book book1 = bookList.get(index);
        Book book2 = BookDAO.getBookByISBN(book1.getISBN());
        assertEquals(book1, book2);

        System.out.println("Test getBookByISBN end");
    }

    @Test
    @Order(3)
    void deleteBookById() {
        System.out.println("\nTest deleteBookById start");

        index = (int) (Math.random() * bookList.size());
        Book book1 = bookList.get(index);
        BookDAO.deleteBookById(book1.getBookId());
        Book book2 = BookDAO.getBookById(book1.getBookId());
        assertNull(book2);

        System.out.println("Test deleteBookById end");
    }

    @Test
    @Order(4)
    void getDeletedBookById() {
        System.out.println("\nTest getDeletedBookById start");

        Book book1 = bookList.get(index);
        Book book2 = BookDAO.getDeletedBookById(book1.getBookId());
        assertEquals(book1, book2);

        System.out.println("Test deleteBookById end");
    }

    @Test
    @Order(5)
    void getDeletedBookByISBN() {
        System.out.println("\nTest getDeletedBookByISBN start");

        Book book1 = bookList.get(index);
        Book book2 = BookDAO.getDeletedBookByISBN(book1.getISBN());
        assertEquals(book1, book2);

        System.out.println("Test deleteBookByISBN end");
    }

    @Test
    @Order(6)
    void updateBook() {
        System.out.println("\nTest updateBook start");

        Book book1 = bookList.get(index);
        BookDAO.updateBook(book1);
        Book book2 = BookDAO.getBookById(book1.getBookId());
        assertEquals(book1, book2);

        System.out.println("Test updateBook end");
    }

    @Test
    @Order(7)
    void addNewBook() {
        System.out.println("\nTest addNewBook start");

        String ISBN = Long.toString((long) (Math.random() * 1000000000000000000L));
        String title = "Test addNewBook title";
        String author = "Test addNewBook author";
        String publisher = "Test addNewBook publisher";
        Date publishedDate = new Date();
        int quantity = 1;
        String description = "Test addNewBook description";
        String coverImagePath = "Test addNewBook coverImagePath";
        ArrayList<GenreType> genreList = new ArrayList<>();
        BookDAO.addNewBook(ISBN, title, author, publisher, publishedDate, quantity, description,
                coverImagePath, genreList);
        Book book1 = BookDAO.getBookByISBN(ISBN);
        assertNotNull(book1);
        BookDAO.deleteBookById(book1.getBookId());

        System.out.println("Test addNewBook end");
    }

    @Test
    @Order(8)
    void searchBookByTitle() {
        System.out.println("\nTest searchBookByTitle start");

        index = (int) (Math.random() * bookList.size());
        String title = bookList.get(index).getTitle();
        List<Book> bookList1 = BookDAO.searchBookByTitle(title);
        assertNotNull(bookList1);
        assertFalse(bookList1.isEmpty());
        System.out.println(bookList1.subList(0, Math.min(bookList1.size(), 5)));

        System.out.println("Test searchBookByTitle end");
    }

    @Test
    @Order(9)
    void getTrendingBooks() {
        System.out.println("\nTest getTrendingBooks start");

        List<Book> bookList1 = BookDAO.getTrendingBooks(5);
        assertNotNull(bookList1);
        assertFalse(bookList1.isEmpty());
        System.out.println(bookList1.subList(0, Math.min(bookList1.size(), 5)));

        System.out.println("Test searchBookByTitle end");
    }
}
