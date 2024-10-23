import java.sql.*;

public final class DatabaseUtil {
    private static boolean isSetupDriver = false;

    private DatabaseUtil() {}

    public static void setupDriver() {
        if (!DatabaseUtil.isSetupDriver) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // main of method
            } catch (ClassNotFoundException e) {
                System.out.println("---------- SOME ERROR ------------");
                System.out.println("RuntimeException, e: " + e.toString());
                System.out.println("----------------------------------");
                throw new RuntimeException(e);
            }
            DatabaseUtil.isSetupDriver = true;
        }
    }

    public static Connection connect(String url, String userName, String password) {
        if (!DatabaseUtil.isSetupDriver) return null;
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("---------- SOME ERROR ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("----------------------------------");
            throw new RuntimeException(e);
        }
    }

    public static void execute(Connection connection, String SQLQuery) {
        if (connection == null) return;
        try {
            Statement statement = connection.createStatement();
            // check sql query and break if unsafe
            statement.execute(SQLQuery);
        } catch (SQLException e) {
            System.out.println("---------- SOME ERROR ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("----------------------------------");
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuery(Connection connection, String SQLQuery) {
        if (connection == null) return null;
        try {
            Statement statement = connection.createStatement();
            // check sql query and break if unsafe
            return statement.executeQuery(SQLQuery);
        } catch (SQLException e) {
            System.out.println("---------- SOME ERROR ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("----------------------------------");
            throw new RuntimeException(e);
        }
    }
}
