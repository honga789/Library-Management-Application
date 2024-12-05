package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.services.admin.BookService;
import dha.libapp.services.admin.UserService;
import dha.libapp.syncdao.UserSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.GoogleBooks.BookFetchCallback;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksAPI;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksTask;
import dha.libapp.models.User;
import dha.libapp.utils.ListView.UserListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminManageUserController implements Initializable {

    @FXML
    private javafx.scene.control.Button addUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button editUserButton;
    private javafx.scene.control.Label editStatus = new javafx.scene.control.Label();

    @FXML
    private Pane loadingPane;

    @FXML
    private ListView<User> manageUserListView;

    private User selectedUser;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("user");

        initializeButton();

        manageUserListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                User selected = (User) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
                this.selectedUser = selected;
            }
        });

        loadingPane.setVisible(true);
        UserSyncDAO.getAllUserSync(new DAOExecuteCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                loadingPane.setVisible(false);
                UserListView.renderToListView(manageUserListView, result);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void setBookDetailView(User user) {

    }

    private void initializeButton() {
        addUserButton.setOnAction(event -> {
            openFieldBox();
        });
        editUserButton.setOnAction(event -> {
            //openFieldBoxForEdit();
        });
    }

    private void openFieldBoxForEdit(User user) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Edit user");
        alert.setHeaderText("Enter new user data:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField username = createStyledTextField("Username");
        username.setPromptText(user.getUserName());
        TextField password = createStyledTextField("Password");
        password.setPromptText(user.getPassword());
        TextField fullName = createStyledTextField("Full Name");
        fullName.setPromptText(user.getFullName());
        TextField phoneNumber = createStyledTextField("Phone Number");
        phoneNumber.setPromptText(user.getPhoneNumber());
        TextField email = createStyledTextField("Email");
        email.setPromptText(user.getEmail());
        //add user
        javafx.scene.control.Button addButton = new javafx.scene.control.Button("Confirm Edit");
        addButton.setStyle("-fx-font-size: 15px; -fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            String usernameText = username.getText();
            String passwordText = password.getText();
            String fullNameText = fullName.getText();
            String phoneNumberText = phoneNumber.getText();
            String emailText = email.getText();
            try {
                UserService.getInstance().updateUser(user.getUserId(), passwordText, fullNameText, phoneNumberText, emailText);
            } catch (Exception e) {
                showErrorPopup("Error Editing User","Please enter valid user data");
            }
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

        gridPane.add(editStatus, 0, 11);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);

        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");

        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();


    }

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
            UserService.getInstance().addUser(usernameText, passwordText, fullNameText, phoneNumberText,
                    emailText, new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            // controller
                        }

                        @Override
                        public void onError(Throwable e) {
                            // controller
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

        gridPane.add(editStatus, 0, 11);

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
    //text field

}
