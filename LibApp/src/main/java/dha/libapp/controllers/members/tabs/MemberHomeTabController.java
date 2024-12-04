package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.tabs.MemberHomeTabService;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.Image.ImageAPI;
import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MemberHomeTabController implements Initializable {
    private static MemberHomeTabController instance;
    public static MemberHomeTabController getInstance() { return instance; }

    @FXML
    private Label userFullName;

    @FXML
    private ListView<Book> topTrendingListView;

    @FXML
    private ListView<Book> recommendationListView;

    @FXML
    private Pane loadingTrendingPane;

    @FXML
    private Pane loadingRecommendationPane;

    @FXML
    private Label bookDetailName;

    @FXML
    private Label bookDetailAuthor;

    @FXML
    private Label bookDetailDescription;

    @FXML
    private ImageView bookDetailImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        setLoadingTrendingPaneVisible(true);
        setLoadingRecommendationPaneVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        MemberHomeTabService.renderRecommendationBooks();
        MemberHomeTabService.renderTopTrendingBooks();

        recommendationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
            }
        });

        topTrendingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
            }
        });
    }

    public void setBookDetailView(Book book) {
        bookDetailName.setText(book.getTitle());
        bookDetailAuthor.setText(book.getAuthor());
        bookDetailDescription.setText(book.getDescription());

        ImageTask imageTask = ImageAPI.getImageWithUrl(book.getCoverImagePath(), new ImageFetchCallback() {
            @Override
            public void onSuccess(Image image) {
                bookDetailImage.setImage(image);
            }

            @Override
            public void onFailure(Exception ex) {

            }
        });

        ExecutorHandle.getInstance().addTask(imageTask);

    }

    public void renderRecommendationBooks(List<Book> bookList) {
        BookListView.renderToListView(recommendationListView, bookList);
    }

    public void renderTrendingBooks(List<Book> bookList) {
        BookListView.renderToListView(topTrendingListView, bookList);
    }

    public void setLoadingTrendingPaneVisible(boolean visible) {
        loadingTrendingPane.setVisible(visible);
    }

    public void setLoadingRecommendationPaneVisible(boolean visible) {
        loadingRecommendationPane.setVisible(visible);
    }
}
