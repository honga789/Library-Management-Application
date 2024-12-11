package dha.libapp.syncdao.utils;

import javafx.concurrent.Task;

/**
 * Utility class for running DAO tasks asynchronously with callback support.
 * Provides methods to execute and update tasks, handling their success and failure states.
 */
public class DAOTaskRunner {

    /**
     * Executes a JavaFX Task asynchronously and triggers the appropriate DAOExecuteCallback method.
     *
     * @param <T>      the type of result expected from the task.
     * @param task      the task to be executed.
     * @param callback  the callback to handle success or failure of the task.
     */
    public static <T> void executeTask(Task<T> task, DAOExecuteCallback<T> callback) {
        task.setOnSucceeded(event -> callback.onSuccess(task.getValue()));
        task.setOnFailed(event -> callback.onError(task.getException()));
        new Thread(task).start();
    }

    /**
     * Executes a JavaFX Task asynchronously for update operations and triggers the appropriate DAOUpdateCallback method.
     *
     * @param task      the task to be executed.
     * @param callback  the callback to handle success or failure of the task.
     */
    public static void updateTask(Task<Void> task, DAOUpdateCallback callback) {
        task.setOnSucceeded(event -> callback.onSuccess());
        task.setOnFailed(event -> callback.onError(task.getException()));
        new Thread(task).start();
    }
}
