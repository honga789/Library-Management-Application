package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.models.Book;

import java.util.List;

public class PendingTabCache {
    private PendingTabCache() {}

    private static PendingTabCache instance;
    public static PendingTabCache getInstance() {
        if (instance == null) {
            instance = new PendingTabCache();
        }
        return instance;
    }

    private final Cache<List<Book>> pendingBookList = new Cache<>();

    public Cache<List<Book>> getPendingBookList() {
        return pendingBookList;
    }
}
