package dha.libapp.services.authen;

import dha.libapp.controllers.authen.RegisterController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.UserRole;

public class RegisterService {
    public static void register(String username, String password, String fullName, String phone, String email) {
        if (username.isEmpty() || password.isEmpty() ||
                fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {

            RegisterController.getInstance().onEmptyInput();
            return;
        }

        if (userExists(username)) {
            RegisterController.getInstance().onDuplicateUsername();
            return;
        }

        if (password.length() < 8) {
            RegisterController.getInstance().onPasswordTooShort();
            return;
        }

        if (!isValidEmail(email) || !isValidPhone(phone)
                || username.length() > 50 || password.length() > 100
                || fullName.length() > 100) {
            RegisterController.getInstance().onInvalidInput();
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            UserDAO.addNewUser(username, hashedPassword, UserRole.MEMBER, fullName, phone, email);
            RegisterController.getInstance().onRegisterSuccess();
        } catch (Exception e) {
            RegisterController.getInstance().onRegisterFailure();
        }
    }

    private static boolean userExists(String username) {
        return UserDAO.getUserByUsername(username) != null;
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@")
                && email.contains(".") && email.length() < 100;
    }

    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]+")
                && phone.length() >= 10 && phone.length() < 20;
    }
}
