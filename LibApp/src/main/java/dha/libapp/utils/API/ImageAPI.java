package dha.libapp.utils.API;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageAPI {


    interface ImageFetchCallback {
        void onSuccess(Image image);
        void onFailure(Exception ex);
    }

    public static ImageTask getImageWithUrl(String url, ImageFetchCallback callback) {
        return new ImageTask(url, callback);
    }

    public static class ImageTask extends Thread {
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


    public static void main(String[] args) {
        List<Image> test = new ArrayList<Image>();
        String testUrl = "http://books.google.com/books/content?id=CD2dEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";
        ImageFetchCallback callback = new ImageFetchCallback() {
            @Override
            public void onSuccess(Image image) {
                System.out.println("Get image success");
                test.add(image);
            }
            @Override
            public void onFailure(Exception e) {
                System.out.println("Get image failure");
            }
        };
        ImageTask task = new ImageTask(testUrl, callback);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(task);
        while (test.isEmpty()) {
            System.out.println("Waiting for image");
            if (!test.isEmpty()) {
                System.out.println(test.get(0));
            }
        }

        executorService.shutdown();

    }
}
