package daoTest;

import dha.libapp.dao.DeletedUserDAO;
import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeletedUserDAOTest {
    @Test
    @Order(1)
    void getDeletedUserById() {
        UserDAO.addNewUser("duy@", "123", UserRole.MEMBER, "Duy Trần",
                "12345", "duytran@gmail.com");

        User user = UserDAO.getUserByUsername("duy@");

        assertNotNull(user);
        UserDAO.deleteUserById(user.getUserId());

        User deletedUser = DeletedUserDAO.getDeletedUserById(user.getUserId());
        assertNotNull(deletedUser);

        System.out.println(deletedUser);
    }
}
