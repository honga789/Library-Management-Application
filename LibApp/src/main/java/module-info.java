module dha.libapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.desktop;

    opens dha.libapp.controllers.authen to javafx.fxml;
    opens dha.libapp.controllers.members to javafx.fxml;
    opens dha.libapp.controllers.members.tabs to javafx.fxml;
    opens dha.libapp.controllers.admin to javafx.fxml;
    opens dha.libapp.controllers.admin.tabs to javafx.fxml;

    opens dha.libapp.views to javafx.fxml;
    opens dha.libapp.views.authen to javafx.fxml;
    opens dha.libapp.views.members to javafx.fxml;
    opens dha.libapp.views.members.tabs to javafx.fxml;
    opens dha.libapp.views.admin to javafx.fxml;
    opens dha.libapp.views.admin.tabs to javafx.fxml;

    opens dha.libapp to javafx.fxml;
    exports dha.libapp;
}