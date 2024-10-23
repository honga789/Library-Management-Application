import java.util.Objects;

/**
 * The Member class is a subclass of the abstract User class.
 * It represents a user with specific details such as username and password,
 * and automatically generates a user ID based on the username.
 * This class overrides the equals method to compare members based on their user ID.
 * It also provides a method to display the member's information.
 */
public class Member extends User {

    /**
     * Constructs a Member object with the given username and password.
     * The user ID is generated automatically based on the username.
     *
     * @param userName The username for the member.
     * @param password The password for the member.
     */
    public Member(String userName, String password) {
        super(userName, password);
        this.setUserId(); // Generates a user ID based on the username.
        this.setUserRole();
    }

    /**
     * Constructor for copy from another Member.
     *
     * @param member another Member.
     */
    public Member(Member member) {
        super(member);
    }

    /**
     * Displays the member's information by printing the toString result.
     */
    @Override
    public void displayInfo() {
        System.out.println(super.toString() + "\n");
    }

    /**
     * Checks if this member is equal to another object by comparing user IDs.
     *
     * @param obj The object to compare with.
     * @return true if the objects are the same or have the same userId, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Member)) {
            return false;
        }

        Member other = (Member) obj;

        return this.getUserId() == other.getUserId();
    }
}
