package dha.libapp.utils.API.GoogleBooks;

import dha.libapp.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCb4_1F0aXwL6lDlhdW6VhJ-KUKyQzVM4U";

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
        GoogleBooksTask getByISBN = getBookDataByISBN("1975335341", callback);
        executorService.submit(getByISBN);

        executorService.shutdown();

    }
}
