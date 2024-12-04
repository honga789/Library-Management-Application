package dha.libapp.services.admin;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.models.GenreType;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;
import java.util.List;

import dha.libapp.models.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class BookService {

    private static BookService instance = new BookService();

    private BookService() {}

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

    public void addBook(String ISBN, String title, String author, String publisher,
                        Date publicationDate, int quantity, String description,
                        String coverImagePath, ArrayList<GenreType> genreList) {
        //check for book in dataBase
        Task<Book> task = new Task<>() {

            @Override
            protected Book call() throws Exception {
                try {
                    Book book = BookDAO.getBookByISBN(ISBN);
                    if (book == null) {
                        book = BookDAO.getDeletedBookByISBN(ISBN);
                        if (book == null) {
                            BookDAO.addNewBook(ISBN, title, author, publisher, publicationDate,
                                    quantity, description, coverImagePath, genreList);
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
                    throw new RuntimeException("add Book failed");
                }
            }

            @Override
            protected void failed() {
                System.out.println("Task failed");
                throw new RuntimeException("add Book failed");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Book added successfully");
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
                || book.getCoverImagePath().length() > 256) {

            // controller for invalid
            throw new RuntimeException("Invalid values");
        }

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                Book bookOld = BookDAO.getBookById(book.getBookId());
                assert bookOld != null;
                boolean exist = (BookDAO.getBookByISBN(book.getISBN()) != null);
                if (!Objects.equals(bookOld.getISBN(), book.getISBN()) && exist) {
                    throw new Exception("Book already exists");
                }

                BookDAO.updateBook(book);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Update book successfully");
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("Update book failed");
                throw new RuntimeException("Update book failed");
            }
        };
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
}
