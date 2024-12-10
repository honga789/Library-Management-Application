package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

public class PendingTabCache extends Cache {
    private final CacheItem<List<Book>> pendingBookList = new CacheItem<>();

    public CacheItem<List<Book>> getPendingBookList() {
        return pendingBookList;
    }

    @Override
    public void clearAll() {
        pendingBookList.clear();
    }
}
