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

    private static BookService instance;

    private BookService() {
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
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

    public void getAllBooks(DAOExecuteCallback<List<Book>> callback) {
        BookSyncDAO.getAllBookSync(new DAOExecuteCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                System.out.println("get all books successful");
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("get all books failed");
                callback.onError(e);
            }
        });
    }

    public void getSearchBooks(String title,DAOExecuteCallback<List<Book>> callback) {
        BookSyncDAO.searchBookByTitleSync(title, new DAOExecuteCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }
        });
    }

    public void addBook(String ISBN, String title, String author, String publisher,
                        Date publicationDate, int quantity, String description,
                        String coverImagePath, ArrayList<GenreType> genreList,
                        DAOUpdateCallback callback) {

        if (ISBN == null || title == null || author == null || publisher == null || publicationDate == null
                || quantity < 0 || description == null || coverImagePath == null || genreList == null
                || ISBN.length() < 10 || ISBN.length() > 30 || title.length() > 100 || author.length() > 100
                || publisher.length() > 100 || coverImagePath.length() > 256 || genreList.isEmpty()) {

            callback.onError(new RuntimeException("Invalid input"));
            return;
        }

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
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
            }

            @Override
            protected void failed() {
                System.out.println("Book add failed");
                callback.onError(new RuntimeException("add Book failed"));
            }

            @Override
            protected void succeeded() {
                System.out.println("Book added successfully");
                callback.onSuccess();
            }
        };
        new Thread(task).start();
    }

    public void updateBook(Book book, DAOUpdateCallback callback) {
        if (book.getISBN().isEmpty() || book.getTitle().isEmpty() || book.getAuthor().isEmpty()
                || book.getPublisher().isEmpty() || book.getPublicationDate() == null || book.getQuantity() < 0
                || book.getDescription().isEmpty() || book.getCoverImagePath().isEmpty() || book.getGenreList().isEmpty()
                || book.getISBN().length() < 10 || book.getISBN().length() > 30 || book.getTitle().length() > 100
                || book.getAuthor().length() > 100 || book.getPublisher().length() > 100
                || book.getCoverImagePath().length() > 256) {

            callback.onError(new RuntimeException("Invalid input"));
            return;
        }

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                Book bookOld = BookDAO.getBookById(book.getBookId());
                assert bookOld != null;
                boolean exist = (BookDAO.getBookByISBN(book.getISBN()) != null);
                if (!Objects.equals(bookOld.getISBN(), book.getISBN()) && exist) {
                    callback.onError(new RuntimeException("Book exists"));
                }

                BookDAO.updateBook(book);
                return null;
            }

            @Override
            protected void succeeded() {
                System.out.println("Update book successfully");
                callback.onSuccess();
            }

            @Override
            protected void failed() {
                System.out.println("Update book failed");
                callback.onError(new RuntimeException("Update book failed"));
            }
        };
        new Thread(task).start();
    }

    public void deleteBook(Book book, DAOUpdateCallback callback) {
        BookSyncDAO.deleteBookByIdSync(book.getBookId(), new DAOUpdateCallback() {
            @Override
            public void onSuccess() {
                System.out.println("Book deleted successfully");
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Book deletion failed");
                callback.onError(new RuntimeException("Book deletion failed"));
            }
        });
    }
}
