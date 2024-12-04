package dha.libapp.controllers.admin.tabs;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;

public class AdminManageUserController {

    private TextField createStyledTextField(String promptText) {
        javafx.scene.control.TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return textField;
    }

    private Text createStyledLabel(String labelText) {
        Text text = new Text(labelText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        text.setStyle("-fx-fill: #333;");
        return text;
    }

    //text field
}
