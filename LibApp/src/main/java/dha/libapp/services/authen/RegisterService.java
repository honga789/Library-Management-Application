package dha.libapp.services.authen;

import dha.libapp.controllers.authen.RegisterController;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.UserRole;
import dha.libapp.syncdao.UserSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;

/**
 * Service class responsible for handling user registration logic.
 * Validates the registration input, hashes the password, and stores the user data in the database.
 */
public class RegisterService {

    /**
     * Registers a new user by validating the input and storing the user information in the database.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param fullName The full name of the user.
     * @param phone    The phone number of the user.
     * @param email    The email address of the user.
     */
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

            UserSyncDAO.addNewUserSync(username, hashedPassword, UserRole.MEMBER,
                    fullName, phone, email, new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            RegisterController.getInstance().onRegisterSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            RegisterController.getInstance().onRegisterFailure();
                        }
                    });
        } catch (Exception e) {
            RegisterController.getInstance().onRegisterFailure();
        }
    }

    /**
     * Checks whether a user with the given username already exists in the database.
     *
     * @param username The username to check.
     * @return true if the user already exists, false otherwise.
     */
    private static boolean userExists(String username) {
        return UserDAO.getUserByUsername(username) != null;
    }

    /**
     * Validates the format of the given email address.
     *
     * @param email The email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@")
                && email.contains(".") && email.length() < 100;
    }

    /**
     * Validates the format of the given phone number.
     *
     * @param phone The phone number to validate.
     * @return true if the phone number is valid, false otherwise.
     */
    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]+")
                && phone.length() >= 10 && phone.length() < 20;
    }
}
