package dha.libapp.utils.API.Image;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageAPI {


    public static ImageTask getImageWithUrl(String url, ImageFetchCallback callback) {
        return new ImageTask(url, callback);
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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(task);

        executorService.shutdown();

    }
}
