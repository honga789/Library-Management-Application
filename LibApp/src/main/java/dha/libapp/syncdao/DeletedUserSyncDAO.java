package dha.libapp.syncdao;

import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.List;

public class DeletedUserSyncDAO {
    public static void getDeletedUserByIdSync(int userId, DAOExecuteCallback<User> callback) {
        Task<User> task = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return DeletedUserDAO.getDeletedUserById(userId);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void searchDeletedUserByUsernameSync(String username, DAOExecuteCallback<List<User>> callback) {
        Task<List<User>> task = new Task<List<User>>() {
            @Override
            protected List<User> call() throws Exception {
                return DeletedUserDAO.searchDeletedUserByUsername(username);
            }
        };
        DAOTaskRunner.executeTask(task, callback);

    }
}
