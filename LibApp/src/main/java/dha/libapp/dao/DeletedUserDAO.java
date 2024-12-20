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

/**
 * DAO class to handle database operations for Deleted User.
 */
public class DeletedUserDAO {

    /**
     * Converts a ResultSet to a User object for a deleted user.
     *
     * @param resultSet The ResultSet containing the data from the database.
     * @return A User object corresponding to the deleted user or null if the ResultSet is null.
     */
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

    /**
     * Retrieves a deleted user by their ID.
     *
     * @param userId The ID of the deleted user.
     * @return A User object corresponding to the deleted user with the provided ID or null if not found.
     */
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

    /**
     * Adds a new deleted user to the database.
     *
     * @param deleted_user_id   The ID of the deleted user.
     * @param deleted_user_name The name of the deleted user.
     */
    public static void addNewDeletedUser(int deleted_user_id, String deleted_user_name) {
        String sql = "INSERT INTO Deleted_user(deleted_user_id, deleted_user_name) "
                + "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, deleted_user_id, deleted_user_name)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when addNewDeletedUser...");
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for deleted users based on their username.
     *
     * @param username The username to search for.
     * @return A list of User objects that match the given username pattern.
     */
    public static List<User> searchDeletedUserByUsername(String username) {
        List<User> deletedUsers = new ArrayList<>();
        String sql = "SELECT * FROM User u JOIN Deleted_user du ON u.user_id = du.deleted_user_id "
                + "WHERE du.deleted_user_name LIKE ?";
        username = username + "%";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, username);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User deletedUser = getDeletedUserFromResultSet(resultSet);
                if (deletedUser != null) {
                    deletedUsers.add(deletedUser);
                }
            }
            return deletedUsers;
        } catch (SQLException e) {
            System.out.println("Error when searchDeletedUserByUsername...");
            throw new RuntimeException(e);
        }
    }
}
