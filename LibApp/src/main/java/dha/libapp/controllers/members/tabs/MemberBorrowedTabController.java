package dha.libapp.controllers.members.tabs;

import dha.libapp.models.Book;
import dha.libapp.services.members.tabs.MemberBorrowedTabService;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MemberBorrowedTabController implements Initializable {
    private static MemberBorrowedTabController instance;

    public static MemberBorrowedTabController getInstance() {
        return instance;
    }

    @FXML
    private ListView<Book> borrowedListView;

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

        MemberBorrowedTabService.renderBorrowedBooks();

        borrowedListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
            }
        });
    }

    public void renderBorrowedBooks(List<Book> borrowedBooks) {
        BookListView.renderToListView(borrowedListView, borrowedBooks);
    }

    public void setBookDetailView(Book book) {
        bookDetailName.setText(book.getTitle());
        bookDetailAuthor.setText(book.getAuthor());
        bookDetailDescription.setText(book.getDescription());
    }

    public void setLoadingPaneVisible(boolean visible) {
        loadingPane.setVisible(visible);
    }

    public void setBorrowedListViewVisible(boolean visible) {
        borrowedListView.setVisible(visible);
    }
}
