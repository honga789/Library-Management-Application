package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

public class BorrowedTabCache extends Cache {
    private final CacheItem<List<Book>> borrowedBookList = new CacheItem<>();

    public CacheItem<List<Book>> getBorrowedBookList() {
        return borrowedBookList;
    }

    @Override
    public void clearAll() {
        borrowedBookList.clear();
    }
}
