package dha.libapp.controllers.admin.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.services.BookService;
import javafx.fxml.FXML;
import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.dao.BookDAO;
import java.util.ArrayList;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.scene.control.CheckBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.List;

public class AdminManageDocumentController {

    @FXML
    private Button newBook;
    private List<GenreType> genreTypeList = new ArrayList<GenreType>();
    private ArrayList<GenreType> selectedGenreTypeList = new ArrayList<GenreType>();
    private Label editStatus = new Label();

    @FXML
    public void initialize() {
        initializeButton();
        genreTypeList = GenreTypeDAO.getAllGenreType();
    }

    private void initializeButton() {
        newBook.setOnAction(event -> {
            openFieldBox();
        });
    }

    private void openFieldBox() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Nhập thông tin");
        alert.setHeaderText("Vui lòng nhập thông tin sách:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField isbnField = createStyledTextField("ISBN");

        TextField titleField = createStyledTextField("Tiêu đề");

        TextField descriptionField = createStyledTextField("Mô tả");

        TextField stockField = createStyledTextField("Số lượng");

        TextField coverField = createStyledTextField("Đường dẫn ảnh bìa");

        TextField authorField = createStyledTextField("Tác giả");

        TextField publishedDateField = createStyledTextField("Năm xuất bản");

        TextField publisherField = createStyledTextField("Nhà xuất bản");


        //genre checkbox

        // Create a Popup for the dropdown
        Popup genrePopup = new Popup();

        // VBox to hold checkboxes
        VBox genreBox = new VBox(10);
        genreBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        genrePopup.getContent().add(genreBox);
        // List of genres

        for (GenreType genre : genreTypeList) {
            CheckBox genreCheckBox = new CheckBox(genre.getGenreName());
            genreBox.getChildren().add(genreCheckBox);
        }
        // Add the Confirm button inside the popup
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        confirmButton.setOnAction(event -> {
            StringBuilder selectedGenres = new StringBuilder("Selected Genres: ");
            for (Node node : genreBox.getChildren()) {
                if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
                    selectedGenres.append(checkBox.getText()).append(", ");

                }
            }
            if (selectedGenres.length() > 17) {
                selectedGenres.setLength(selectedGenres.length() - 2); // Trim trailing ", "
            } else {
                selectedGenres.append("None");
            }
            System.out.println(selectedGenres);
            genrePopup.hide();
        });
        genreBox.getChildren().add(confirmButton);

        //choose genre
        Button genreCheckButton = new Button("Chọn thể loại");
        genreCheckButton.setStyle("-fx-font-size: 12px; -fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        genreCheckButton.setOnMouseClicked(event -> {
            if (!genrePopup.isShowing()) {
                // Position the popup near the genreCheckButton
                genrePopup.show(
                        genreCheckButton,
                        genreCheckButton.localToScene(0, 0).getX() + genreCheckButton.getScene().getWindow().getX(),
                        genreCheckButton.localToScene(0, 0).getY() + genreCheckButton.getScene().getWindow().getY() + genreCheckButton.getHeight()
                );
            } else {
                genrePopup.hide();
            }
        });
        //add book
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-font-size: 15px; -fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String description = descriptionField.getText();
            int quantity = 1;
            try {
                quantity = Integer.parseInt(stockField.getText());
            } catch (NumberFormatException e) {

            }
            String author = authorField.getText();
            String publisher = publisherField.getText();
            String publishDateString = publishedDateField.getText();
            String imgUrl = coverField.getText();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date publishDate = null;
            try {
                Date date = formatter.parse(publishDateString);
                System.out.println("Converted Date: " + date);
            } catch (ParseException e) {
                System.out.println("Invalid date format!");
                e.printStackTrace();
            }
            //BookService.addBook();
        });

        //call api autofill
        Button autofillButton = new Button("Tự điền");
        autofillButton.setStyle("-fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        autofillButton.setOnMouseClicked(event -> {

        });

        gridPane.add(createStyledLabel("ISBN:"), 0, 0);
        gridPane.add(isbnField, 1, 0);
        gridPane.add(autofillButton, 2, 0);

        gridPane.add(createStyledLabel("Tiêu đề:"), 0, 1);
        gridPane.add(titleField, 1, 1);

        gridPane.add(createStyledLabel("Mô tả:"), 0, 2);
        gridPane.add(descriptionField, 1, 2);

        gridPane.add(createStyledLabel("Số lượng:"), 0, 3);
        gridPane.add(stockField, 1, 3);

        gridPane.add(createStyledLabel("Ảnh bìa:"), 0, 4);
        gridPane.add(coverField, 1, 4);

        gridPane.add(createStyledLabel("Tác giả:"), 0, 5);
        gridPane.add(authorField, 1, 5);

        gridPane.add(createStyledLabel("Thể loại:"), 0, 6);
        gridPane.add(genreCheckButton, 1, 6);

        gridPane.add(createStyledLabel("Ngày xuất bản:"), 0, 7);
        gridPane.add(publishedDateField, 1, 7);

        gridPane.add(createStyledLabel("Nhà xuất bản:"), 0, 8);
        gridPane.add(publisherField, 1, 8);

        gridPane.add(addButton, 0, 10);

        gridPane.add(editStatus, 0, 11);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);

        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");

        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();


    }
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
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


}
