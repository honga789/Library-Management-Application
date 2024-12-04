package dha.libapp.utils.API.Image;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageTask extends Thread {
    private final String imgUrl;
    private final ImageFetchCallback callback;

    public ImageTask(String imgUrl, final ImageFetchCallback callback) {
        this.imgUrl = imgUrl;
        this.callback = callback;
    }

    @Override
    public void run() {
        Image image = null;
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
                callback.onSuccess(image);
            }

        } catch (Exception e) {
            if (callback != null) {
                callback.onFailure(e);
            }
        }
    }
}
