import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class UserTest {

    private Member member;

    @Before
    public void setUp() {
        member = new Member("Duytran", "password123");
        member.setFullName("Duy Tran")
                .setEmail("duytran@example.com")
                .setPhoneNumber("123456789")
                .setBirthDate(new Date());
    }

    @Test
    public void testConstructor() {
        assertEquals("Duytran", member.getUserName());
        assertEquals("password123", member.getPassword());
        assertTrue(member.getUserId() != 0);
        assertEquals(UserRole.MEMBER, member.getUserRole());
    }

    @Test
    public void testCopyConstructor() {
        Member copyMember = new Member(member);
        assertEquals(member.getUserName(), copyMember.getUserName());
        assertEquals(member.getPassword(), copyMember.getPassword());
        assertEquals(member.getUserId(), copyMember.getUserId());
        assertEquals(member.getUserRole(), copyMember.getUserRole());
    }

    @Test
    public void testSettersAndGetters() {
        member.setUserName("newUsername")
                .setPassword("newPassword")
                .setEmail("new.email@example.com")
                .setPhoneNumber("012345678");

        assertEquals("newUsername", member.getUserName());
        assertEquals("newPassword", member.getPassword());
        assertEquals("new.email@example.com", member.getEmail());
        assertEquals("012345678", member.getPhoneNumber());
    }

    @Test
    public void testToString() {
        String expectedString = "Role: MEMBER"
                + "\n\tId: " + member.getUserId()
                + "\n\tUsername: Duytran"
                + "\n\tPassword: password123"
                + "\n\tFullName: Duy Tran"
                + "\n\tEmail: duytran@example.com"
                + "\n\tPhoneNumber: 123456789"
                + "\n\tBirthDate: " + member.getBirthDate()
                + "\n\tProfileImagePath: null";

        assertEquals(expectedString, member.toString());
    }

    @Test
    public void testEquals() {
        Member anotherMember = new Member("Duytran", "password123");

        assertTrue(member.equals(anotherMember));
    }

    @Test
    public void testCheckValidInit_Valid() {
        member.checkValidInit();
    }

    @Test
    public void testCheckValidInit_Invalid() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Member("", "");
        });
        assertEquals("Username or password is invalid.", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Member("Duy", "");
        });
        assertEquals("Username or password is invalid.", exception2.getMessage());

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Member("", "Tran");
        });
        assertEquals("Username or password is invalid.", exception3.getMessage());
    }
}
