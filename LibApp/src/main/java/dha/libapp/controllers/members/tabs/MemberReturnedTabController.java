package dha.libapp.controllers.members.tabs;

import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.UserBorrowBookService;
import dha.libapp.services.members.tabs.MemberReturnedTabService;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.Image.ImageAPI;
import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView bookDetailImage;

    @FXML
    private Button borrowBookBtn;

    @FXML
    private Book selectedBook;

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
                this.selectedBook = selected;
            }
        });

        borrowBookBtn.setOnMouseClicked(e -> {
            if (selectedBook == null) {
                System.out.println("Please choose Book!");
            } else {
                UserBorrowBookService.borrowBook(selectedBook);
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

        ImageTask imageTask = ImageAPI.getImageWithUrl(book.getCoverImagePath(), new ImageFetchCallback() {
            @Override
            public void onSuccess(Image image) {
                bookDetailImage.setImage(image);
            }

            @Override
            public void onFailure(Exception ex) {
                URL resourceUrl = getClass().getResource("/dha/libapp/images/book_loader.jpg");

                if (resourceUrl != null) {
                    Image defaultImage = new Image(resourceUrl.toExternalForm());
                    bookDetailImage.setImage(defaultImage);
                } else {
                    System.out.println("Dont't find default resources.");
                }
            }
        });

        ExecutorHandle.getInstance().addTask(imageTask);
    }

    public void setLoadingPaneVisible(boolean visible) {
        loadingPane.setVisible(visible);
    }

    public void setReturnedListViewVisible(boolean visible) {
        returnedListView.setVisible(visible);
    }
}
