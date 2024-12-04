package dha.libapp.services.members;

import dha.libapp.models.Book;
import dha.libapp.models.User;
import javafx.concurrent.Task;
import java.util.List;

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
