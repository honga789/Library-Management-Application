package dha.libapp.services;

import dha.libapp.controllers.authen.LoginController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;

import java.security.NoSuchAlgorithmException;

public class LoginService {
    public static void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            LoginController.getInstance().onInvalidInput();
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            User user = UserDAO.getUserByUsernameAndPassword(username, hashedPassword);

            if (user != null) {
                if (user.getRole().equals(UserRole.MEMBER)) {
                    LoginController.getInstance().onLoginToMemberScene(user);
                } else if (user.getRole().equals(UserRole.ADMIN)) {
                    LoginController.getInstance().onLoginToAdminScene(user);
                }
            } else {
                LoginController.getInstance().onIncorrectInput();
            }

        } catch (Exception e) {
            LoginController.getInstance().onLoginFailure();
        }
    }
}
