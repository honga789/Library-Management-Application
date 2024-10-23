import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDatabaseUtil {
    @Test
    public void connectWithoutDriverSetupTest() {
        Connection connection = DatabaseUtil.connect("jdbc:mysql://localhost:3306/library_database", "root", "");
        System.out.println(connection);
        // If Driver has not setup, connection will be null
        assertNull(connection);
    }

    @Test
    public void setupDriverAndConnectTest() {
        DatabaseUtil.setupDriver();
        Connection connection = DatabaseUtil.connect("jdbc:mysql://localhost:3306/library_database", "root", "");
        System.out.println(connection);
        assertNotNull(connection);
    }

    @Test
    public void executeTest() {
        DatabaseUtil.setupDriver();
        Connection connection = DatabaseUtil.connect("jdbc:mysql://localhost:3306/lib_test", "root", "");
        String sql = "INSERT INTO test_execute(test) values (2005)";
        DatabaseUtil.execute(connection, sql);
    }

    @Test
    public void executeQueryTest() {
        DatabaseUtil.setupDriver();
        Connection connection = DatabaseUtil.connect("jdbc:mysql://localhost:3306/lib_test", "root", "");
        String query = "SELECT te.test FROM test_execute te";
        ResultSet resultSet = DatabaseUtil.executeQuery(connection, query);
        if (resultSet != null) {
            try {
                resultSet.next();
                int result = resultSet.getInt("test");
                System.out.println(result);
                assertEquals(2005, result);
            } catch (SQLException e) {
                System.out.println("Some error when run resultSet.next()");
                throw new RuntimeException(e);
            }
        }
    }
}
