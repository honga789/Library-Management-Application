package dha.libapp.syncdao;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
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

}
