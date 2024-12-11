package daoTest;

import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.models.GenreType;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenreTypeDAOTest {
    static List<GenreType> genreTypeList = new ArrayList<>();
    static int index = 0;

    @BeforeAll
    static void setupBeforeClass() throws Exception {
        System.out.println("GenreTypeDAOTest setupBeforeClass - start");

        genreTypeList = GenreTypeDAO.getAllGenreType();
        assertNotNull(genreTypeList);
        assertFalse(genreTypeList.isEmpty());
        System.out.println("genreTypeList: \n" + genreTypeList.subList(0, Math.min(genreTypeList.size(), 5)));

        System.out.println("GenreTypeDAOTest setupAfterClass - end");
    }

    @BeforeEach
    void setup() throws Exception {
        assertNotNull(genreTypeList);
        assertFalse(genreTypeList.isEmpty());
    }

    @Test
    @Order(1)
    void getGenreTypeById() {
        System.out.println("\nTest getGenreTypeById - start");

        index = (int) (Math.random() * genreTypeList.size());
        GenreType genreType1 = genreTypeList.get(index);
        GenreType genreType2 = GenreTypeDAO.getGenreTypeById(genreType1.getGenreId());
        assertEquals(genreType1, genreType2);

        System.out.println("Test getGenreTypeById - end");
    }

    @Test
    @Order(2)
    void getGenreTypeByName() {
        System.out.println("\nTest getGenreTypeByName - start");

        index = (int) (Math.random() * genreTypeList.size());
        GenreType genreType1 = genreTypeList.get(index);
        GenreType genreType2 = GenreTypeDAO.getGenreTypeByName(genreType1.getGenreName());
        assertEquals(genreType1, genreType2);

        System.out.println("Test getGenreTypeByName - end");
    }
}
