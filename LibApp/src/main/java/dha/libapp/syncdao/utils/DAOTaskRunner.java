package dha.libapp.syncdao.utils;

import javafx.concurrent.Task;

public class DAOTaskRunner {
    public static <T> void executeTask(Task<T> task, DAOExecuteCallback<T> callback) {
        task.setOnSucceeded(event -> callback.onSuccess(task.getValue()));
        task.setOnFailed(event -> callback.onError(task.getException()));
        new Thread(task).start();
    }

    public static void updateTask(Task<Void> task, DAOUpdateCallback callback) {
        task.setOnSucceeded(event -> callback.onSuccess());
        task.setOnFailed(event -> callback.onError(task.getException()));
        new Thread(task).start();
    }
}
