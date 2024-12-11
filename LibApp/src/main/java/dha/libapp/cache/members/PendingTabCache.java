package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

/**
 * A cache for storing data related to the Pending Tab feature.
 * This cache stores a list of books that are pending approval or processing,
 * optimizing data retrieval for pending-related operations.
 */
public class PendingTabCache extends Cache {

    /**
     * CacheItem to store the list of pending books.
     */
    private final CacheItem<List<Book>> pendingBookList = new CacheItem<>();

    public CacheItem<List<Book>> getPendingBookList() {
        return pendingBookList;
    }

    /**
     * Clears all data in the Pending Tab cache, including the list of pending books.
     */
    @Override
    public void clearAll() {
        pendingBookList.clear();
    }
}
