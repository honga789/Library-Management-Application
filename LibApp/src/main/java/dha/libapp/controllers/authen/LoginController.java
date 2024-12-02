package dha.libapp.controllers.authen;

import dha.libapp.MainAppController;
import dha.libapp.services.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label emptyFieldsLabel;

    @FXML
    private Label invalidCredentialsLabel;

    private final LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    @FXML
    public void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            invalidCredentialsLabel.setVisible(false);
            emptyFieldsLabel.setVisible(true);
        } else if (loginService.authenticate(username, password)) {
            MainAppController.changeScene("views/mainPage/Home.fxml");
        } else {
            emptyFieldsLabel.setVisible(false);
            invalidCredentialsLabel.setVisible(true);
        }
    }

    @FXML
    public void switchToRegister() {
        MainAppController.changeScene("views/authen/Register.fxml");
    }
}
