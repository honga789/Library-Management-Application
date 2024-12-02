module dha.libapp {
    requires javafx.controls;
    requires javafx.fxml;

    exports dha.libapp.controllers;
    opens dha.libapp.controllers to javafx.fxml;

    opens dha.libapp.views to javafx.fxml;

    opens dha.libapp to javafx.fxml;
    exports dha.libapp;
}