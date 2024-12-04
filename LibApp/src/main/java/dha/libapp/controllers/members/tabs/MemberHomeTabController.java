package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
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
        setLoadingTrendingPaneVisible(true);
        setLoadingRecommendationPaneVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        List<Book> allBook = BookDAO.getAllBook();
        List<String> allStringBook = new ArrayList<>();
        for (Book b : allBook) { allStringBook.add(b.toString()); }

        topTrendingListView.getItems().addAll(allStringBook);

        topTrendingListView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    }

    public void setLoadingTrendingPaneVisible(boolean visible) {
        loadingTrendingPane.setVisible(visible);
    }

    public void setLoadingRecommendationPaneVisible(boolean visible) {
        loadingRecommendationPane.setVisible(visible);
    }
}
