package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.tabs.MemberHomeTabService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.Image.ImageAPI;
import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private Pane searchLoadingPane;

    @FXML
    private ListView<Book> searchBookListView;

    @FXML
    private AnchorPane searchBox;

    @FXML
    private ImageView closeSearchBox;

    @FXML
    private TextField searchInput;

    @FXML
    private Button searchBtn;

    public void setSearchLoadingPaneVisible(boolean visible) {
        searchLoadingPane.setVisible(visible);
    }

    public void setSearchBoxVisible(boolean visible) {
        searchBox.setVisible(visible);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        setLoadingTrendingPaneVisible(true);
        setLoadingRecommendationPaneVisible(true);

        setSearchLoadingPaneVisible(false);
        setSearchBoxVisible(false);

        closeSearchBox.setOnMouseClicked(e -> {
            setSearchBoxVisible(false);
        });

        searchBtn.setOnMouseClicked(e -> {
            setSearchLoadingPaneVisible(true);
            setSearchBoxVisible(true);
            String search = searchInput.getText();
            BookSyncDAO.searchBookByTitleSync(search, new DAOExecuteCallback<List<Book>>() {
                @Override
                public void onSuccess(List<Book> result) {
                    setSearchLoadingPaneVisible(false);
                    BookListView.renderToListView(searchBookListView, result);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        });

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

        searchBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
