package dha.libapp;

import dha.libapp.utils.Database.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;

public class MainApp extends Application {

    private double offsetX;
    private double offsetY;

    private static Connection dbConnection;
    public static Connection getDbConnection() {
        if (dbConnection == null) {
            dbConnection = DBUtil.connect("jdbc:mysql://b0dhldnmrpv8rotqmh6y-mysql.services.clever-cloud.com/b0dhldnmrpv8rotqmh6y", "uoxesvpdndreask6", "LTpg5gRkVYgDyuiSKjt3");
        }
        return dbConnection;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Connection connection = DBUtil.connect("jdbc:mysql://b0dhldnmrpv8rotqmh6y-mysql.services.clever-cloud.com/b0dhldnmrpv8rotqmh6y", "uoxesvpdndreask6", "LTpg5gRkVYgDyuiSKjt3");
        System.out.println(connection);
        dbConnection = connection;

        StackPane root = new StackPane();
        root.setStyle(
                "-fx-background-color: #ffffff; -fx-background-radius: 30"
        );

        Parent fxmlContent = MainApp.getContentFromFxml("views/MainAppView.fxml");

        root.getChildren().addAll(fxmlContent);
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

    public static Parent getContentFromFxml(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml));
        try {
            return fxmlLoader.load();
        } catch (Exception e) {
            System.out.println("Error when load file: " + fxml);
            throw new RuntimeException(e);
        }
    }
}
