package dha.libapp.controllers.authen;

import dha.libapp.MainAppController;
import dha.libapp.services.authen.RegisterService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    private static RegisterController instance;

    public static RegisterController getInstance() {
        return instance;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Label emptyFieldsLabel;

    @FXML
    private Label invalidInputLabel;

    @FXML
    private Label duplicateUsernameLabel;

    @FXML
    private Label passwordTooShortLabel;

    @FXML
    private Label failedRegisterLabel;

    @FXML
    public void handleRegisterAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        RegisterService.register(username, password, fullName, phone, email);
    }

    public void onEmptyInput() {
        invalidInputLabel.setVisible(false);
        duplicateUsernameLabel.setVisible(false);
        passwordTooShortLabel.setVisible(false);
        failedRegisterLabel.setVisible(false);
        emptyFieldsLabel.setVisible(true);
    }

    public void onInvalidInput() {
        emptyFieldsLabel.setVisible(false);
        duplicateUsernameLabel.setVisible(false);
        passwordTooShortLabel.setVisible(false);
        failedRegisterLabel.setVisible(false);
        invalidInputLabel.setVisible(true);
    }

    public void onRegisterSuccess() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }

    public void onDuplicateUsername() {
        emptyFieldsLabel.setVisible(false);
        passwordTooShortLabel.setVisible(false);
        failedRegisterLabel.setVisible(false);
        invalidInputLabel.setVisible(false);
        duplicateUsernameLabel.setVisible(true);
    }

    public void onPasswordTooShort() {
        emptyFieldsLabel.setVisible(false);
        failedRegisterLabel.setVisible(false);
        invalidInputLabel.setVisible(false);
        duplicateUsernameLabel.setVisible(false);
        passwordTooShortLabel.setVisible(true);
    }

    public void onRegisterFailure() {
        emptyFieldsLabel.setVisible(false);
        invalidInputLabel.setVisible(false);
        duplicateUsernameLabel.setVisible(false);
        passwordTooShortLabel.setVisible(false);
        failedRegisterLabel.setVisible(true);
    }

    @FXML
    public void switchToLogin() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }
}
