package dha.libapp.services.members;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.BorrowRecordDAO;
import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.GenreType;
import dha.libapp.models.User;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecommendationService {

    public interface RecommendCallback {
        void onSuccess(List<Book> recommendedBook);
    }

    public static void getRecommendedBooksForUser(User user, RecommendCallback recommendCallback, int maxRecommended) {

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

                List<Integer> userVector = vectors.get(user.getUserId());
                double userVectorMagnitude = 0;
                for (int i = 0; i < n; i++) {
                    userVectorMagnitude += weights.get(i) * userVector.get(i) * userVector.get(i);
                }
                userVectorMagnitude = Math.sqrt(userVectorMagnitude);

                if (userVectorMagnitude == 0) {
                    return BookDAO.getAllBook().subList(0, maxRecommended);
                }

                HashMap<Integer, Double> cosineSimilarities = new HashMap<>();
                double finalUserVectorMagnitude = userVectorMagnitude;
                vectors.forEach((key, otherVector) -> {
                    if (key != user.getUserId()) {
                        double productDot = 0;
                        for (int i = 0; i < n; i++) {
                            productDot += weights.get(i) * userVector.get(i) * otherVector.get(i);
                        }
                        double vectorMagnitude = 0;
                        for (int i = 0; i < n; i++) {
                            vectorMagnitude += weights.get(i) * otherVector.get(i) * otherVector.get(i);
                        }
                        vectorMagnitude = Math.sqrt(vectorMagnitude);

                        double cosineSimilarity = 0;
                        if (finalUserVectorMagnitude * vectorMagnitude != 0) {
                            cosineSimilarity = productDot / (finalUserVectorMagnitude * vectorMagnitude);
                        }

                        cosineSimilarities.put(key, cosineSimilarity);
                    }
                });

                cosineSimilarities.put(0, 0.0);

                System.out.println("Cosines:");
                cosineSimilarities.forEach((key, value) -> {
                    System.out.println(key + ": " + value);
                });

                List<Integer> sortedKeys = cosineSimilarities.entrySet()
                        .stream()
                        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // sort by value
                        .map(Map.Entry::getKey) // get key from Map.Entry
                        .collect(Collectors.toList()); // change to List<Integer>

                System.out.println("Size: " + sortedKeys.size());
                System.out.println(sortedKeys);

                List<Book> recommendedBooks = new ArrayList<>();
                List<BorrowRecord> brs = BorrowRecordDAO.getAllBorrowRecordsByUserId(user.getUserId());

                int i = 0;
                while (recommendedBooks.size() < maxRecommended && i < sortedKeys.size()) {
                    List<BorrowRecord> borrowRecords = BorrowRecordDAO.getAllBorrowRecordsByUserId(sortedKeys.get(i));
                    for (BorrowRecord borrowRecord : borrowRecords) {
                        Book book = BookDAO.getBookById(borrowRecord.getBookId());

                        if (book == null) {
                            continue;
                        }

                        boolean isBorrowed = false;
                        for (BorrowRecord br : brs) {
                            if (br.getBookId() == book.getBookId()) {
                                isBorrowed = true;
                            }
                        }

                        if (!isBorrowed) recommendedBooks.add(book);
                        if (recommendedBooks.size() >= maxRecommended) {
                            break;
                        }
                    }
                    i++;
                }

                System.out.println("Size: " + recommendedBooks.size());

                System.out.println(recommendedBooks);

                return recommendedBooks;
            }

            @Override
            protected void succeeded() {
                System.out.println("ok");
                recommendCallback.onSuccess(getValue());
            }

            @Override
            protected void failed() {
                System.out.println("failed");
                throw new RuntimeException(getException().getMessage());
            }
        };

        new Thread(task).start();

    }
}

