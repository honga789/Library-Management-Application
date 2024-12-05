package dha.libapp.controllers.admin.tabs;

import dha.libapp.controllers.admin.AdminViewController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.BorrowService;
import dha.libapp.services.admin.tabs.AdminApproveRequestService;
import dha.libapp.services.admin.tabs.AdminReturnRequestService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import dha.libapp.utils.ListView.BorrowListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminApproveRequestController implements Initializable {
    private static AdminApproveRequestController instance;

    public static AdminApproveRequestController getInstance() {
        return instance;
    }

    @FXML
    private Label userFullName;

    @FXML
    private ListView<BorrowRecord> approveRequestListView;

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

    private BorrowRecord selectBorrow;

    @FXML
    private Button approveButton;
    private BorrowRecord tempBorrowRecord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        loadingPane.setVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        AdminApproveRequestService.renderApproveBooks();

        approveRequestListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BorrowRecord selected = (BorrowRecord) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setInformationDetail(selected);
                this.selectBorrow = selected;
            }
        });

        approveButton.setOnMouseClicked(e -> {
            System.out.println("clicked");
            BorrowService.getInstance().acceptBorrow(selectBorrow, new DAOUpdateCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("Approve button");
                    AdminViewController.getInstance().switchToApproveRequestTab();
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Approve false");
                }
            });
        });


    }

    public void renderApproveBooks(List<BorrowRecord> approveBooks) {
        BorrowListView.renderToListView(approveRequestListView, approveBooks);
    }

    public void setInformationDetail(BorrowRecord selected) {
//        userId.setText(user.getUserId() + "");
//        fullName.setText(user.getFullName());
//        titleBook.setText(book.getTitle());
//        authorBook.setText(book.getAuthor());
        AdminApproveRequestService.getInfoBorrow(selected, new DAOExecuteCallback<AdminApproveRequestService.BorrowInfo>() {
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

    public void setApproveListViewVisible(boolean visible) {
        approveRequestListView.setVisible(visible);
    }

    private void initializeButton() {
        approveButton.setOnAction(event -> {
            approveBorrow(tempBorrowRecord);
        });
    }

    public void approveBorrow(BorrowRecord borrowRecord) {
        System.out.println("approve");
    }
}
