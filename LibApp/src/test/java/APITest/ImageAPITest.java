package APITest;

import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static dha.libapp.utils.API.Image.ImageAPI.getImageWithUrl;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageAPITest {

    @Test
    public void imageAPITest() {
        String testURL = "https://portal.uet.vnu.edu.vn/uet-lms/uet_files/logo.png";
        List<Image> dataHolder = new ArrayList<>();
        assertNotNull(ExecutorHandle.getInstance());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ImageFetchCallback callback = new ImageFetchCallback() {

            @Override
            public void onSuccess(Image image) {
                System.out.println("API call success");
                System.out.println(image);
                dataHolder.add(image);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Exception ex) {
                System.out.println(ex.getMessage());
                countDownLatch.countDown();
            }
        };

        ImageTask imageTask = getImageWithUrl(testURL, callback);
        ExecutorHandle.getInstance().addTask(imageTask);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        if (!dataHolder.isEmpty()) {
            assertNotNull(dataHolder.getFirst());
            System.out.println("API call completed");
        }
        ExecutorHandle.getInstance().shutdownExecutorService();
    }
}
