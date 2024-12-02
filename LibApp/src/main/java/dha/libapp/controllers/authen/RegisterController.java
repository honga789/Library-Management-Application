package dha.libapp.controllers.authen;

import com.sun.tools.javac.Main;
import dha.libapp.MainAppController;
import dha.libapp.services.RegisterService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {
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

    private final RegisterService registerService;

    public RegisterController() {
        registerService = new RegisterService();
    }

    @FXML
    public void handleRegisterAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        emptyFieldsLabel.setVisible(false);
        invalidInputLabel.setVisible(false);

        if (username.isEmpty() || password.isEmpty() ||
                fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {

            invalidInputLabel.setVisible(false);
            emptyFieldsLabel.setVisible(true);
        } else if (!isValidEmail(email) || !isValidPhone(phone)) {
            emptyFieldsLabel.setVisible(false);
            invalidInputLabel.setVisible(true);
        } else if (registerService.registerUser(username, password, fullName, phone, email)) {
            MainAppController.changeScene("views/authen/Login.fxml");
        } else {
            System.out.println("Đăng kí thất bại");
        }
    }

    @FXML
    public void switchToLogin() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]+") && phone.length() == 10;
    }
}
