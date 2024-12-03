package dha.libapp.controllers.admin;

import dha.libapp.MainApp;
import dha.libapp.effects.HoverEffect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private AnchorPane tabContent;

    @FXML
    private Button btnHomeTab;
    @FXML
    private Button btnManageUserTab;
    @FXML
    private Button btnManageDocumentTab;
    @FXML
    private Button btnReturnRequestTab;
    @FXML
    private Button btnBorrowRequestTab;
    @FXML
    private Button btnApproveRequestTab;

    private void switchTab(String fxmlTabFile) {
        Parent fxmlTabContent = MainApp.getContentFromFxml(fxmlTabFile);
        tabContent.getChildren().clear();
        tabContent.getChildren().addAll(fxmlTabContent);
    }

    @FXML
    public void switchToHomeTab() {
        switchTab("views/admin/tabs/AdminHomeTab.fxml");
    }

    @FXML
    public void switchToManageUserTab() {
        switchTab("views/admin/tabs/AdminManageUserTab.fxml");
    }

    @FXML
    public void switchToManageDocumentTab() {
        switchTab("views/admin/tabs/AdminManageDocumentTab.fxml");
    }

    @FXML
    public void switchToReturnRequestTab() {
        switchTab("views/admin/tabs/AdminReturnRequestTab.fxml");
    }

    @FXML
    public void switchToBorrowRequestTab() {
        switchTab("views/admin/tabs/AdminBorrowRequestTab.fxml");
    }

    @FXML
    public void switchToApproveRequestTab() {
        switchTab("views/admin/tabs/AdminApproveRequestTab.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchTab("views/admin/tabs/AdminHomeTab.fxml");

        HoverEffect.applyBackgroundColorEffect(btnHomeTab, "ffffff00", "ffffff55", 100);
        HoverEffect.applyBackgroundColorEffect(btnManageUserTab, "ffffff00", "ffffff55", 100);
        HoverEffect.applyBackgroundColorEffect(btnManageDocumentTab, "ffffff00", "ffffff55", 100);
        HoverEffect.applyBackgroundColorEffect(btnReturnRequestTab, "ffffff00", "ffffff55", 100);
        HoverEffect.applyBackgroundColorEffect(btnBorrowRequestTab, "ffffff00", "ffffff55", 100);
        HoverEffect.applyBackgroundColorEffect(btnApproveRequestTab, "ffffff00", "ffffff55", 100);
    }
}