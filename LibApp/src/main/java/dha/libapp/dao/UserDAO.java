package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.User;
import dha.libapp.utils.Database.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        // hehe

        ResultSet resultSet = DBUtil.executeQuery(MainApp.getDbConnection(), "SELECT * FROM user");

        try {
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static void main(String[] args) {
        System.out.println("UserDAO:");
        System.out.println(MainApp.getDbConnection());
    }

}
