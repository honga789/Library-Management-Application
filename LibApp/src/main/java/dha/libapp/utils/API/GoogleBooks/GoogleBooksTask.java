/**
 * The package dha.libapp.utils.API.GoogleBooks provides utilities to interact with the Google Books API.
 * <p>
 * This package includes functionality to fetch book data from the Google Books API
 * based on a query string, parse the API response, and return the book details in a structured format.
 */
package dha.libapp.utils.API.GoogleBooks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import dha.libapp.models.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a task that fetches book data from the Google Books API asynchronously.
 * <p>
 * This class extends {@link Thread} and performs an API request to fetch details of books
 * based on a given query. The results are passed to a callback for further processing.
 */
public class GoogleBooksTask extends Thread {
    private final String query;
    private final BookFetchCallback callback;

    /**
     * The API key for authenticating requests to the Google Books API.
     */
    private static final String API_KEY = "AIzaSyCb4_1F0aXwL6lDlhdW6VhJ-KUKyQzVM4U";

    /**
     * Constructs a new {@code GoogleBooksTask}.
     *
     * @param query    the search query string for finding books
     * @param callback the callback to handle the results or errors
     */
    public GoogleBooksTask(String query, BookFetchCallback callback) {
        this.query = query;
        this.callback = callback;
    }

    /**
     * Executes the task to fetch book data from the Google Books API.
     * <p>
     * This method constructs the API request URL, makes an HTTP GET request,
     * parses the JSON response, and extracts relevant book details. If successful,
     * the results are passed to the {@code onSuccess} method of the callback. In case of an error,
     * the {@code onFailure} method of the callback is invoked.
     */
    @Override
    public void run() {
        List<Book> booksData = new ArrayList<>();
        try {
            // URL encode the query string to handle special characters
            // Construct the URL to access the Google Books API
            String url = "https://www.googleapis.com/books/v1/volumes?q" + query + "&key=" + API_KEY;
            System.out.println(url);
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
                    // Extract and collect book info
                        System.out.println("Get JSON Item:");
                        JsonObject volumeInfo = items.get(0).getAsJsonObject().getAsJsonObject("volumeInfo");
                        String title = volumeInfo.get("title").getAsString();
                        System.out.println("Title: " + title);
                        JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");

                        String author = null;
                        if (authorsArray != null) {
                            author= authorsArray.get(0).getAsString();
                        }
                        System.out.println("Author: " + author);

                        JsonPrimitive publisherJson = volumeInfo.getAsJsonPrimitive("publisher");
                        String publisher = null;
                        if (publisherJson != null) {
                            publisher = publisherJson.getAsString();
                        }
                        System.out.println("Publisher: " + publisher);
                        JsonObject publishDateJsonObj = volumeInfo.getAsJsonObject("publishDate");
                        String publishedDateJson = null;
                        if (publishDateJsonObj != null) {
                            publishedDateJson = publishDateJsonObj.getAsString();
                        }
                        System.out.println("Published Date: " + publishedDateJson);
                        //check date and parse
                        SimpleDateFormat formatterFullDate = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
                        Date publishedDate = null;

                        if (publishedDateJson != null) {
                            Date tempDate = null;
                            if (publishedDateJson.length()==10) {
                                try {
                                    tempDate = formatterFullDate.parse(publishedDateJson);
                                    System.out.println("Converted Date: " + tempDate);
                                } catch (ParseException e) {
                                    System.out.println("Invalid date format from API");
                                }

                            }
                            if (publishedDateJson.length()==4) {
                                try {
                                    tempDate = formatterYear.parse(publishedDateJson);
                                    System.out.println("Converted Date: " + tempDate);
                                } catch (ParseException e) {
                                    System.out.println("Invalid date format from API");
                                }
                            }
                            if (tempDate != null) {
                                publishedDate = tempDate;
                            }
                        }

                        int quantity = 0;

                        String description = null;
                        if (volumeInfo.get("description") != null) {
                            description = volumeInfo.get("description").getAsString();
                        }
                        System.out.println("Description: " + description);

                        String imgUrl = null;
                        if (volumeInfo.getAsJsonObject("imageLinks") != null) {
                            imgUrl= volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
                        }
                        System.out.println("imgUrl: " + imgUrl);

                        JsonArray identifiersArray = volumeInfo.getAsJsonArray("industryIdentifiers");
                        String ISBN_13 = "0000000000";
                        for (int j = 0; j < identifiersArray.size(); j++) {
                            JsonObject identifierObj = identifiersArray.get(j).getAsJsonObject();
                            if ("ISBN_13".equals(identifierObj.get("type").getAsString())) {
                                ISBN_13 = identifierObj.get("identifier").getAsString();
                            }
                        }

                        System.out.println("ISBN_13: " + ISBN_13);
                        if (title != null) {
                            Book book = new Book(-1, ISBN_13, title,author, publisher, publishedDate, quantity, description, imgUrl, null);
                            booksData.add(book);
                        }


                    // On success, trigger the callback
                    if (callback != null) {
                        callback.onSuccess(booksData);
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
