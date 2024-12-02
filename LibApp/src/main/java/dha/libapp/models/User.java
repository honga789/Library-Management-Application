package dha.libapp.models;

public class User {

    private int userId;
    private String userName;
    private String password;
    private UserRole role;
    private String fullName;
    private String phoneNumber;
    private String email;

    public User() {
    }

    public User(int userId, String userName, String password, UserRole role, String fullName, String phoneNumber, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
