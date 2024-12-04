package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.services.admin.BookService;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.GoogleBooks.BookFetchCallback;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksAPI;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksTask;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AdminManageUserController {

    @FXML
    private javafx.scene.control.Button addUserButton;
    private javafx.scene.control.Label editStatus = new javafx.scene.control.Label();

    @FXML
    public void initialize() {
        initializeButton();
    }

    private void initializeButton() {
        addUserButton.setOnAction(event -> {
            openFieldBox();
        });
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

    //text field

}
