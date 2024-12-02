module dha.libapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports dha.libapp.controllers;
    opens dha.libapp.controllers to javafx.fxml;
    opens dha.libapp.controllers.authen to javafx.fxml;

    opens dha.libapp.views to javafx.fxml;
    opens dha.libapp.views.authen to javafx.fxml;
    opens dha.libapp.views.mainPage to javafx.fxml;

    opens dha.libapp to javafx.fxml;
    exports dha.libapp;
}