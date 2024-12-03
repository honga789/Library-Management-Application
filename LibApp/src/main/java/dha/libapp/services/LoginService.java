package dha.libapp.services;

import dha.libapp.controllers.authen.LoginController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;

public class LoginService {
    public static void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            LoginController.getInstance().onInvalidInput();
            return;
        }

        User user = UserDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            if (user.getRole().equals(UserRole.MEMBER)) {
                LoginController.getInstance().onLoginSuccess();
            } else if (user.getRole().equals(UserRole.ADMIN)) {
                LoginController.getInstance().onLoginSuccess();
            }
        } else {
            LoginController.getInstance().onLoginFailure();
        }
    }
}
