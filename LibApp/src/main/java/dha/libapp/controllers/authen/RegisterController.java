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
//        emptyFieldsLabel.setVisible(false);
//        invalidInputLabel.setVisible(false);
//
//        if (username.isEmpty() || password.isEmpty() ||
//                fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
//
//            invalidInputLabel.setVisible(false);
//            emptyFieldsLabel.setVisible(true);
//        } else if (!isValidEmail(email) || !isValidPhone(phone)) {
//            emptyFieldsLabel.setVisible(false);
//            invalidInputLabel.setVisible(true);
//        } else if (registerService.registerUser(username, password, fullName, phone, email)) {
//            MainAppController.changeScene("views/authen/Login.fxml");
//        } else {
//            System.out.println("Đăng kí thất bại");
//        }
    }

    @FXML
    public void onEmptyInput() {
        invalidInputLabel.setVisible(false);
        emptyFieldsLabel.setVisible(true);
    }

    @FXML
    public void onInvalidInput() {
        emptyFieldsLabel.setVisible(false);
        invalidInputLabel.setVisible(true);
    }

    @FXML
    public void onRegisterSuccess() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }

    @FXML
    public void onRegisterFailure() {
        System.out.println("Đăng kí thất bại");
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
