package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.Book;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.tabs.AdminBorrowRequestService;
import dha.libapp.services.admin.tabs.AdminReturnRequestService;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminBorrowRequestController implements Initializable {
    private static AdminBorrowRequestController instance;

    public static AdminBorrowRequestController getInstance() {
        return instance;
    }

    @FXML
    private Label userFullName;

    @FXML
    private ListView<Book> borrowRequestListView;

    @FXML
    private Pane loadingPane;

    @FXML
    private Label userId;

    @FXML
    private Label fullName;

    @FXML
    private Label titleBook;

    @FXML
    private Label authorBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        loadingPane.setVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        AdminBorrowRequestService.renderBorrowBooks();

//        returnRequestListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                Book selected = (Book) newValue;
//                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
//                this.setBookDetailView(selected);
//                this.selectedBook = selected;
//            }
//        });
    }

    public void renderBorrowBooks(List<Book> borrowBooks) {
        BookListView.renderToListView(borrowRequestListView, borrowBooks);
    }

    public void setInformationDetail(Book book, User user) {
        userId.setText(user.getUserId() + "");
        fullName.setText(user.getFullName());
        titleBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
    }

    public void setLoadingPaneVisible(boolean visible) {
        loadingPane.setVisible(visible);
    }

    public void setBorrowListViewVisible(boolean visible) {
        borrowRequestListView.setVisible(visible);
    }
}
