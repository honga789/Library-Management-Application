package dha.libapp.cache;

public class CacheItem<T> {
    private T data;
    private boolean saved;

    public CacheItem() {
        data = null;
        saved = false;
    }

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

    public boolean isSaved() {
        return saved;
    }

    public void clear() {
        data = null;
        saved = false;
    }
}
