package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.cache.CacheItem;
import dha.libapp.models.Book;

import java.util.List;

public class HomeTabCache extends Cache {
    private final CacheItem<List<Book>> recommendationBookList = new CacheItem<>();
    private final CacheItem<List<Book>> topTrendingBookList = new CacheItem<>();

    public CacheItem<List<Book>> getRecommendationBookList() {
        return recommendationBookList;
    }

    public CacheItem<List<Book>> getTopTrendingBookList() {
        return topTrendingBookList;
    }


    @Override
    public void clearAll() {
        recommendationBookList.clear();
        topTrendingBookList.clear();
    }
}
