package dha.libapp.utils.ListView;

import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class BookListView {
    public static void renderToListView(ListView<Book> listView, List<Book> bookList) {
        System.out.println("Books:");
        for (Book book : bookList) {
            System.out.println(book);
        }

        listView.getItems().clear();
        listView.getItems().addAll(bookList);
    }
}
