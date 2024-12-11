package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

/**
 * A cache for storing data related to the Home Tab feature.
 * This cache includes recommendations and top trending books,
 * optimizing data retrieval for a better user experience.
 */
public class HomeTabCache extends Cache {

    /**
     * CacheItem to store the list of recommended books.
     */
    private final CacheItem<List<Book>> recommendationBookList = new CacheItem<>();

    /**
     * CacheItem to store the list of top trending books.
     */
    private final CacheItem<List<Book>> topTrendingBookList = new CacheItem<>();

    public CacheItem<List<Book>> getRecommendationBookList() {
        return recommendationBookList;
    }

    public CacheItem<List<Book>> getTopTrendingBookList() {
        return topTrendingBookList;
    }

    /**
     * Clears all data in the Home Tab cache, including recommendations and top trending books.
     */
    @Override
    public void clearAll() {
        recommendationBookList.clear();
        topTrendingBookList.clear();
    }
}
