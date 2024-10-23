import java.util.Date;
import java.util.Objects;

/**
 * The User class is an abstract class representing the basic attributes
 * and operations of a user in the system, including roles, personal information,
 * and account credentials.
 */
public abstract class User {
    private UserRole userRole;        // The role of the user (member, librarian).
    private int userId;               // Unique identifier for the user.
    private String userName;          // Username for user account.
    private String password;          // Password for user account.
    private String fullName;          // Full name of user.
    private String email;             // The email of user.
    private String phoneNumber;       // The phone number of user.
    private Date birthDate;           // The date of birth of user.
    private String profileImagePath;  // Path of profile's image.

    /**
     * Constructs a new User object with the given username and password.
     *
     * @param userName The username for the account.
     * @param password The password for the account.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    protected User setUserRole() {
        if (this instanceof Member) {
            this.userRole = UserRole.MEMBER;
        } else if (this instanceof Librarian) {
            this.userRole = UserRole.LIBRARIAN;
        }
        return this;
    }

    public int getUserId() {
        return userId;
    }

    protected User setUserId() {
        checkValidInit();
        this.userId = genId();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        setUserId();
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public User setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public User setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
        return this;
    }

    /**
     * Abstract method that must be implemented by subclasses
     * to display user information.
     */
    public abstract void displayInfo();

    /**
     * Returns a string representation of the User object,
     * including all user details.
     *
     * @return A string representing the user details.
     */
    public String toString() {
        return  "User Role: " + userRole
                + "\nUser Id: " + userId
                + "\nUser Name: " + userName
                + "\nUser Password: " + password
                + "\nUser FullName: " + fullName
                + "\nUser Email: " + email
                + "\nUser PhoneNumber: " + phoneNumber
                + "\nUser BirthDate: " + birthDate
                + "\nUser ProfileImagePath = " + profileImagePath;
    }

    /**
     * Checks if the username and password are valid (not null or empty).
     * Throws an IllegalArgumentException if either is invalid.
     */
    public void checkValidInit() {
        if (userName == null || userName.isEmpty()
            || password == null || password.isEmpty()) {

            throw new IllegalArgumentException("Username or password is invalid.");
        }
    }

    /**
     * Generates a unique user ID based on the username.
     * The ID is generated using a hash of the username.
     *
     * @return The generated user ID.
     */
    private int genId() {
        System.out.println(this.userName);
        return Objects.hash(this.userName);
    }

//    public boolean matches(Object template) {
//
//    }
}
