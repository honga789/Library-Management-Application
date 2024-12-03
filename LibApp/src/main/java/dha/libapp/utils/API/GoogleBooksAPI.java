package dha.libapp.utils.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyCb4_1F0aXwL6lDlhdW6VhJ-KUKyQzVM4U";  // Replace with your actual API key

    static class GoogleBooksTask implements Callable<List<String>> {
        private final String query;

        public GoogleBooksTask(String query) {
            this.query = query;
        }

        @Override
        public List<String> call() throws Exception {
            List<String> bookTitles = new ArrayList<>();

            String encodedQuery = URLEncoder.encode(query, "UTF-8");
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
                }
            } else {
                throw new Exception("Failed to get response from API. HTTP error code: " + responseCode);
            }

            return bookTitles;
        }
    }

    public static void main(String[] args) {
        // Query term to search for books
        String query = "java programming";

        // Create an ExecutorService to run tasks in parallel
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // You can use a fixed thread pool if needed

        // Submit the task (GoogleBooksTask) that will call the API
        Future<List<String>> future = executorService.submit(new GoogleBooksTask(query));

        try {
            // Get the result from the Future
            List<String> bookTitles = future.get();

            // Print the book titles
            if (bookTitles.isEmpty()) {
                System.out.println("No books found.");
            } else {
                System.out.println("Books found:");
                for (String title : bookTitles) {
                    System.out.println(title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
