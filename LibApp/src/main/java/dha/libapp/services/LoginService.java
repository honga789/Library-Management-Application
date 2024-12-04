package dha.libapp.services;

import dha.libapp.controllers.authen.LoginController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.syncdao.UserSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.security.NoSuchAlgorithmException;

public class LoginService {
    public static void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            LoginController.getInstance().onInvalidInput();
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            LoginController.getInstance().setloginLoadingPaneVisible(true);

            UserSyncDAO.getUserByUsernameAndPasswordSync(username, hashedPassword, new DAOExecuteCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    if (user != null) {

                        SessionService.getInstance().setUser(user);

                        if (user.getRole().equals(UserRole.MEMBER)) {
                            LoginController.getInstance().onLoginToMemberScene(user);
                        } else if (user.getRole().equals(UserRole.ADMIN)) {
                            LoginController.getInstance().onLoginToAdminScene(user);
                        }
                    } else {
                        LoginController.getInstance().onIncorrectInput();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    LoginController.getInstance().onLoginFailure();
                }
            });
        } catch (Exception e) {
            LoginController.getInstance().onLoginFailure();
        }
    }
}
