package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.BorrowStatus;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle database operations for User.
 */
public class UserDAO {

    /**
     * Converts a ResultSet to a User object.
     *
     * @param resultSet The ResultSet containing the data from the database.
     * @return A User object corresponding to the user data in the ResultSet or null if the ResultSet is null.
     */
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

    /**
     * Retrieves all users from the database.
     *
     * @return A list of User objects representing all users in the database.
     */
    public static List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User "
                + "ORDER BY user_id";

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

    /**
     * Retrieves a User by their ID.
     *
     * @param userId The ID of the User.
     * @return A User object corresponding to the provided ID or null if not found.
     */
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

    /**
     * Retrieves a User by their username.
     *
     * @param userName The username of the User.
     * @return A User object corresponding to the provided username or null if not found.
     */
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

    /**
     * Retrieves a User by their username and password.
     *
     * @param username The username of the User.
     * @param password The password of the User.
     * @return A User object corresponding to the provided username and password or null if not found.
     */
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

    /**
     * Adds a new User to the database.
     *
     * @param userName    The username of the new User.
     * @param password    The password of the new User.
     * @param role        The role of the new User.
     * @param fullName    The full name of the new User.
     * @param phoneNumber The phone number of the new User.
     * @param email       The email of the new User.
     */
    public static void addNewUser(String userName, String password, UserRole role,
                                  String fullName, String phoneNumber, String email) {
        String sql = "INSERT INTO User(user_name, password, role, full_name, phone_number, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, userName, password, role.toString(), fullName, phoneNumber, email)) {

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when addNewUser...");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing User.
     *
     * @param userId      The ID of the User to update.
     * @param password    The new password of the User.
     * @param fullName    The new full name of the User.
     * @param phoneNumber The new phone number of the User.
     * @param email       The new email of the User.
     */
    public static void updateUser(int userId, String password, String fullName, String phoneNumber,
                                  String email) {
        String sql = "UPDATE User SET password = ?, "
                + "full_name = ?, phone_number = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, password, fullName, phoneNumber, email, userId)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when updateUser...");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing User.
     *
     * @param user The User object containing the updated information.
     */
    public static void updateUser(User user) {
        String sql = "UPDATE User SET password = ?, role = ?, "
                + "full_name = ?, phone_number = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, user.getPassword(), user.getRole().toString(), user.getFullName(),
                user.getPhoneNumber(), user.getEmail(), user.getUserId())) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when updateUser...");
            throw new RuntimeException(e);
        }
    }

    public static void updateUserName(int userId, String userName) {
        String sql = "UPDATE User SET user_name = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, userName, userId)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when updateUserName...");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a User from the database by their ID.
     * The User is marked as deleted, and associated borrow records are updated.
     *
     * @param userId The ID of the User to delete.
     */
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
            DeletedUserDAO.addNewDeletedUser(userId, userName);
            BorrowRecordDAO.updateBorrowRecordByUserIdAndStatus(
                    userId,
                    BorrowStatus.PENDING,
                    BorrowStatus.CANCELED
            );
        } catch (SQLException e) {
            System.out.println("Error when deleteUserById...");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a User from the database by their username.
     * The User is marked as deleted, and associated borrow records are updated.
     *
     * @param username The username of the User to delete.
     */
    public static void deleteUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            System.out.println("User with name " + username + " does not exist");
            return;
        }

        deleteUserById(user.getUserId());
    }

    /**
     * Searches for Users whose usernames match the given pattern.
     *
     * @param username The partial username to search for.
     * @return A list of User objects whose usernames match the pattern.
     */
    public static List<User> searchUserByUsername(String username) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User WHERE user_name LIKE ? "
                + "ORDER BY user_id";
        username = username + "%";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, username);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
            return userList;
        } catch (SQLException e) {
            System.out.println("Error when searchUserByUsername...");
            throw new RuntimeException(e);
        }
    }
}
