package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static User getUserFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.wasNull()) return null;
            int id = resultSet.getInt("user_id");
            String user_name = resultSet.getString("user_name");
            String password = resultSet.getString("password");
            String roleString = resultSet.getString("role");
            String fullName = resultSet.getString("full_name");
            String phoneNumber = resultSet.getString("phone_number");
            String email = resultSet.getString("email");

            UserRole role = UserRole.MEMBER;
            if (roleString.equals("ADMIN")) {
                role = UserRole.ADMIN;
            }
            return new User(id, user_name, password, role, fullName, phoneNumber, email);
        } catch (SQLException e) {
            System.out.println("Error when getUserFromResultSet...");
            throw new RuntimeException(e);
        }
    }

    public static List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preStm.executeQuery()) {
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
            return userList;
        } catch (Exception e) {
            System.out.println("Error when getAllUser...");
            throw new RuntimeException(e);
        }
    }

    public static User getUserById(int userId) {
        String sql = "SELECT * FROM User u WHERE u.user_id = ?";
        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userId);
            ResultSet resultSet = preStm.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            return getUserFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Error when getUserById...");
            throw new RuntimeException(e);
        }
    }

    public static User getUserByUsername(String userName) {
        String sql = "SELECT * FROM User u WHERE u.user_name = ?";
        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userName);
             ResultSet resultSet = preStm.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            return getUserFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Error when getUserById...");
            throw new RuntimeException(e);
        }
    }

    public static User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM User u WHERE u.user_name = ? AND u.password = ?";
        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, username, password);
             ResultSet resultSet = preStm.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            return getUserFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Error when getUserByUserNameAndPassword...");
            throw new RuntimeException(e);
        }
    }

    public static boolean addNewUser(String userName, String password, UserRole role, String fullName, String phoneNumber, String email) {
        String sql = "INSERT INTO User(user_name, password, role, full_name, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userName, password, role.toString(), fullName, phoneNumber, email)) {
            return preStm.execute();
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

        List<User> userList2 = getAllUser();
        System.out.println(userList2.size());
        System.out.println("User:");
        for (User user : userList2) {
            System.out.println(user.getUserId());
        }

        List<User> userList3 = getAllUser();
        System.out.println(userList3.size());
        System.out.println("User:");
        for (User user : userList3) {
            System.out.println(user.getUserId());
        }

        System.out.println(getUserById(1));
    }

}
