package dha.libapp.models;

public class User {

    private int userId;
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private UserRole role;
    private String email;

    public User() {
    }

    public User(int userId, String userName, String password, String fullName, String phoneNumber, UserRole role, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
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

    public String toString() {
        return String.format(
                "\nID: %s\nUser Name: %s\nFull Name: %s\n ",
                userId, userName, fullName
        );

//        return "User: [\n"
//                + "userId = " + userId + ",\n"
//                + "userName = " + userName + ",\n"
//                + "password = " + password + ",\n"
//                + "fullName = " + fullName + ",\n"
//                + "phoneNumber = " + phoneNumber + ",\n"
//                + "role = " + role + ",\n"
//                + "email = " + email + "\n]";
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return this.userId == user.getUserId()
                    && this.userName.equals(user.getUserName());
        }
        return false;
    }
}
