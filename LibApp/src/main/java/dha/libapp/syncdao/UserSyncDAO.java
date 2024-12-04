package dha.libapp.syncdao;

import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import javafx.concurrent.Task;

import java.util.List;

public class UserSyncDAO {

    // Fetch all users
    public static void getAllUserSync(DAOExecuteCallback<List<User>> callback) {
        Task<List<User>> task = new Task<List<User>>() {
            @Override
            protected List<User> call() throws Exception {
                return UserDAO.getAllUser();
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    // Fetch user by ID
    public static void getUserByIdSync(int userId, DAOExecuteCallback<User> callback) {
        Task<User> task = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return UserDAO.getUserById(userId);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    // Fetch user by username
    public static void getUserByUsernameSync(String userName, DAOExecuteCallback<User> callback) {
        Task<User> task = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return UserDAO.getUserByUsername(userName);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    // Fetch user by username and password
    public static void getUserByUsernameAndPasswordSync(String username, String password, DAOExecuteCallback<User> callback) {
        Task<User> task = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return UserDAO.getUserByUsernameAndPassword(username, password);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    // Add new user
    public static void addNewUserSync(String userName, String password, UserRole role,
                                      String fullName, String phoneNumber, String email, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserDAO.addNewUser(userName, password, role, fullName, phoneNumber, email);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    // Update user by ID
    public static void updateUserSync(int userId, String password,
                                      String fullName, String phoneNumber, String email, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserDAO.updateUser(userId, password, fullName, phoneNumber, email);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    // Update user by User object
    public static void updateUserSync(User user, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserDAO.updateUser(user);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    // Delete user by ID
    public static void deleteUserByIdSync(int userId, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserDAO.deleteUserById(userId);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }

    // Delete user by username
    public static void deleteUserByUsernameSync(String username, DAOUpdateCallback callback) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                UserDAO.deleteUserByUsername(username);
                return null;
            }
        };
        DAOTaskRunner.updateTask(task, callback);
    }
}
