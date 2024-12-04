package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.models.Book;

import java.util.List;

public class ReturnedTabCache {
    private ReturnedTabCache() {}

    private static ReturnedTabCache instance;
    public static ReturnedTabCache getInstance() {
        if (instance == null) {
            instance = new ReturnedTabCache();
        }
        return instance;
    }

    private final Cache<List<Book>> returnedBookList = new Cache<>();

    public Cache<List<Book>> getReturnedBookList() {
        return returnedBookList;
    }
}
