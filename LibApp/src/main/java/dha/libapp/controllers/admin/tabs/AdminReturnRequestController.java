package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.tabs.AdminApproveRequestService;
import dha.libapp.services.admin.tabs.AdminReturnRequestService;
import dha.libapp.services.members.tabs.MemberReturnedTabService;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.utils.ListView.BookListView;
import dha.libapp.utils.ListView.BorrowListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminReturnRequestController implements Initializable {
    private static AdminReturnRequestController instance;

    public static AdminReturnRequestController getInstance() {
        return instance;
    }

    @FXML
    private Label userFullName;

    @FXML
    private ListView<BorrowRecord> returnRequestListView;

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

        AdminReturnRequestService.renderReturnedBooks();

//        returnRequestListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                Book selected = (Book) newValue;
//                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
//                this.setBookDetailView(selected);
//                this.selectedBook = selected;
//            }
//        });
    }

    public void renderReturnedBooks(List<BorrowRecord> returnedBooks) {
        BorrowListView.renderToListView(returnRequestListView, returnedBooks);
    }

    public void setInformationDetail(BorrowRecord selected) {
        AdminReturnRequestService.getInfoBorrow(selected, new DAOExecuteCallback<AdminApproveRequestService.BorrowInfo>() {
            @Override
            public void onSuccess(AdminApproveRequestService.BorrowInfo result) {
                userId.setText("User ID: " + result.user.getUserId());
                fullName.setText("User full name: " + result.user.getFullName());
                titleBook.setText("Book title: " + result.book.getTitle());
                authorBook.setText("Book author: " + result.book.getAuthor());
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void setLoadingPaneVisible(boolean visible) {
        loadingPane.setVisible(visible);
    }

    public void setReturnedListViewVisible(boolean visible) {
        returnRequestListView.setVisible(visible);
    }
}
