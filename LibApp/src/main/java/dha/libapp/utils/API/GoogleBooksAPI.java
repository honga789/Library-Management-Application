package dha.libapp.utils.API;

import dha.libapp.models.Book;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCb4_1F0aXwL6lDlhdW6VhJ-KUKyQzVM4U";  // Replace with your actual API key

    // Interface for the callback function
    interface BookFetchCallback {
        void onSuccess(List<String> bookTitles);
        void onFailure(Exception ex);
    }

    static class GoogleBooksTask extends Thread {
        private final String query;
        private final BookFetchCallback callback;

        public GoogleBooksTask(String query, BookFetchCallback callback) {
            this.query = query;
            this.callback = callback;
        }

        @Override
        public void run() {
            List<String> bookTitles = new ArrayList<>();
            try {
                // URL encode the query string to handle special characters
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                // Construct the URL to access the Google Books API
                String url = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;

                // Create a URL object
                URL apiUrl = new URL(url);

                // Open an HTTP connection to the API
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                // Set the request timeout (optional)
                connection.setConnectTimeout(5000); // 5 seconds timeout
                connection.setReadTimeout(5000); // 5 seconds read timeout

                // Send the GET request and get the response code
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the API
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Parse the JSON response
                    JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                    JsonArray items = jsonResponse.getAsJsonArray("items");

                    if (items != null) {
                        // Extract and collect book titles
                        for (int i = 0; i < items.size(); i++) {
                            JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");
                            String title = volumeInfo.get("title").getAsString();
                            bookTitles.add(title);
                        }

                        // On success, trigger the callback
                        if (callback != null) {
                            callback.onSuccess(bookTitles);
                        }
                    } else {
                        throw new Exception("No books found in the response");
                    }
                } else {
                    throw new Exception("Failed to get response from API. HTTP error code: " + responseCode);
                }


            } catch (Exception e) {
                // On failure, trigger the callback with the exception
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        }
    }

    public static void getBookByTitle(String title, BookFetchCallback callback) {
        // Use ExecutorService for managing threads
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Create and submit the task to fetch books
        GoogleBooksTask task = new GoogleBooksTask(title, callback);
        executor.submit(task);

        // Shutdown the executor once the task is submitted
        executor.shutdown();
    }

    public static void main(String[] args) {
        // Define the callback for handling success and failure
        BookFetchCallback callback = new BookFetchCallback() {
            @Override
            public void onSuccess(List<String> bookTitles) {
                System.out.println("Books fetched successfully!");
                for (String bookTitle : bookTitles) {
                    System.out.println(bookTitle);
                }
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println("Error fetching books: " + ex.getMessage());
            }
        };
        getBookByTitle("java Books", callback);
    }
}
