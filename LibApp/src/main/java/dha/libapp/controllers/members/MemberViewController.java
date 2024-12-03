package dha.libapp.controllers.members;

import dha.libapp.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberViewController implements Initializable {

    @FXML
    private AnchorPane tabContent;

    private void switchTab(String fxmlTabFile) {
        Parent fxmlTabContent = MainApp.getContentFromFxml(fxmlTabFile);
        tabContent.getChildren().clear();
        tabContent.getChildren().addAll(fxmlTabContent);
    }

    @FXML
    public void switchToHomeTab() {
        switchTab("views/members/tabs/MemberHomeTab.fxml");
    }

    @FXML
    public void switchToBorrowPendingTab() {
        switchTab("views/members/tabs/MemberBorrowPendingTab.fxml");
    }

    @FXML
    public void switchToBorrowedTab() {
        switchTab("views/members/tabs/MemberBorrowedTab.fxml");
    }

    @FXML
    public void switchToReturnedTab() {
        switchTab("views/members/tabs/MemberReturnedTab.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchTab("views/members/tabs/MemberHomeTab.fxml");
    }
}
