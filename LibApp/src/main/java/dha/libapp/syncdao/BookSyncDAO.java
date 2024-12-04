package dha.libapp.syncdao;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookSyncDAO {
    public static void getAllBookSync(DAOExecuteCallback<List<Book>> callback) {
        Task<List<Book>> task = new Task<List<Book>>() {
            @Override
            protected List<Book> call() throws Exception {
                return BookDAO.getAllBook();
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getBookByIdSync(int bookId, DAOExecuteCallback<Book> callback) {
        Task<Book> task = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                return BookDAO.getBookById(bookId);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getBookByISBNSync(String ISBN, DAOExecuteCallback<Book> callback) {
        Task<Book> task = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                return BookDAO.getBookByISBN(ISBN);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getDeletedBookByIdSync(int bookId, DAOExecuteCallback<Book> callback) {
        Task<Book> task = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                return BookDAO.getDeletedBookById(bookId);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getDeletedBookByISBNSync(String ISBN, DAOExecuteCallback<Book> callback) {
        Task<Book> task = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                return BookDAO.getDeletedBookByISBN(ISBN);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void addNewBookSync(String ISBN, String title, String author, String publisher,
                                      Date publicationDate, int quantity, String description,
                                      String coverImagePath, ArrayList<GenreType> genreList,
                                      DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BookDAO.addNewBook(ISBN, title, author, publisher, publicationDate, quantity,
                        description, coverImagePath, genreList);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void updateBookSync(int book_id, String ISBN, String title, String author, String publisher,
                                      Date publicationDate, int quantity, String description,
                                      String coverImagePath, ArrayList<GenreType> genreList,
                                      DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BookDAO.updateBook(book_id, ISBN, title, author, publisher, publicationDate, quantity,
                        description, coverImagePath, genreList);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void updateBookSync(Book book, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BookDAO.updateBook(book);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void deleteBookByIdSync(int book_id, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BookDAO.deleteBookById(book_id);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void deleteBookByISBNSync(String ISBN, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                BookDAO.deleteBookByISBN(ISBN);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    public static void searchBookByTitleSync(String title, DAOExecuteCallback<List<Book>> callback) {
        Task<List<Book>> task = new Task<List<Book>>() {

            @Override
            protected List<Book> call() throws Exception {
                return BookDAO.searchBookByTitle(title);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }
}
