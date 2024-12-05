package dha.libapp.controllers.admin;

import dha.libapp.MainApp;
import dha.libapp.MainAppController;
import dha.libapp.cache.members.BorrowedTabCache;
import dha.libapp.cache.members.HomeTabCache;
import dha.libapp.cache.members.PendingTabCache;
import dha.libapp.cache.members.ReturnedTabCache;
import dha.libapp.controllers.members.MemberViewController;
import dha.libapp.services.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    private static AdminViewController instance;
    public static AdminViewController getInstance() { return instance; }

    @FXML
    private AnchorPane tabContent;

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
        instance = this;
        switchTab("views/admin/tabs/AdminManageUserTab.fxml");
    }

    private void clearAllCache() {
        HomeTabCache.getInstance().getRecommendationBookList().clear();
        HomeTabCache.getInstance().getTopTrendingBookList().clear();
        PendingTabCache.getInstance().getPendingBookList().clear();
        BorrowedTabCache.getInstance().getBorrowedBookList().clear();
        ReturnedTabCache.getInstance().getReturnedBookList().clear();
    }

    @FXML
    public void handleLogout() {
        clearAllCache();
        SessionService.getInstance().setUser(null);
        MainAppController.changeScene("views/authen/Login.fxml");
    }
}