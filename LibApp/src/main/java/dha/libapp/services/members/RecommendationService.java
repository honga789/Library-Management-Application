package dha.libapp.services.members;

import dha.libapp.MainApp;
import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.dao.GenreTypeDAO;
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
import java.util.Collections;
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
                List<GenreType> genreTypeList = GenreTypeDAO.getAllGenreType();
                List<BorrowRecord> borrowRecordList = BorrowRecordDAO.getAllBorrowRecords();
                HashMap<Integer, ArrayList<Integer>> vectors = new HashMap<>();
                int n = genreTypeList.size();

                for (BorrowRecord borrowRecord : borrowRecordList) {
                    int userId = borrowRecord.getUserId();
                    Book book = BookDAO.getBookById(borrowRecord.getBookId());
                    if (book == null) {
                        continue;
                    }

                    if (!vectors.containsKey(userId)) {
                        ArrayList<Integer> list = new ArrayList<>();
                        for (int i = 0; i < n; ++i) {
                            list.add(0);
                        }
                        vectors.put(userId, list);
                    }

                    for (GenreType genreType : book.getGenreList()) {
                        int id = genreType.getGenreId() - 1;
                        int val = vectors.get(userId).get(id);
                        vectors.get(userId).set(id, val + 1);
                    }
                }

                ArrayList<Float> weights = new ArrayList<>();
                for (GenreType genreType : genreTypeList) {
                    weights.add(genreType.getWeight());
                }
                System.out.println(weights);

                vectors.forEach((key, value) -> {
                    System.out.println(key + ": " + value);
                });

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
