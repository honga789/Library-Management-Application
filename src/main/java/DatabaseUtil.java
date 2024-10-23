import java.sql.*;

/**
 * DatabaseUtil Class.
 * This class is used for connect and make data transfer with mysql database.
 */
public final class DatabaseUtil {
    private static boolean isSetupDriver = false;

    private DatabaseUtil() {}

    /**
     * Setup Driver for Java Database Connectivity.
     * Class name: com.mysql.cj.jdbc.Driver
     * Use Class.forName to set up class of Driver.
     * This method must be call before connect database.
     */
    public static void setupDriver() {
        if (!DatabaseUtil.isSetupDriver) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // main of method
            } catch (ClassNotFoundException e) {
                System.out.println("---------- ERROR WHEN SETUP DRIVER FOR DATABASE ------------");
                System.out.println("RuntimeException, e: " + e.toString());
                System.out.println("------------------------------------------------------------");
                throw new RuntimeException(e);
            }
            DatabaseUtil.isSetupDriver = true;
        }
    }

    /**
     * Connect to database.
     * @param url String, url of database. Use jdbc:mysql: to define protocol and sql language. Eg: "jdbc:mysql://localhost:3306/library_database"
     * @param userName String, username to connect to database
     * @param password String, password to connect to database
     * @return Connection
     */
    public static Connection connect(String url, String userName, String password) {
        if (!DatabaseUtil.isSetupDriver) return null;
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("---------- ERROR WHEN CONNECT TO DATABASE ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("------------------------------------------------------");
            throw new RuntimeException(e);
        }
    }

    /**
     * Execute method. Query must not return data.
     * @param connection Connection, returned at connect method.
     * @param SQLQuery String, sql query must not return data.
     */
    public static void execute(Connection connection, String SQLQuery) {
        if (connection == null) return;
        try {
            Statement statement = connection.createStatement();
            // check sql query and break if unsafe
            statement.execute(SQLQuery);
        } catch (SQLException e) {
            System.out.println("---------- ERROR WHEN EXECUTE ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("------------------------------------------");
            throw new RuntimeException(e);
        }
    }

    /**
     * ExecuteQuery method. Query must return data.
     * @param connection Connection, returned at connect method.
     * @param SQLQuery String, sql query must return data.
     * @return ResultSet, data returned of data.
     */
    public static ResultSet executeQuery(Connection connection, String SQLQuery) {
        if (connection == null) return null;
        try {
            Statement statement = connection.createStatement();
            // check sql query and break if unsafe
            return statement.executeQuery(SQLQuery);
        } catch (SQLException e) {
            System.out.println("---------- ERROR WHEN EXECUTE QUERY ------------");
            System.out.println("SQLException, e: " + e.toString());
            System.out.println("------------------------------------------------");
            throw new RuntimeException(e);
        }
    }
}
