package dha.libapp.services.admin;

import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.services.authen.PasswordService;
import dha.libapp.syncdao.UserSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOUpdateCallback;

import java.util.List;

/**
 * Service class responsible for managing user-related operations such as adding, updating, deleting, and searching users.
 */
public class UserService {
    private static UserService instance;

    private UserService() {
    }

    /**
     * Returns the singleton instance of the UserService.
     *
     * @return The singleton instance of UserService.
     */
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /**
     * Adds a new user with the specified details.
     *
     * @param userName    The username for the new user.
     * @param password    The password for the new user.
     * @param fullName    The full name of the new user.
     * @param phoneNumber The phone number of the new user.
     * @param email       The email address of the new user.
     * @param callback    The callback to be invoked upon completion of the operation.
     */
    public void addUser(String userName, String password, String fullName,
                        String phoneNumber, String email, DAOUpdateCallback callback) {

        if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty()
                || phoneNumber.isEmpty() || email.isEmpty() || userExists(userName)
                || password.length() < 8 || !isValidEmail(email) || !isValidPhone(phoneNumber)
                || userName.length() > 50 || password.length() > 100 || fullName.length() > 100) {

            callback.onError(new RuntimeException("Invalid input"));
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            UserSyncDAO.addNewUserSync(userName, hashedPassword, UserRole.MEMBER,
                    fullName, phoneNumber, email, new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            System.out.println("User added successfully");
                            callback.onSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User added failed");
                            callback.onError(new RuntimeException("User added failed"));
                        }
                    });
        } catch (Exception e) {
            System.out.println("User added failed");
            callback.onError(new RuntimeException("User added failed"));
        }
    }

    /**
     * Updates the details of an existing user.
     *
     * @param userId      The ID of the user to update.
     * @param password    The new password for the user.
     * @param fullName    The new full name of the user.
     * @param phoneNumber The new phone number of the user.
     * @param email       The new email address of the user.
     * @param callback    The callback to be invoked upon completion of the operation.
     */
    public void updateUser(int userId, String password, String fullName, String phoneNumber,
                           String email, DAOUpdateCallback callback) {

        if (password.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()
                || email.isEmpty() || password.length() < 8 || !isValidEmail(email)
                || !isValidPhone(phoneNumber) || password.length() > 50 || fullName.length() > 100) {

            callback.onError(new RuntimeException("Invalid input"));
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            UserSyncDAO.updateUserSync(userId, hashedPassword, fullName, phoneNumber, email,
                    new DAOUpdateCallback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("User updated successfully");
                            callback.onSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User updated failed");
                            callback.onError(new RuntimeException("User updated failed"));
                        }
                    });
        } catch (Exception e) {
            System.out.println("User update failed");
            callback.onError(new RuntimeException("User update failed"));
        }
    }

    /**
     * Searches for users based on the username.
     *
     * @param username The username of the user to search for.
     * @param callback The callback to be invoked upon completion of the search.
     */
    public void getSearchUser(String username, DAOExecuteCallback<List<User>> callback) {
        UserSyncDAO.searchUserByUsernameSync(username, new DAOExecuteCallback<>() {
            @Override
            public void onSuccess(List<User> result) {
                System.out.println("User search successfully");
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("User search failed");
                callback.onError(e);
            }
        });
    }

    /**
     * Updates the details of a given user object.
     *
     * @param user     The user object to update.
     * @param callback The callback to be invoked upon completion of the operation.
     */
    public void updateUser(User user, DAOUpdateCallback callback) {
        if (user.getPassword().isEmpty() || user.getFullName().isEmpty() || user.getPhoneNumber().isEmpty()
                || user.getEmail().isEmpty() || user.getPassword().length() < 8 || !isValidEmail(user.getEmail())
                || !isValidPhone(user.getPhoneNumber()) || user.getPassword().length() > 50
                || user.getFullName().length() > 100) {

            callback.onError(new RuntimeException("Invalid input"));
            return;
        }

        try {
            String hashedPassword = PasswordService.hashPassword(user.getPassword());

            UserSyncDAO.updateUserSync(user.getUserId(), user.getPassword(), user.getFullName(),
                    user.getPhoneNumber(), user.getEmail(), new DAOUpdateCallback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("User updated successfully");
                            callback.onSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User updated failed");
                            callback.onError(new RuntimeException("User updated failed"));
                        }
                    });
        } catch (Exception e) {
            System.out.println("User update failed");
            callback.onError(new RuntimeException("User update failed"));
        }
    }

    /**
     * Deletes a user based on their user ID.
     *
     * @param userId   The ID of the user to delete.
     * @param callback The callback to be invoked upon completion of the deletion.
     */
    public void deleteUser(int userId, DAOUpdateCallback callback) {
        UserSyncDAO.deleteUserByIdSync(userId, new DAOUpdateCallback() {

            @Override
            public void onSuccess() {
                System.out.println("User deleted successfully");
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("User deleted failed");
                callback.onError(new RuntimeException("User deleted failed"));
            }
        });
    }

    /**
     * Checks if a user with the given username already exists.
     *
     * @param username The username to check.
     * @return True if the user exists, otherwise false.
     */
    private static boolean userExists(String username) {
        return UserDAO.getUserByUsername(username) != null;
    }

    /**
     * Validates the email format.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, otherwise false.
     */
    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@")
                && email.contains(".") && email.length() < 100;
    }

    /**
     * Validates the phone number format.
     *
     * @param phone The phone number to validate.
     * @return True if the phone number is valid, otherwise false.
     */
    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]+")
                && phone.length() >= 10 && phone.length() < 20;
    }
}
