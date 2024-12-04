package dha.libapp.controllers.members.tabs;

import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.tabs.MemberReturnedTabService;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MemberReturnedTabController implements Initializable {
    private static MemberReturnedTabController instance;

    public static MemberReturnedTabController getInstance() {
        return instance;
    }

    @FXML
    private Label userFullName;

    @FXML
    private ListView<Book> returnedListView;

    @FXML
    private Pane loadingPane;

    @FXML
    private Label bookDetailName;

    @FXML
    private Label bookDetailAuthor;

    @FXML
    private Label bookDetailDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        loadingPane.setVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        MemberReturnedTabService.renderReturnedBooks();

        returnedListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
            }
        });
    }

    public void renderReturnedBooks(List<Book> returnedBooks) {
        BookListView.renderToListView(returnedListView, returnedBooks);
    }

    public void setBookDetailView(Book book) {
        bookDetailName.setText(book.getTitle());
        bookDetailAuthor.setText(book.getAuthor());
        bookDetailDescription.setText(book.getDescription());
    }

    public void setLoadingPaneVisible(boolean visible) {
        loadingPane.setVisible(visible);
    }

    public void setReturnedListViewVisible(boolean visible) {
        returnedListView.setVisible(visible);
    }
}
