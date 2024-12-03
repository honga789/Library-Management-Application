package dha.libapp.controllers.members.tabs;

import dha.libapp.models.User;
import dha.libapp.services.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberHomeTabController implements Initializable {

    @FXML
    private Label userFullName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFullName.setText(SessionService.getInstance().getUser().getFullName());
    }
}
