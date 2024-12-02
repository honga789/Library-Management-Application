module dha.libapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens dha.libapp to javafx.fxml;
    exports dha.libapp;
}