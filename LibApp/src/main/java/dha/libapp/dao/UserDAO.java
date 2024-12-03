package dha.libapp.dao;

import com.sun.tools.javac.Main;
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
            if (resultSet.wasNull()) {
                return null;
            }

            int user_id = resultSet.getInt("user_id");
            String user_name = resultSet.getString("user_name");
            String password = resultSet.getString("password");
            String fullName = resultSet.getString("full_name");
            String phoneNumber = resultSet.getString("phone_number");
            UserRole role = UserRole.valueOf(resultSet.getString("role"));
            String email = resultSet.getString("email");

            if (user_name == null) {
                return null;
            }
            return new User(user_id, user_name, password, fullName, phoneNumber, role, email);
        } catch (SQLException e) {
            System.out.println("Error when getUserFromResultSet...");
            throw new RuntimeException(e);
        }
    }

    private static User getDeletedUserFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }

            int user_id = resultSet.getInt("user_id");
            String user_name = resultSet.getString("deleted_user_name");
            String password = resultSet.getString("password");
            String fullName = resultSet.getString("full_name");
            String phoneNumber = resultSet.getString("phone_number");
            UserRole role = UserRole.valueOf(resultSet.getString("role"));
            String email = resultSet.getString("email");

            return new User(user_id, user_name, password, fullName, phoneNumber, role, email);
        } catch (SQLException e) {
            System.out.println("Error when getDeletedUserFromResultSet...");
            throw new RuntimeException(e);
        }
    }

    private static void addNewDeletedUser(int deleted_user_id, String deleted_user_name) {
        String sql = "INSERT INTO Deleted_user(deleted_user_id, deleted_user_name) "
                + "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, deleted_user_id, deleted_user_name)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error when addNewDeletedUser...");
            throw new RuntimeException(e);
        }
    }

    public static List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
            return userList;
        } catch (Exception e) {
            System.out.println("Error when getAllUser...");
            throw new RuntimeException(e);
        }
    }

    public static User getUserById(int userId) {
        String sql = "SELECT * FROM User WHERE user_id = ?";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userId);
            ResultSet resultSet = prepareStatement.executeQuery()) {

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
        String sql = "SELECT * FROM User WHERE user_name = ?";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userName);
             ResultSet resultSet = prepareStatement.executeQuery()) {

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
        String sql = "SELECT * FROM User WHERE user_name = ? AND password = ?";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, username, password);
             ResultSet resultSet = prepareStatement.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }
            return getUserFromResultSet(resultSet);
        } catch (Exception e) {
            System.out.println("Error when getUserByUserNameAndPassword...");
            throw new RuntimeException(e);
        }
    }

    public static void addNewUser(String userName, String password, UserRole role,
                                  String fullName, String phoneNumber, String email) {
        String sql = "INSERT INTO User(user_name, password, role, full_name, phone_number, email) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, userName, password, role.toString(), fullName, phoneNumber, email)) {

            prepareStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error when addNewUser...");
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(int userId, String password, UserRole role,
                                  String fullName, String phoneNumber, String email) {
        String sql = "UPDATE User SET password = ?, role = ?, "
                    + "full_name = ?, phone_number = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, password, role.toString(), fullName, phoneNumber, email, userId)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when updateUser...");
            throw new RuntimeException(e);
        }
    }

    public static void deleteUserById(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return;
        }

        String userName = user.getUserName();
        String sql = "UPDATE User SET user_name = null WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, userId)) {

            preparedStatement.executeUpdate();
            addNewDeletedUser(userId, userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return;
        }

        int userId = user.getUserId();
        deleteUserById(userId);
    }

    public static User getDeletedUserById(int userId) {
        String sql = "SELECT * FROM User u JOIN Deleted_user du ON u.user_id = du.deleted_user_id "
                + "WHERE u.user_id = ?";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, userId);
             ResultSet resultSet = prepareStatement.executeQuery()) {

            if (resultSet.next()) {
                return getDeletedUserFromResultSet(resultSet);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error when getDeletedUserById...");
            throw new RuntimeException(e);
        }
    }
}
