package dha.libapp.services;

public class LoginService {
    public boolean authenticate(String username, String password) {
        String validUsername = "admin";
        String validPassword = "password123";

        return validUsername.equals(username) && validPassword.equals(password);
    }
}
