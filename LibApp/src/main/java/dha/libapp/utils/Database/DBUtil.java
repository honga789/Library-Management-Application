package dha.libapp.utils.Database;

import java.sql.*;

/**
 * Utility class for managing database connections and operations.
 * This class simplifies the process of setting up the database driver,
 * creating connections, and preparing SQL statements with parameters.
 */
public class DBUtil {

    /**
     * Flag to ensure the database driver is set up only once.
     */
    private static boolean isDriverSetup = false;

    /**
     * Sets up the MySQL JDBC driver if it has not already been initialized.
     * Throws a runtime exception if the driver is not found.
     */
    private static void setupDriver() {
        try {
            if (!isDriverSetup) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                isDriverSetup = true;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    /**
     * Establishes a connection to the database using the provided URL, username, and password.
     *
     * @param url      the database URL
     * @param username the username for the database connection
     * @param password the password for the database connection
     * @return a Connection object to interact with the database
     * @throws RuntimeException if there is an error connecting to the database
     */
    public static Connection connect(String url, String username, String password) {
        if (!isDriverSetup) {
            setupDriver();
        }
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Database Connection Error......");
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * Creates a PreparedStatement for the given SQL query and sets the specified parameters.
     *
     * @param connection the Connection object
     * @param sql        the SQL query with placeholders for parameters
     * @param params     the parameters to set in the PreparedStatement
     * @return a PreparedStatement ready for execution
     * @throws RuntimeException if there is an error preparing the statement
     */
    public static PreparedStatement getPrepareStatement(Connection connection, String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, params);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select query", e);
        }
    }

    /**
     * Sets parameters for a PreparedStatement.
     *
     * @param preparedStatement the PreparedStatement to set parameters on
     * @param params            the parameters to set in the statement
     * @throws SQLException if there is an error setting the parameters
     */
    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }
}
