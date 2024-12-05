package dha.libapp.controllers.admin.tabs;

import dha.libapp.services.admin.UserService;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import dha.libapp.models.Book;
import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import dha.libapp.services.admin.tabs.AdminBorrowRequestService;
import dha.libapp.services.admin.tabs.AdminReturnRequestService;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminBorrowRequestController implements Initializable {
    private static AdminBorrowRequestController instance;

    public static AdminBorrowRequestController getInstance() {
        return instance;
    }

    @FXML
    private Button addBorrowRequest;

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

    private void openFieldBox() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Add new user");
        alert.setHeaderText("Enter new user name:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField username = createStyledTextField("Username");

        TextField password = createStyledTextField("Password");

        TextField fullName = createStyledTextField("Full Name");

        TextField phoneNumber = createStyledTextField("Phone Number");

        TextField email = createStyledTextField("Email");

        //add user
        javafx.scene.control.Button addButton = new javafx.scene.control.Button("Add");
        addButton.setStyle("-fx-font-size: 15px; -fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            String usernameText = username.getText();
            String passwordText = password.getText();
            String fullNameText = fullName.getText();
            String phoneNumberText = phoneNumber.getText();
            String emailText = email.getText();
            List<Throwable> holder = new ArrayList<>();
            UserService.getInstance().addUser(usernameText, passwordText, fullNameText, phoneNumberText,
                    emailText, new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            // controller
                            showSuccessPopup("Add User Success", "User Added Successfully");
                        }
                        @Override
                        public void onError(Throwable e) {
                            // controller
                            showErrorPopup("Error Adding User", "Got: " + e.getMessage() + "\nPlease try again");
                        }
                    });
        });

        gridPane.add(createStyledLabel("Username:"), 0, 0);
        gridPane.add(username, 1, 0);

        gridPane.add(createStyledLabel("Password:"), 0, 1);
        gridPane.add(password, 1, 1);

        gridPane.add(createStyledLabel("Full Name:"), 0, 2);
        gridPane.add(fullName, 1, 2);

        gridPane.add(createStyledLabel("Phone Number:"), 0, 3);
        gridPane.add(phoneNumber, 1, 3);

        gridPane.add(createStyledLabel("Email:"), 0, 4);
        gridPane.add(email, 1, 4);

        gridPane.add(addButton, 0, 10);


        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);

        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");

        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

    }
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return textField;
    }

    private Text createStyledLabel(String labelText) {
        Text text = new Text(labelText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        text.setStyle("-fx-fill: #333;");
        return text;
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

    private void initializeButton() {
        addBorrowRequest.setOnAction(event -> {

        });
    }

    private void addBorrowRequest() {

    }

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
