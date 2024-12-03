package dha.libapp.controllers.members.tabs;

import dha.libapp.dao.BookDAO;
import dha.libapp.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label userFullName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFullName.setText(UserDAO.getUserByUsername("duyacquy").getFullName());
    }
}
