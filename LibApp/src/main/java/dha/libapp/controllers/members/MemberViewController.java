package dha.libapp.controllers.members;

import dha.libapp.MainApp;
import dha.libapp.MainAppController;
import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.members.BorrowedTabCache;
import dha.libapp.cache.members.HomeTabCache;
import dha.libapp.cache.members.PendingTabCache;
import dha.libapp.cache.members.ReturnedTabCache;
import dha.libapp.services.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberViewController implements Initializable {
    private static MemberViewController instance;
    public static MemberViewController getInstance() { return instance; }

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
        instance = this;
        switchTab("views/members/tabs/MemberHomeTab.fxml");
    }

    private void clearAllCache() {
//        HomeTabCache.getInstance().getRecommendationBookList().clear();
//        HomeTabCache.getInstance().getTopTrendingBookList().clear();
//        PendingTabCache.getInstance().getPendingBookList().clear();
//        BorrowedTabCache.getInstance().getBorrowedBookList().clear();
//        ReturnedTabCache.getInstance().getReturnedBookList().clear();
//        CacheFactory.getCache(HomeTabCache.class).clearAll();
//        CacheFactory.getCache(BorrowedTabCache.class).clearAll();
//        CacheFactory.getCache(PendingTabCache.class).clearAll();
//        CacheFactory.getCache(ReturnedTabCache.class).clearAll();

        CacheFactory.clearAllCache();
    }

    @FXML
    public void handleRefresh() {
        MemberViewController.getInstance().switchToHomeTab();
        clearAllCache();
    }

    @FXML
    public void handleLogout() {
        clearAllCache();
        SessionService.getInstance().setUser(null);
        MainAppController.changeScene("views/authen/Login.fxml");
    }
}
