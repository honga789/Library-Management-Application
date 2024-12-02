package dha.libapp.utils.Database;

import java.sql.*;

public class DBUtil {

    private static boolean isDriverSetup = false;

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

    public static Connection connect(String url, String username, String password) {
        if (!isDriverSetup) {
            setupDriver();
        }
        try {
            return DriverManager.getConnection(url, username, password);
            // return DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
        } catch (SQLException e) {
            System.out.println("Database Connection Error......");
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // UPDATE, INSERT, DELETE
    public static int executeUpdate(Connection connection, String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update query", e);
        }
    }

    // SELECT
    public static ResultSet executeQuery(Connection connection, String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            return resultSet; // Trả về ResultSet để sử dụng ngoài phương thức
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select query", e);
        }
    }

    public static PreparedStatement getPrepareStatement(Connection connection, String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, params);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select query", e);
        }
    }

    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }
}
