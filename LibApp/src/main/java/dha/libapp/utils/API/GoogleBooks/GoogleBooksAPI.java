/**
 * The package dha.libapp.utils.API.GoogleBooks provides utilities to interact with the Google Books API.
 * <p>
 * This package includes functionality to fetch book data from the Google Books API
 * based on a query string or ISBN, parse the API response, and return the book details in a structured format.
 */
package dha.libapp.utils.API.GoogleBooks;

import dha.libapp.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides methods to interact with the Google Books API.
 * <p>
 * This class includes methods to fetch book data by ISBN and demonstrates a basic usage example in the main method.
 */
public class GoogleBooksAPI {

    /**
     * The API key for authenticating requests to the Google Books API.
     */
    private static final String API_KEY = "AIzaSyCb4_1F0aXwL6lDlhdW6VhJ-KUKyQzVM4U";

    /**
     * Creates a {@link GoogleBooksTask} to fetch book data by ISBN.
     * <p>
     * This method removes any hyphens from the provided ISBN and constructs a query for the Google Books API.
     *
     * @param isbn     the ISBN of the book to search for
     * @param callback the callback to handle the results or errors
     * @return a {@link GoogleBooksTask} configured to fetch data for the provided ISBN
     */
    public static GoogleBooksTask getBookDataByISBN(String isbn, BookFetchCallback callback) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < isbn.length(); ++i) {
            if (isbn.charAt(i) != '-') {
                sb.append(isbn.charAt(i));
            }
        }
        isbn = sb.toString();
        String query = "=isbn:" + isbn;
        return new GoogleBooksTask(query, callback);
    }

    /**
     * Demonstrates usage of the {@link GoogleBooksAPI}.
     * <p>
     * This main method defines a callback to handle API responses and utilizes an executor service
     * to execute tasks in a thread pool.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Define the callback for handling success and failure
        List<Book> booksData = new ArrayList<>();

        BookFetchCallback callback = new BookFetchCallback() {
            @Override
            public void onSuccess(List<Book> bookTitles) {
                System.out.println("Books fetched successfully!");
                booksData.addAll(bookTitles);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println("Error fetching books: " + ex.getMessage());
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // GoogleBooksTask getByISBN = getBookDataByISBN("1975335341", callback);

        executorService.shutdown();
    }
}
