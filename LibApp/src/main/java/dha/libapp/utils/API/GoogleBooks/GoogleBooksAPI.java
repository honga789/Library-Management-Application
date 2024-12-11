/**
 * The package dha.libapp.utils.API.GoogleBooks provides utilities to interact with the Google Books API.
 * <p>
 * This package includes functionality to fetch book data from the Google Books API
 * based on a query string or ISBN, parse the API response, and return the book details in a structured format.
 */
package dha.libapp.utils.API.GoogleBooks;

/**
 * Provides methods to interact with the Google Books API.
 * <p>
 * This class includes methods to fetch book data by ISBN and demonstrates a basic usage example in the main method.
 */
public class GoogleBooksAPI {

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
}
