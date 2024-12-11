package dha.libapp.syncdao.utils;

/**
 * Callback interface for execution operations with a return type.
 * Provides methods to handle success and error scenarios during DAO executions.
 *
 * @param <T> the type of result expected from the execution.
 */
public interface DAOExecuteCallback<T> {
    /**
     * Called when the execution operation is successful.
     *
     * @param result the result of the successful operation.
     */
    void onSuccess(T result);

    /**
     * Called when an error occurs during the execution operation.
     *
     * @param e the exception encountered during the operation.
     */
    void onError(Throwable e);
}
