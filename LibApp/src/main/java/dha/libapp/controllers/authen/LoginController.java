package dha.libapp.controllers.authen;

import dha.libapp.MainAppController;
import dha.libapp.controllers.members.tabs.MemberHomeTabController;
import dha.libapp.models.User;
import dha.libapp.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static LoginController instance;

    public static LoginController getInstance() {
        return instance;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label emptyFieldsLabel;

    @FXML
    private Label failedLoginLabel;

    @FXML
    private Label incorrectLabel;

    @FXML
    public void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        LoginService.login(username, password);
    }

    public void onLoginToMemberScene(User user) {
        MainAppController.changeScene("views/members/MemberView.fxml");
    }

    public void onLoginToAdminScene(User user) {
        MainAppController.changeScene("views/admin/MemberView.fxml");
    }

    public void onInvalidInput() {
        incorrectLabel.setVisible(false);
        failedLoginLabel.setVisible(false);
        emptyFieldsLabel.setVisible(true);
    }

    public void onIncorrectInput() {
        emptyFieldsLabel.setVisible(false);
        failedLoginLabel.setVisible(false);
        incorrectLabel.setVisible(true);
    }

    public void onLoginFailure() {
        incorrectLabel.setVisible(false);
        emptyFieldsLabel.setVisible(false);
        failedLoginLabel.setVisible(true);
    }

    @FXML
    public void switchToRegister() {
        MainAppController.changeScene("views/authen/Register.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }
}
