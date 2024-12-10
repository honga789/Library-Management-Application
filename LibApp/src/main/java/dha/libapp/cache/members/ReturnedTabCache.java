package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

public class ReturnedTabCache extends Cache {
    private final CacheItem<List<Book>> returnedBookList = new CacheItem<>();

    public CacheItem<List<Book>> getReturnedBookList() {
        return returnedBookList;
    }

    @Override
    public void clearAll() {
        returnedBookList.clear();
    }
}
