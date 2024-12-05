package dha.libapp.utils.ListView;

import dha.libapp.models.BorrowRecord;
import javafx.scene.control.ListView;

import java.util.List;

public class BorrowListView {
    public static void renderToListView(ListView<BorrowRecord> listView, List<BorrowRecord> borrowList) {
        listView.getItems().clear();
        listView.getItems().addAll(borrowList);
    }
}
