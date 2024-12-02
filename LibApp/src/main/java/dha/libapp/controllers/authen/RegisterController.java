package dha.libapp.controllers.authen;

import dha.libapp.MainAppController;
import javafx.fxml.FXML;

public class RegisterController {
    @FXML
    public void switchToLogin() {
        MainAppController.changeScene("views/authen/Login.fxml");
    }
}
