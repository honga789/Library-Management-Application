package dha.libapp.cache;

public class Cache<T> {
    private T data;
    private boolean saved;

    public Cache() {
        data = null;
        saved = false;
    }

    public Cache(T data) {
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
