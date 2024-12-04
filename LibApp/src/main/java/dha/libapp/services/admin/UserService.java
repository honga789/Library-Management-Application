package dha.libapp.services.admin;

import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import dha.libapp.services.authen.PasswordService;
import dha.libapp.syncdao.UserSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;

import java.util.List;

public class UserService {
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void addUser(String userName, String password, String fullName,
                        String phoneNumber, String email) throws Exception {
        if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty()
                || phoneNumber.isEmpty() || email.isEmpty() || userExists(userName)
                || password.length() < 8 || !isValidEmail(email) || !isValidPhone(phoneNumber)
                || userName.length() > 50 || password.length() > 100 || fullName.length() > 100) {

            // controller for invalid
            throw new Exception("Invalid input");
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            UserSyncDAO.addNewUserSync(userName, hashedPassword, UserRole.MEMBER,
                    fullName, phoneNumber, email, new DAOUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            System.out.println("User added successfully");
                            // controller;
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User added failed");
                            throw new RuntimeException(e);
                            // controller;
                        }
                    });
        } catch (Exception e) {
            System.out.println("User added failed");
            throw new Exception("User added failed");
            // controller for error.
        }
    }

    public void updateUser(int userId, String password, String fullName, String phoneNumber,
                           String email) throws Exception {
        if (password.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()
                || email.isEmpty() || password.length() < 8 || !isValidEmail(email)
                || !isValidPhone(phoneNumber) || password.length() > 50 || fullName.length() > 100) {

            // controller for invalid
            throw new RuntimeException("Invalid input");
        }

        try {
            String hashedPassword = PasswordService.hashPassword(password);

            UserSyncDAO.updateUserSync(userId, password, fullName, phoneNumber, email,
                    new DAOUpdateCallback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("User updated successfully");
                            // controller;
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User updated failed");
                            throw new RuntimeException(e);
                            // controller;
                        }
                    });
        } catch (Exception e) {
            System.out.println("User update failed");
            throw new Exception("User update failed");
            // controller for error
        }
    }

    public void updateUser(User user) throws Exception {
        if (user.getPassword().isEmpty() || user.getFullName().isEmpty() || user.getPhoneNumber().isEmpty()
                || user.getEmail().isEmpty() || user.getPassword().length() < 8 || !isValidEmail(user.getEmail())
                || !isValidPhone(user.getPhoneNumber()) || user.getPassword().length() > 50
                || user.getFullName().length() > 100) {

            // controller for invalid
            throw new RuntimeException("Invalid input");
        }

        try {
            String hashedPassword = PasswordService.hashPassword(user.getPassword());

            UserSyncDAO.updateUserSync(user.getUserId(), user.getPassword(), user.getFullName(),
                    user.getPhoneNumber(), user.getEmail(), new DAOUpdateCallback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("User updated successfully");
                            // controller;
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("User updated failed");
                            throw new RuntimeException(e);
                            // controller;
                        }
                    });
        } catch (Exception e) {
            System.out.println("User update failed");
            throw new Exception("User update failed");
            // controller for error
        }
    }

    public void deleteUser(int userId) throws Exception {
        try {
            UserSyncDAO.deleteUserByIdSync(userId, new DAOUpdateCallback() {

                @Override
                public void onSuccess() {
                    System.out.println("User deleted successfully");
                    //controller;
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("User deleted failed");
                    throw new RuntimeException(e);
                    //controller;
                }
            });
        } catch (Exception e) {
            System.out.println("User deleted failed");
            throw new Exception("User deleted failed");
            //controller for error;
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
