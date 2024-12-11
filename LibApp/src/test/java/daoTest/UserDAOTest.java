package daoTest;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    @Test
    public void testSearchUserByUsername() {
        List<User> users = UserDAO.searchUserByUsername("ad");
        assertNotNull(users);
        assertFalse(users.isEmpty());
        System.out.println(users.size());
        System.out.println(users);
    }
}
