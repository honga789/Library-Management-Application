package dha.libapp.controllers.admin.tabs;

import dha.libapp.controllers.admin.AdminViewController;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.BorrowRecordService;
import dha.libapp.services.admin.UserService;
import dha.libapp.services.admin.tabs.AdminApproveRequestService;
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
import java.util.*;

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
    private Label username;

    @FXML
    private Label fullName;

    @FXML
    private Label titleBook;

    @FXML
    private Label authorBook;

    @FXML
    private Label borrowDate;

    @FXML
    private Label dueDate;

    private BorrowRecord selectBorrow;

    @FXML
    private Button cancelButton;

    @FXML
    private Button approveButton;

    @FXML
    private BorrowRecord tempBorrowRecord;

    @FXML
    private Pane searchLoadingPane;

    @FXML
    private ListView<BorrowRecord> searchApproveListView;

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

        initializeButton();
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

            BorrowRecordService.getInstance().getSearchBorrowRecordsByUsernameAndStatus(search, BorrowStatus.PENDING,
                    new DAOExecuteCallback<List<BorrowRecord>>() {
                @Override
                public void onSuccess(List<BorrowRecord> result) {
                    setSearchLoadingPaneVisible(false);
                    BorrowListView.renderToListView(searchApproveListView, result);
                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }
            });
        });

        searchApproveListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BorrowRecord selected = (BorrowRecord) newValue;
                this.setInformationDetail(selected);
                this.selectBorrow = selected;
            }
        });
    }

    public void renderApproveBooks(List<BorrowRecord> approveBooks) {
        BorrowListView.renderToListView(approveRequestListView, approveBooks);
    }

    public void setInformationDetail(BorrowRecord selected) {
        AdminApproveRequestService.getInfoBorrow(selected, new DAOExecuteCallback<AdminApproveRequestService.BorrowInfo>() {
            @Override
            public void onSuccess(AdminApproveRequestService.BorrowInfo result) {
                username.setText("Username: " + result.user.getUserName());
                fullName.setText("User full name: " + result.user.getFullName());
                titleBook.setText("Book title: " + result.book.getTitle());
                authorBook.setText("Book author: " + result.book.getAuthor());
                borrowDate.setText("Borrow date: " + selected.getBorrowDate());
                dueDate.setText("Due date: " + selected.getDueDate());
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException();
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

        approveButton.setOnMouseClicked(e -> {
            if (selectBorrow != null) {
                System.out.println("clicked");
                BorrowRecordService.getInstance().acceptBorrow(selectBorrow, new DAOUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        showSuccessPopup("Request Approved", "Approved Successfully");
                        AdminViewController.getInstance().switchToApproveRequestTab();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorPopup("Cannot Approve Request", "Got: " + e.getMessage());
                    }
                });
            } else {
                showErrorPopup("No Borrow Record Selected", "Please select a Borrow Record");
            }
        });
        cancelButton.setOnMouseClicked(e -> {
            if (selectBorrow != null) {
                System.out.println("clicked");
                BorrowRecordService.getInstance().deniedBorrow(selectBorrow, new DAOUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        showSuccessPopup("Request Cancelled", "Request Cancelled Successfully");
                        AdminViewController.getInstance().switchToApproveRequestTab();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorPopup("Cannot Cancel Request", "Got: " + e.getMessage());
                    }
                });
            } else {
                showErrorPopup("No Borrow Record Selected", "Please select a Borrow Record");
            }
        });
    }

    public void approveBorrow(BorrowRecord borrowRecord) {
        System.out.println("approve");
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
    private void showConfirmPopup(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose OK");
        } else {
            System.out.println("User chose Cancel or closed the dialog");
        }
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
}
