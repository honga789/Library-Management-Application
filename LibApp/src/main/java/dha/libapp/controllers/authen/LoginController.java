package dha.libapp.controllers.authen;

import dha.libapp.MainAppController;
import javafx.fxml.FXML;

public class LoginController {
//    @FXML
//    public void handleLogin() {
//        dha.libapp.MainAppController.changeScene("views/Index.fxml");
//    }

    @FXML
    public void switchToRegister() {
        MainAppController.changeScene("views/authen/Register.fxml");
    }
}
