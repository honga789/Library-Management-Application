package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

/**
 * A cache for storing data related to the Returned Tab feature.
 * This cache stores a list of books that have been returned,
 * optimizing data retrieval for returned-related operations.
 */
public class ReturnedTabCache extends Cache {

    /**
     * CacheItem to store the list of returned books.
     */
    private final CacheItem<List<Book>> returnedBookList = new CacheItem<>();

    public CacheItem<List<Book>> getReturnedBookList() {
        return returnedBookList;
    }

    /**
     * Clears all data in the Returned Tab cache, including the list of returned books.
     */
    @Override
    public void clearAll() {
        returnedBookList.clear();
    }
}
