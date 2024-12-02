package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.User;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        // hehe

        ResultSet resultSet = DBUtil.executeQuery(MainApp.getDbConnection(), "SELECT * FROM User");

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                User user = new User();
                user.setUserId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static List<User> getAllUser2() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), "SELECT * FROM User");
            ResultSet resultSet = preStm.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                User user = new User();
                user.setUserId(id);
                userList.add(user);
            }
            return userList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("UserDAO:");
        System.out.println(MainApp.getDbConnection());

        List<User> userList = getAllUser();
        System.out.println(userList.size());
        System.out.println("User:");
        for (User user : userList) {
            System.out.println(user.getUserId());
        }

        List<User> userList2 = getAllUser2();
        System.out.println(userList2.size());
        System.out.println("User:");
        for (User user : userList2) {
            System.out.println(user.getUserId());
        }

        List<User> userList3 = getAllUser2();
        System.out.println(userList3.size());
        System.out.println("User:");
        for (User user : userList3) {
            System.out.println(user.getUserId());
        }
    }

}
