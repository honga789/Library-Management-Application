package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private static HomeController instance;

    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    private Label userFullName;

    public void onLoad(User user) {
        userFullName.setText(user.getFullName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }
}
