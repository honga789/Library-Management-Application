package dha.libapp.services.admin;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.GenreType;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;
import java.util.List;

import dha.libapp.models.Book;
import java.util.ArrayList;
import java.util.Date;

public class BookService {

    private static BookService instance = new BookService();
    private BookService() {
        System.out.println("init BookService");
    }
    public static BookService getInstance() {

        return instance;
    }

    public interface GenreCallback {
        void onSuccess(List<GenreType> genreTypesCallback);
    }

    public void getGenreByName(String genreName, GenreCallback callback) {
        Task<List<GenreType>> task = new Task<>() {

            @Override
            protected List<GenreType> call() throws Exception {
                List<GenreType> genreTypes = new ArrayList<>();
                genreTypes.add(GenreTypeDAO.getGenreTypeByName(genreName));
                return genreTypes;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                callback.onSuccess(getValue());
                System.out.println("get genre success");
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("get genre by name failed");
            }
        };
        new Thread(task).start();
    }

    public interface GlobalCallback {
        void onSuccess();
        void onFailure(Exception genreTypesCallback);
    }

    public void getGenres(GenreCallback callback) {
        Task<List<GenreType>> task = new Task<>() {

            @Override
            protected List<GenreType> call() throws Exception {
                return GenreTypeDAO.getAllGenreType();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("get genres successful");
                callback.onSuccess(GenreTypeDAO.getAllGenreType());
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("get genres failed");
            }
        };
        new Thread(task).start();
    }

    public void deleteBook(Book book) throws Exception {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                BookDAO.deleteBookById(book.getBookId());
                return null;
            }

            @Override
            protected void failed() {
                super.failed();
                throw new RuntimeException("delete book failed");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("delete book successful");

            }
        };
        new Thread(task).start();
    }
    public void updateBook(Book book) throws Exception {
        if (book.getISBN().isEmpty() || book.getTitle().isEmpty() || book.getAuthor().isEmpty()
        || book.getPublisher().isEmpty() || book.getPublicationDate() == null || book.getQuantity() < 0
        || book.getDescription().isEmpty() || book.getCoverImagePath().isEmpty() || book.getGenreList().isEmpty()
        || book.getISBN().length() < 10 || book.getISBN().length() > 30 || book.getTitle().length() > 100
        || book.getAuthor().length() > 100 || book.getPublisher().length() > 100
        || book.getCoverImagePath().length() > 256 || ISBNExists(book.getISBN())) {

            // controller for invalid
            throw new RuntimeException("Invalid values");
        }

        BookSyncDAO.updateBookSync(book, new DAOUpdateCallback() {

            @Override
            public void onSuccess() {
                System.out.println("update book successful");
                // controller;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("update book failed");
                throw new RuntimeException("update book failed", e);
                // controller;
            }
        });
    }

    public void addBook(String ISBN, String title, String author, String publisher,
                               Date publicationDate, int quantity, String description,
                               String coverImagePath, ArrayList<GenreType> genreList) {
        //check for book in dataBase
        Task<Book> task = new Task<>() {
            @Override
            protected void failed() {
                System.out.println("Task failed");
                throw new RuntimeException();

            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Book added successfully");
            }

            @Override
            protected Book call() throws Exception {
                Book book = null;
                try {
                    book = BookDAO.getBookByISBN(ISBN);
                    if (book == null) {
                        book = BookDAO.getDeletedBookByISBN(ISBN);
                        if (book == null) {
                            BookDAO.addNewBook(ISBN,title,author,publisher,publicationDate,quantity,description,coverImagePath,genreList);
                        } else {
                            book.setQuantity(quantity);
                            BookDAO.updateBook(book);
                        }
                    } else {
                        book.setQuantity(book.getQuantity() + quantity);
                        BookDAO.updateBook(book);
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();


    }
    private static boolean ISBNExists(String ISBN) {
        return BookDAO.getBookByISBN(ISBN) != null;
    }
}
