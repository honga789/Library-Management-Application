package APITest;

import dha.libapp.models.Book;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.GoogleBooks.BookFetchCallback;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksAPI;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksTask;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleBooksAPITest {
    @Test
    public void SimpleAPICallTest() {
        String ISBN = "9780137909100";
        String expectedTitle = "Code";
        assertNotNull(ExecutorHandle.getInstance());
        List<Book> dataHolder = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        BookFetchCallback callback = new BookFetchCallback() {

            @Override
            public void onSuccess(List<Book> booksData) {
                dataHolder.addAll(booksData);
                System.out.println("API call success");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Exception ex) {
                countDownLatch.countDown();
            }
        };
        GoogleBooksTask googleBooksTask = GoogleBooksAPI.getBookDataByISBN(ISBN, callback);
        ExecutorHandle.getInstance().addTask(googleBooksTask);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Book book = null;
        if (!dataHolder.isEmpty()) {
            book = dataHolder.getFirst();
        }
        ;
        assertNotNull(book);
        assertEquals(book.getTitle(), expectedTitle);
        System.out.println("Test success");
        ExecutorHandle.getInstance().shutdownExecutorService();
    }
}
