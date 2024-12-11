package dha.libapp.utils.API.Image;


import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A task for fetching an image from a given URL asynchronously.
 * This class extends {@link Thread} and performs the image fetching in a separate thread.
 */
public class ImageTask extends Thread {
    private final String imgUrl;
    private final ImageFetchCallback callback;

    /**
     * Constructs an {@code ImageTask}.
     *
     * @param imgUrl   The URL of the image to be fetched.
     * @param callback A callback to handle the result of the image fetch operation.
     *                 Can be {@code null} if no callback is required.
     */
    public ImageTask(String imgUrl, final ImageFetchCallback callback) {
        this.imgUrl = imgUrl;
        this.callback = callback;
    }

    /**
     * Runs the image fetching operation in a separate thread.
     * Attempts to fetch the image from the given URL. On success or failure,
     * the appropriate method of the {@link ImageFetchCallback} is invoked.
     */
    @Override
    public void run() {
        Image image;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to fetch image: " + connection.getResponseCode());
            }

            try (InputStream inputStream = connection.getInputStream()) {
                image = new Image(inputStream);
            }
            if (callback != null) {
                System.out.println("callback: " + callback);
                callback.onSuccess(image);
            } else {
                System.out.println("callback: null");
            }

        } catch (Exception e) {
            if (callback != null) {
                callback.onFailure(e);
            }
        }
    }
}
