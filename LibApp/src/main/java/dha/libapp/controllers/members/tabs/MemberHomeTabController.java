package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.tabs.MemberHomeTabService;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    private ListView<String> topTrendingListView;

    @FXML
    private ListView<String> recommendationListView;

    @FXML
    private Pane loadingTrendingPane;

    @FXML
    private Pane loadingRecommendationPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        setLoadingTrendingPaneVisible(true);
        setLoadingRecommendationPaneVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        MemberHomeTabService.renderRecommendationBooks();

    }

    public void renderRecommendationBooks(List<Book> bookList) {
        BookListView.renderToListView(recommendationListView, bookList);
    }

    public void setLoadingTrendingPaneVisible(boolean visible) {
        loadingTrendingPane.setVisible(visible);
    }

    public void setLoadingRecommendationPaneVisible(boolean visible) {
        loadingRecommendationPane.setVisible(visible);
    }
}
