package daoTest;
import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.models.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeletedUserDAOTest {

    @Test
    public void testGetDeletedUserById() {
        int x = 5;
        User user = DeletedUserDAO.getDeletedUserById(x);
        assertNotNull(user);
        System.out.println(user);
        assertEquals(x, user.getUserId());
    }
}
