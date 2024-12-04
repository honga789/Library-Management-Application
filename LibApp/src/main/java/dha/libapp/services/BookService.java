package dha.libapp.services;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.GenreType;

import java.util.ArrayList;
import java.util.Date;

public class BookService {

    private BookService instance = new BookService();
    private BookService() {
    }

    public static void addBook(String ISBN, String title, String author, String publisher,
                               Date publicationDate, int quantity, String description,
                               String coverImagePath, ArrayList<GenreType> genreList) {

    }

}
