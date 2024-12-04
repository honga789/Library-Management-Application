package dha.libapp.utils.ListView;

import dha.libapp.models.Book;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class BookListView {
    public static void renderToListView(ListView<String> listView, List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("book list is empty");
            return;
        }

        System.out.println("Books:");
        for (Book book : bookList) {
            System.out.println(book);
        }

        List<String> allStringBook = new ArrayList<>();
         for (Book b : bookList) { if (b == null) continue; allStringBook.add(b.toString()); }

        listView.getItems().addAll(allStringBook);

        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    }
}
