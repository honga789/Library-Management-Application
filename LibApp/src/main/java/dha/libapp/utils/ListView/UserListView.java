package dha.libapp.utils.ListView;

import dha.libapp.models.User;
import javafx.scene.control.ListView;

import java.util.List;

public class UserListView {
    public static void renderToListView(ListView<User> listView, List<User> userList) {
        listView.getItems().clear();
        listView.getItems().addAll(userList);
    }
}
