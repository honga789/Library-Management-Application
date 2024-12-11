package dha.libapp.syncdao.utils;

/**
 * Callback interface for update operations.
 * Provides methods to handle success and error scenarios during DAO updates.
 */
public interface DAOUpdateCallback {
    /**
     * Called when the update operation is successful.
     */
    void onSuccess();

    /**
     * Called when an error occurs during the update operation.
     *
     * @param e the exception encountered during the operation.
     */
    void onError(Throwable e);
}
