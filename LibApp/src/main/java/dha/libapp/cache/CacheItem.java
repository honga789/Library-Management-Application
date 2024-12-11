package dha.libapp.cache;

/**
 * Represents a single item stored in the cache.
 *
 * @param <T> the type of data stored in the cache item
 */
public class CacheItem<T> {

    /**
     * The data stored in the cache item.
     */
    private T data;

    /**
     * Indicates whether the cache item has been saved.
     */
    private boolean saved;

    /**
     * Creates an empty cache item.
     */
    public CacheItem() {
        data = null;
        saved = false;
    }

    /**
     * Creates a cache item with the specified data.
     *
     * @param data the data to store in the cache item
     */
    public CacheItem(T data) {
        this.data = data;
        saved = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        this.saved = true;
    }

    /**
     * Checks whether the cache item has been saved.
     *
     * @return true if the item is saved, false otherwise
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Clears the cache item by removing its data and resetting its saved status.
     */
    public void clear() {
        data = null;
        saved = false;
    }
}
