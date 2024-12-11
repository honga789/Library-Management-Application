package daoTest;

import dha.libapp.dao.UserDAO;
import dha.libapp.models.User;
import dha.libapp.models.UserRole;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeletedUserDAOTest {
    static List<User> userList = new ArrayList<>();
    static int index = 0;

    @Test
    @Order(1)
    void getDeletedUserById() {
        UserDAO.addNewUser("duy@", "123", UserRole.MEMBER, "Duy Trần",
                "12345", "duytran@gmail.com");

        User user = UserDAO.getUserByUsername("duy@");

        userList.add(user);
    }
}
