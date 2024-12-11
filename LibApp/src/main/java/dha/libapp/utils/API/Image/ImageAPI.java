package dha.libapp.utils.API.Image;


/**
 * A utility class for working with images via asynchronous API calls.
 * Provides methods to create and manage image fetching tasks.
 */
public class ImageAPI {

    /**
     * Creates an {@link ImageTask} to fetch an image from the specified URL.
     * <p>
     * The task must be started explicitly by calling the {@code start()} method
     * to begin the image fetching operation.
     * </p>
     *
     * @param url      The URL of the image to fetch. Must be a valid and accessible URL.
     * @param callback A callback to handle the result of the image fetch operation.
     *                 The callback's {@code onSuccess} or {@code onFailure} method will be invoked
     *                 based on the outcome of the operation. Can be {@code null} if no callback is required.
     * @return An {@link ImageTask} instance configured with the given URL and callback.
     */
    public static ImageTask getImageWithUrl(String url, ImageFetchCallback callback) {
        return new ImageTask(url, callback);
    }
}
