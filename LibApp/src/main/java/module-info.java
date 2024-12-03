module dha.libapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;

    opens dha.libapp.controllers.authen to javafx.fxml;
    opens dha.libapp.controllers.members to javafx.fxml;
    opens dha.libapp.controllers.members.tabs to javafx.fxml;

    opens dha.libapp.views to javafx.fxml;
    opens dha.libapp.views.authen to javafx.fxml;
    opens dha.libapp.views.members to javafx.fxml;
    opens dha.libapp.views.members.tabs to javafx.fxml;

    opens dha.libapp to javafx.fxml;
    exports dha.libapp;
}