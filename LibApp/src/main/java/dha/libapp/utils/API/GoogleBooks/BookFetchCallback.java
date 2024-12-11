package dha.libapp.utils.API.GoogleBooks;

import dha.libapp.models.Book;

import java.util.List;

// Interface for the callback
public interface BookFetchCallback {
    void onSuccess(List<Book> booksData);
    void onFailure(Exception ex);
}
