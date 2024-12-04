package dha.libapp.cache.members;

import dha.libapp.cache.Cache;
import dha.libapp.models.Book;

import java.util.List;

public class BorrowedTabCache {
    private BorrowedTabCache() {}

    private static BorrowedTabCache instance;
    public static BorrowedTabCache getInstance() {
        if (instance == null) {
            instance = new BorrowedTabCache();
        }
        return instance;
    }

    private final Cache<List<Book>> borrowedBookList = new Cache<>();

    public Cache<List<Book>> getBorrowedBookList() {
        return borrowedBookList;
    }
}
