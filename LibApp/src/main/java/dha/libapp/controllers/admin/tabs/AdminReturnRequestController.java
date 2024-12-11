package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.BorrowRecordService;
import dha.libapp.services.admin.tabs.AdminApproveRequestService;
import dha.libapp.services.admin.tabs.AdminReturnRequestService;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import dha.libapp.utils.ListView.BorrowListView;
import dha.libapp.utils.ListView.UserListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminReturnRequestController implements Initializable {
    private static AdminReturnRequestController instance;

    public static AdminReturnRequestController getInstance() {
        return instance;
    }

    @FXML
    private Button acceptButton;

    @FXML
    private Label userFullName;

    @FXML
    private ListView<BorrowRecord> returnRequestListView;

    @FXML
    private Pane loadingPane;

    @FXML
    private Label userId;

    @FXML
    private Label username;

    @FXML
    private Label fullName;

    @FXML
    private Label titleBook;

    @FXML
    private Label authorBook;

    @FXML
    private BorrowRecord selectBorrow;

    @FXML
    private Label borrowDate;

    @FXML
    private Label dueDate;

    @FXML
    private Pane searchLoadingPane;

    @FXML
    private ListView<BorrowRecord> searchBorrowRecordListView;

    @FXML
    private AnchorPane searchBox;

    @FXML
    private ImageView closeSearchBox;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView searchBtn;

    public void setSearchLoadingPaneVisible(boolean visible) {
        searchLoadingPane.setVisible(visible);
    }

    public void setSearchBoxVisible(boolean visible) {
        searchBox.setVisible(visible);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        initializeButtons();

        loadingPane.setVisible(true);

        userFullName.setText(SessionService.getInstance().getUser().getFullName());

        AdminReturnRequestService.renderReturnedBooks();

        returnRequestListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BorrowRecord selected = (BorrowRecord) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setInformationDetail(selected);
                this.selectBorrow = selected;
            }
        });

        // Search bar
        setSearchLoadingPaneVisible(false);
        setSearchBoxVisible(false);

        closeSearchBox.setOnMouseClicked(e -> {
            setSearchBoxVisible(false);
        });

        searchBtn.setOnMouseClicked(e -> {
            setSearchLoadingPaneVisible(true);
            setSearchBoxVisible(true);
            String search = searchInput.getText();

            BorrowRecordService.getInstance().getSearchBorrowRecordsByUsernameAndStatus(search, BorrowStatus.BORROWED,
                    new DAOExecuteCallback<List<BorrowRecord>>() {
                @Override
                public void onSuccess(List<BorrowRecord> result) {
                    setSearchLoadingPaneVisible(false);
                    BorrowListView.renderToListView(searchBorrowRecordListView, result);
                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }
            });
        });

        searchBorrowRecordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BorrowRecord selected = (BorrowRecord) newValue;
                this.setInformationDetail(selected);
                this.selectBorrow = selected;
            }
        });
    }

    private void initializeButtons() {
        acceptButton.setOnAction(event -> {
            showConfirmAcceptPopup("Confirm Return Request", "Please Confirm Return Request", selectBorrow);
        });
    }

    public void renderReturnedBooks(List<BorrowRecord> returnedBooks) {
        BorrowListView.renderToListView(returnRequestListView, returnedBooks);
    }

    public void setInformationDetail(BorrowRecord selected) {
        AdminReturnRequestService.getInfoBorrow(selected, new DAOExecuteCallback<AdminApproveRequestService.BorrowInfo>() {
            @Override
            public void onSuccess(AdminApproveRequestService.BorrowInfo result) {
                userId.setText("User ID: " + result.user.getUserId());
                username.setText("Username: " + result.user.getUserName());
                fullName.setText("User full name: " + result.user.getFullName());
                titleBook.setText("Book title: " + result.book.getTitle());
                authorBook.setText("Book author: " + result.book.getAuthor());
                borrowDate.setText("Borrow date: " + selected.getBorrowDate());
                dueDate.setText("Due date: " + selected.getDueDate());
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
    private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom styling if needed
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-background-color: #fff; -fx-border-radius: 10; -fx-background-radius: 10;");

        alert.showAndWait();
    }
    private void showSuccessPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom styling if needed
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-background-color: #fff; -fx-border-radius: 10; -fx-background-radius: 10;");

        alert.showAndWait();
    }
    private void showConfirmAcceptPopup(String header, String message, BorrowRecord borrowRecord) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        DAOUpdateCallback callback = new DAOUpdateCallback() {
            @Override
            public void onSuccess() {
                showSuccessPopup("Return Request Accepted", "Return Request Accepted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                showErrorPopup("Return Request Accept Error", "Got: " + e.getMessage() + "\nPlease try again");
            }
        };
        // Handle the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose OK");
            BorrowRecordService.getInstance().returnedBorrow(borrowRecord, callback);
        } else {
            System.out.println("User chose Cancel or closed the dialog");
        }
    }
}
