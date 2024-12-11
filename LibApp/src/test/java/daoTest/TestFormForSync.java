package daoTest;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class TestFormForSync {
    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Đảm bảo JavaFX Application Thread được khởi động
        if (!Platform.isFxApplicationThread()) {
            CountDownLatch fxLatch = new CountDownLatch(1);
            Platform.startup(fxLatch::countDown);
            fxLatch.await();
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(0);
                Thread.sleep(1000);
                System.out.println(123);
                Thread.sleep(5000);
                return null;
            }

            @Override
            protected void succeeded() {
                System.out.println(456);
                latch.countDown(); // Đánh dấu hoàn thành
            }
        };

        Platform.runLater(() -> new Thread(task).start());

        // Chờ cho đến khi task hoàn thành
        latch.await();
    }
}