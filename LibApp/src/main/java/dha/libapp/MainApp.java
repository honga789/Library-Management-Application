package dha.libapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApp extends Application {

    private double offsetX;
    private double offsetY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StackPane root = new StackPane();
        root.setStyle(
                "-fx-background-color: #ffffff; -fx-background-radius: 30"
        );
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("views/MainAppView.fxml"));
        Parent parent = fxmlLoader.load();
        root.getChildren().addAll(parent);
        Scene scene = new Scene(root, 1275, 720);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        root.setOnMousePressed(e -> {
            offsetX = e.getSceneX();
            offsetY = e.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - offsetX);
            primaryStage.setY(e.getScreenY() - offsetY);
        });
    }
}
