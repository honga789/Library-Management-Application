package dha.libapp.services.members;

import dha.libapp.MainApp;
import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.GenreType;
import dha.libapp.models.User;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.utils.Database.DBUtil;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecommendationService {

    public interface RecommendCallback {
        void onSuccess(List<Book> recommendedBook);
    }

    public static void getRecommendedBooksForUser(User user, RecommendCallback recommendCallback) {

        Task<List<Book>> task = new Task<List<Book>>() {
            @Override
            protected List<Book> call() throws Exception {

                

                return null;
            }

            @Override
            protected void succeeded() {
                System.out.println("ok");
                recommendCallback.onSuccess(getValue());
            }
        };

        new Thread(task).start();

    }

    public static void main(String[] args) {
        // Test chức năng với một user giả
        getRecommendedBooksForUser(new User(), recommendedBook -> {
            System.out.println("Recommended Books: " + recommendedBook.size());
        });
    }
}
