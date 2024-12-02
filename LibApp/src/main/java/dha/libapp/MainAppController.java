package dha.libapp;

import dha.libapp.effects.HoverEffect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView closeIcon;

    @FXML
    private ImageView minimizeIcon;

    @FXML
    public void handleClose() {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimize() {
        Stage stage = (Stage) minimizeIcon.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HoverEffect.applyFadeEffect(closeIcon, 1, 0.7, 100);
        HoverEffect.applyFadeEffect(minimizeIcon, 1, 0.7, 100);
    }
}
