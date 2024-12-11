package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

/**
 * A cache for storing a list of borrowed books, specifically for the Borrowed Tab feature.
 * This cache optimizes data retrieval by reducing the need to reload data from the database.
 */
public class BorrowedTabCache extends Cache {

    /**
     * CacheItem to store the list of borrowed books.
     */
    private final CacheItem<List<Book>> borrowedBookList = new CacheItem<>();

    public CacheItem<List<Book>> getBorrowedBookList() {
        return borrowedBookList;
    }

    /**
     * Clears all data in the borrowed book list cache.
     */
    @Override
    public void clearAll() {
        borrowedBookList.clear();
    }
}
