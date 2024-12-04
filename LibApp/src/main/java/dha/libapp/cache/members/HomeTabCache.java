package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.models.Book;

import java.util.List;

public class HomeTabCache {
    private HomeTabCache() {}

    private static HomeTabCache instance;
    public static HomeTabCache getInstance() {
        if (instance == null) {
            instance = new HomeTabCache();
        }
        return instance;
    }

    private final Cache<List<Book>> recommendationBookList = new Cache<>();
    private final Cache<List<Book>> topTrendingBookList = new Cache<>();

    public Cache<List<Book>> getRecommendationBookList() {
        return recommendationBookList;
    }

    public Cache<List<Book>> getTopTrendingBookList() {
        return topTrendingBookList;
    }
}
