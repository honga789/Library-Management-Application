package dha.libapp.syncdao;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

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

    public static void updateBookSync(Book book, DAOUpdateCallback callback) {

    }

}
