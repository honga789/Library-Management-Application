package dha.libapp.syncdao;

import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.models.User;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

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
}
