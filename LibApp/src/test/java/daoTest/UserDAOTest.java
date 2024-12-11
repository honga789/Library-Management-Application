package daoTest;

import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;

import dha.libapp.models.UserRole;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    static List<User> userList = new ArrayList<>();
    static int index = 0;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        userList= UserDAO.getAllUser();
        assertNotNull(userList);
        assertFalse(userList.isEmpty());

        System.out.println("UserList: " + userList.subList(0, Math.min(userList.size(), 5)));
    }

    @BeforeEach
    void setUp() throws Exception {
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }

    @Test
    @Order(1)
    void getUserById() {
        index = (int) (Math.random() * userList.size());

        User user1 = userList.get(index);
        User user2 = UserDAO.getUserById(user1.getUserId());

        assertEquals(user1, user2);
    }

    @Test
    @Order(2)
    void getUserByName() {
        index = (int) (Math.random() * userList.size());

        User user1 = userList.get(index);
        User user2 = UserDAO.getUserByUsername(user1.getUserName());

        assertEquals(user1, user2);
    }

    @Test
    @Order(3)
    void getUserByUsernameAndPassword() {
        index = (int) (Math.random() * userList.size());

        User user1 = userList.get(index);
        User user2 = UserDAO.getUserByUsernameAndPassword(user1.getUserName(), user1.getPassword());

        assertEquals(user1, user2);
    }

    @Test
    @Order(4)
    void addUser() {
        UserDAO.addNewUser("duy@", "123", UserRole.MEMBER, "Duy Trần",
                "12345", "duytran@gmail.com");

        User user = UserDAO.getUserByUsername("duy@");

        index = userList.size();
        userList.add(user);

        assertNotNull(user);
    }

    @Test
    @Order(5)
    void updateUser() {
        User user1 = userList.get(index);
        user1.setPassword("1234");

        UserDAO.updateUser(user1);

        User user2 = UserDAO.getUserByUsername(user1.getUserName());

        assertEquals(user1, user2);
    }

    @Test
    @Order(6)
    void deleteUser() {
        User user1 = UserDAO.getUserById(userList.get(index).getUserId());

        assert user1 != null;
        UserDAO.deleteUserById(user1.getUserId());

        User user2 = UserDAO.getUserByUsername(user1.getUserName());

        assertNull(user2);
    }

    @Test
    @Order(7)
    void searchUserByUsername() {
        userList.removeLast();
        index = (int) (Math.random() * userList.size());
        
        User user1 = userList.get(index);

        List<User> userSearchList = UserDAO.searchUserByUsername(user1.getUserName());

        assertNotNull(userSearchList);
        assertFalse(userSearchList.isEmpty());

        System.out.println("UserList: "
                + userSearchList.subList(0, Math.min(userSearchList.size(), 5)));
    }
}
