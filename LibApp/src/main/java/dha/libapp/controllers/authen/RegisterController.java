package dha.libapp.controllers.authen;

import com.sun.tools.javac.Main;
import dha.libapp.MainAppController;
import dha.libapp.services.RegisterService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private TextField passwordField;

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
        emptyFieldsLabel.setVisible(true);
    }

    public void onInvalidInput() {
        emptyFieldsLabel.setVisible(false);
        invalidInputLabel.setVisible(true);
    }

    public void onRegisterSuccess() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }

    public void onRegisterFailure() {
        System.out.println("Đăng kí thất bại");
    }

    public void onDuplicateUsername() {
        System.out.println("Trùng tên đăng kí");
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
