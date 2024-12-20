package dha.libapp.controllers.admin.tabs;

import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.services.admin.BookService;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import dha.libapp.utils.API.ExecutorHandle;
import dha.libapp.utils.API.GoogleBooks.BookFetchCallback;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksAPI;
import dha.libapp.utils.API.GoogleBooks.GoogleBooksTask;
import dha.libapp.utils.API.Image.ImageAPI;
import dha.libapp.utils.API.Image.ImageFetchCallback;
import dha.libapp.utils.API.Image.ImageTask;
import dha.libapp.utils.ListView.BookListView;
import javafx.fxml.FXML;

import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.ArrayList;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class AdminManageDocumentController {
    @FXML
    private Button newBook;

    @FXML
    private Button editBook;

    @FXML
    private Button deleteBook;

    @FXML
    private Label editStatus = new Label();

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private Pane loadingPane;

    @FXML
    private Label bookDetailName;

    @FXML
    private Label bookDetailAuthor;

    @FXML
    private Label bookDetailDescription;

    @FXML
    private ImageView bookDetailImage;

    @FXML
    private Label ISBN;

    @FXML
    private Label quantityInStock;

    @FXML
    private Book selectedBook;

    @FXML
    private Pane searchLoadingPane;

    @FXML
    private ListView<Book> searchBookListView;

    @FXML
    private AnchorPane searchBox;

    @FXML
    private ImageView closeSearchBox;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView searchBtn;

    private List<GenreType> genreTypeList = new ArrayList<GenreType>();
    private ArrayList<GenreType> selectedGenreTypeList = new ArrayList<GenreType>();

    public void setSearchLoadingPaneVisible(boolean visible) {
        searchLoadingPane.setVisible(visible);
    }

    public void setSearchBoxVisible(boolean visible) {
        searchBox.setVisible(visible);
    }

    public void initialize() {
        genreTypeList.clear();
        initializeButton();
        BookService.GenreCallback callback = new BookService.GenreCallback() {

            @Override
            public void onSuccess(List<GenreType> genreTypesCallback) {
                genreTypeList.addAll(genreTypesCallback);
            }
        };
        BookService.getInstance().getGenres(callback);

        bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                System.out.println("Selected Book: " + selected.getClass().toString() + ": " + selected);
                this.setBookDetailView(selected);
                this.selectedBook = selected;
            }
        });

        loadingPane.setVisible(true);
        BookService.getInstance().getAllBooks(new DAOExecuteCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                loadingPane.setVisible(false);
                BookListView.renderToListView(bookListView, result);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException();
            }
        });

        // Search bar
        setSearchLoadingPaneVisible(false);
        setSearchBoxVisible(false);

        closeSearchBox.setOnMouseClicked(e -> {
            setSearchBoxVisible(false);
        });

        searchBtn.setOnMouseClicked(e -> {
            setSearchLoadingPaneVisible(true);
            setSearchBoxVisible(true);
            String search = searchInput.getText();

            BookService.getInstance().getSearchBooks(search, new DAOExecuteCallback<List<Book>>() {
                @Override
                public void onSuccess(List<Book> result) {
                    setSearchLoadingPaneVisible(false);
                    BookListView.renderToListView(searchBookListView, result);
                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }
            });
        });

        searchBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selected = (Book) newValue;
                this.setBookDetailView(selected);
                this.selectedBook = selected;
            }
        });
    }

    public void setBookDetailView(Book book) {
        bookDetailName.setText(book.getTitle());
        bookDetailAuthor.setText(book.getAuthor());
        bookDetailDescription.setText(book.getDescription());
        ISBN.setText("ISBN: " + book.getISBN());
        quantityInStock.setText("Quantity: " + book.getQuantity());

        ImageTask imageTask = ImageAPI.getImageWithUrl(book.getCoverImagePath(), new ImageFetchCallback() {
            @Override
            public void onSuccess(Image image) {
                bookDetailImage.setImage(image);
            }

            @Override
            public void onFailure(Exception ex) {
                URL resourceUrl = getClass().getResource("/dha/libapp/images/book_loader.jpg");

                if (resourceUrl != null) {
                    Image defaultImage = new Image(resourceUrl.toExternalForm());
                    bookDetailImage.setImage(defaultImage);
                } else {
                    System.out.println("Dont't find default resources.");
                }
            }
        });

        ExecutorHandle.getInstance().addTask(imageTask);
    }

    private void initializeButton() {
        newBook.setOnAction(event -> {
            openFieldBox();
        });
        editBook.setOnAction(event -> {
            if (selectedBook != null) {
                openFieldBoxEdit(selectedBook);
            } else {
                showErrorPopup("Error Select Book", "Please Select a Book");
            }
        });
        deleteBook.setOnAction(event -> {
            if (selectedBook != null) {
                deleteBook(selectedBook);
            } else {
                showErrorPopup("Error Select Book", "Please Select a Book");
            }
        });
    }

    private void deleteBook(Book book) {
        showConfirmDeletePopup("Confirm Book Deletion","Please Confirm Book Delete", book);
    }

    private void openFieldBox() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Add new book");
        alert.setHeaderText("Enter new book information:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField isbnField = createStyledTextField("ISBN");

        TextField titleField = createStyledTextField("Title");

        TextField descriptionField = createStyledTextField("Description");

        TextField stockField = createStyledTextField("Quantity");

        TextField coverField = createStyledTextField("Cover");

        TextField authorField = createStyledTextField("Author");

        TextField publishedDateField = createStyledTextField("Published Date");

        TextField publisherField = createStyledTextField("Publisher");


        //genre checkbox

        // Create a Popup for the dropdown
        Popup genrePopup = new Popup();

        // VBox to hold checkboxes
        VBox genreBox = new VBox(10);
        genreBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        genrePopup.getContent().add(genreBox);
        // List of genres
        if (!genreTypeList.isEmpty()) {
            for (GenreType genre : genreTypeList) {
                CheckBox genreCheckBox = new CheckBox(genre.getGenreName());
                genreBox.getChildren().add(genreCheckBox);
            }
        } else {
            CheckBox nullGenreCheckBox = new CheckBox("Null genre");
            genreBox.getChildren().add(nullGenreCheckBox);
        }

        // Add the Confirm button inside the popup
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        confirmButton.setOnAction(event -> {
            StringBuilder selectedGenres = new StringBuilder("Selected Genres: ");
            selectedGenreTypeList.clear();
            BookService.GenreCallback callback = new BookService.GenreCallback() {

                @Override
                public void onSuccess(List<GenreType> genreTypesCallback) {
                    selectedGenreTypeList.addAll(genreTypesCallback);
                }
            };
            for (Node node : genreBox.getChildren()) {
                if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
                    selectedGenres.append(checkBox.getText()).append(", ");
                    BookService.getInstance().getGenreByName(checkBox.getText(), callback);
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
        Button genreCheckButton = new Button("Choose genres");
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
                publishDate = formatter.parse(publishDateString);
                System.out.println("Converted Date: " + publishDate);
            } catch (ParseException e) {
                showErrorPopup("Invalid data input", "Please enter a valid date with format dd-mm-yyyy");
                return;
            }
            BookService.getInstance().addBook(isbn, title, author, publisher,
                    publishDate, quantity, description, imgUrl, selectedGenreTypeList,
                    new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            showSuccessPopup("Book Added", "Book Added Successfully");
                            // controller
                        }

                        @Override
                        public void onError(Throwable e) {
                            showErrorPopup("Add Book Error", "Got: " + e.getMessage() + "\nPlease try again");
                            // controller
                        }
                    });
        });

        //call api autofill
        Button autofillButton = new Button("Auto fill");
        autofillButton.setStyle("-fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        autofillButton.setOnMouseClicked(event -> {
            List<Book> dataHolder = new ArrayList<>();
            String isbn = isbnField.getText();
            CountDownLatch latch = new CountDownLatch(1);
            BookFetchCallback callback = new BookFetchCallback() {
                @Override
                public void onSuccess(List<Book> booksData) {
                    dataHolder.clear();
                    dataHolder.addAll(booksData);
                    System.out.println("book data added");
                    latch.countDown();
                }

                @Override
                public void onFailure(Exception ex) {
                    System.out.println(ex.getMessage());
                    latch.countDown();
                }
            };

            GoogleBooksTask googleBooksTask = GoogleBooksAPI.getBookDataByISBN(isbn, callback);
            ExecutorHandle.getInstance().addTask(googleBooksTask);

            try {
                // Wait for the task to finish
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task interrupted: " + e.getMessage());
            }

            if (!dataHolder.isEmpty()) {
                titleField.setText(dataHolder.getFirst().getTitle());
                isbnField.setText(dataHolder.getFirst().getISBN());
                if (dataHolder.getFirst().getDescription() != null) {
                    descriptionField.setText(dataHolder.getFirst().getDescription());
                }
                if (dataHolder.getFirst().getAuthor() != null) {
                    authorField.setText(dataHolder.getFirst().getAuthor());
                }
                if (dataHolder.getFirst().getCoverImagePath() != null) {
                    coverField.setText(dataHolder.getFirst().getCoverImagePath());
                }
                if (dataHolder.getFirst().getPublisher() != null) {
                    publisherField.setText(dataHolder.getFirst().getPublisher());
                }
                Date dateFromAPI = dataHolder.getFirst().getPublicationDate();
                if (dateFromAPI != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");;
                    publishedDateField.setText(formatter.format(dateFromAPI));
                }
                authorField.setText(dataHolder.getFirst().getAuthor());
            } else {
                showErrorPopup("Error finding book", "Book not found or don't have info");
            }
            
        });

        gridPane.add(createStyledLabel("ISBN:"), 0, 0);
        gridPane.add(isbnField, 1, 0);
        gridPane.add(autofillButton, 2, 0);

        gridPane.add(createStyledLabel("Title:"), 0, 1);
        gridPane.add(titleField, 1, 1);

        gridPane.add(createStyledLabel("Description:"), 0, 2);
        gridPane.add(descriptionField, 1, 2);

        gridPane.add(createStyledLabel("Quantity:"), 0, 3);
        gridPane.add(stockField, 1, 3);

        gridPane.add(createStyledLabel("Cover:"), 0, 4);
        gridPane.add(coverField, 1, 4);

        gridPane.add(createStyledLabel("Author:"), 0, 5);
        gridPane.add(authorField, 1, 5);

        gridPane.add(createStyledLabel("Genres:"), 0, 6);
        gridPane.add(genreCheckButton, 1, 6);

        gridPane.add(createStyledLabel("Published Date:"), 0, 7);
        gridPane.add(publishedDateField, 1, 7);

        gridPane.add(createStyledLabel("Publisher:"), 0, 8);
        gridPane.add(publisherField, 1, 8);

        gridPane.add(addButton, 0, 10);

        gridPane.add(editStatus, 0, 11);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);

        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");

        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();


    }

    private void openFieldBoxEdit(Book book) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Edit book");
        alert.setHeaderText("Enter book information:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField isbnField = createStyledTextField("ISBN");
        isbnField.setText(book.getISBN());

        TextField titleField = createStyledTextField("Title");
        titleField.setText(book.getTitle());

        TextField descriptionField = createStyledTextField("Description");
        descriptionField.setText(book.getDescription());

        TextField stockField = createStyledTextField("Quantity");
        stockField.setText(String.valueOf(book.getQuantity()));

        TextField coverField = createStyledTextField("Cover");
        coverField.setText(book.getCoverImagePath());

        TextField authorField = createStyledTextField("Author");
        authorField.setText(book.getAuthor());

        TextField publishedDateField = createStyledTextField("Published Date");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date publishDate = book.getPublicationDate();
        publishedDateField.setText(formatter.format(publishDate));

        TextField publisherField = createStyledTextField("Publisher");
        publisherField.setText(book.getPublisher());

        //genre checkbox

        // Create a Popup for the dropdown
        Popup genrePopup = new Popup();

        // VBox to hold checkboxes
        VBox genreBox = new VBox(10);
        genreBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        genrePopup.getContent().add(genreBox);
        // List of genres
        //clone selected from book
        selectedGenreTypeList.clear();
        selectedGenreTypeList.addAll(book.getGenreList());
        System.out.println(selectedGenreTypeList);
        if (!genreTypeList.isEmpty()) {
            for (GenreType genre : genreTypeList) {
                CheckBox genreCheckBox = new CheckBox(genre.getGenreName());
                //pre check
                for (GenreType genreType : selectedGenreTypeList) {
                    if (genre.getGenreId()==(genreType.getGenreId())) {
                        genreCheckBox.setSelected(true);
                        System.out.println(genre.getGenreName());
                    }
                }
                genreBox.getChildren().add(genreCheckBox);
            }
        } else {
            CheckBox nullGenreCheckBox = new CheckBox("Null genre");
            genreBox.getChildren().add(nullGenreCheckBox);
        }
        // Add the Confirm button inside the popup
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        confirmButton.setOnAction(event -> {
            StringBuilder selectedGenres = new StringBuilder("Selected Genres: ");
            selectedGenreTypeList.clear();
            BookService.GenreCallback callback = new BookService.GenreCallback() {

                @Override
                public void onSuccess(List<GenreType> genreTypesCallback) {
                    selectedGenreTypeList.addAll(genreTypesCallback);
                }
            };

            for (Node node : genreBox.getChildren()) {
                if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
                    selectedGenres.append(checkBox.getText()).append(", ");
                    BookService.getInstance().getGenreByName(checkBox.getText(), callback);
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
        Button genreCheckButton = new Button("Choose genres");
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
        Button addButton = new Button("Confirm Edit");
        addButton.setStyle("-fx-font-size: 15px; -fx-background-color: #d46dd2; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            Book newBook = new Book(book);
            newBook.setISBN(isbnField.getText());
            newBook.setAuthor(authorField.getText());
            newBook.setDescription(descriptionField.getText());
            newBook.setCoverImagePath(coverField.getText());
            newBook.setTitle(titleField.getText());
            try {
                newBook.setQuantity(Integer.parseInt(stockField.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Error formatting quantity");
            }
            newBook.setPublisher(publisherField.getText());
            newBook.setGenreList(selectedGenreTypeList);
            String publishDateString = publishedDateField.getText();
            try {
                DAOUpdateCallback callback = new DAOUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        showSuccessPopup("Book Edited", "Book Edited Successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorPopup("Book Edited", "Got: " + e.getMessage() + "\nPlease try again");
                    }
                };
                Date editedDate = null;
                editedDate = formatter.parse(publishDateString);
                newBook.setPublicationDate(editedDate);
                BookService.getInstance().updateBook(newBook, callback);
                System.out.println("Updated Book: " + newBook.getISBN());
            } catch (Exception e) {
                showErrorPopup("Error Update Book", "Got:" + e.getMessage() + "\nPlease enter valid book data");
            }

        });

        gridPane.add(createStyledLabel("ISBN:"), 0, 0);
        gridPane.add(isbnField, 1, 0);

        gridPane.add(createStyledLabel("Title:"), 0, 1);
        gridPane.add(titleField, 1, 1);

        gridPane.add(createStyledLabel("Description:"), 0, 2);
        gridPane.add(descriptionField, 1, 2);

        gridPane.add(createStyledLabel("Quantity:"), 0, 3);
        gridPane.add(stockField, 1, 3);

        gridPane.add(createStyledLabel("Cover:"), 0, 4);
        gridPane.add(coverField, 1, 4);

        gridPane.add(createStyledLabel("Author:"), 0, 5);
        gridPane.add(authorField, 1, 5);

        gridPane.add(createStyledLabel("Genres:"), 0, 6);
        gridPane.add(genreCheckButton, 1, 6);

        gridPane.add(createStyledLabel("Published Date:"), 0, 7);
        gridPane.add(publishedDateField, 1, 7);

        gridPane.add(createStyledLabel("Publisher:"), 0, 8);
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

    private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom styling if needed
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-background-color: #fff; -fx-border-radius: 10; -fx-background-radius: 10;");

        alert.showAndWait();
    }
    private void showConfirmPopup(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose OK");
        } else {
            System.out.println("User chose Cancel or closed the dialog");
        }
    }
    private void showConfirmDeletePopup(String header, String message, Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        DAOUpdateCallback callback = new DAOUpdateCallback() {
            @Override
            public void onSuccess() {
                showSuccessPopup("Book Deleted", "Book deleted successfully");
            }

            @Override
            public void onError(Throwable e) {
                showErrorPopup("Book Delete Error", "Got: " + e.getMessage() + "\nPlease try again");
            }
        };
        // Handle the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose OK");
            BookService.getInstance().deleteBook(book, callback);
        } else {
            System.out.println("User chose Cancel or closed the dialog");
        }
    }
    private void showSuccessPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom styling if needed
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-background-color: #fff; -fx-border-radius: 10; -fx-background-radius: 10;");

        alert.showAndWait();
    }


}
