package dha.libapp.services;

import dha.libapp.controllers.authen.LoginController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;

public class LoginService {
    public static void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            LoginController.getInstance().onInvalidInput();
            return;
        }

        User user = UserDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            LoginController.getInstance().onLoginSuccess();
            // Check role
            System.out.println(user.getRole().toString());
        } else {
            LoginController.getInstance().onLoginFailure();
        }
    }
}
