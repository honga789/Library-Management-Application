package dha.libapp.utils.API;

import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorHandle {
    private ExecutorService executorService;
    private static final ExecutorHandle instance = new ExecutorHandle();
    private ExecutorHandle() {
        executorService = Executors.newFixedThreadPool(10);
    }

    public static ExecutorHandle getInstance() {
        return instance;
    }

    public void addTask(Runnable task) {
        executorService.submit(task);
    }
    public void shutdownExecutorService() {
        executorService.shutdown();
    }


//    public static Image getImage(String imageUrl) {
//        List<Image> container = new ArrayList<>();
//        ImageFetchCallback callback = new ImageFetchCallback() {
//            @Override
//            public void onSuccess(Image image) {
//                System.out.println("Get image success");
//                container.add(image);
//            }
//            @Override
//            public void onFailure(Exception e) {
//                System.out.println("Get image failure");
//            }
//        };
//        ImageTask imageTask = new ImageTask(imageUrl, callback);
//        instance.addTask(imageTask);
//        if (container.isEmpty()) {
//            return null;
//        } else {
//            return container.getFirst();
//        }
//    }
}
