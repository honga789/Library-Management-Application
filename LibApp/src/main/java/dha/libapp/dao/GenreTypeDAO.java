package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.GenreType;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle database operations for GenreType.
 */
public class GenreTypeDAO {

    /**
     * Converts a ResultSet to a GenreType object.
     *
     * @param resultSet The ResultSet containing the data from the database.
     * @return A GenreType object corresponding to the genre data in the ResultSet or null if the ResultSet is null.
     */
    private static GenreType getGenreTypeFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }
            int genre_id = resultSet.getInt("genre_id");
            String genre_name = resultSet.getString("genre_name");
            float weight = resultSet.getFloat("weight");

            return new GenreType(genre_id, genre_name, weight);
        } catch (SQLException e) {
            System.out.println("Error when get genretype from resultset");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all GenreTypes from the database.
     *
     * @return A list of GenreType objects representing all genre types in the database.
     */
    public static List<GenreType> getAllGenreType() {
        List<GenreType> genreTypeList = new ArrayList<>();
        String sql = "SELECT * FROM Genre_type";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                genreTypeList.add(getGenreTypeFromResultSet(resultSet));
            }
            return genreTypeList;
        } catch (SQLException e) {
            System.out.println("Error when get all genretype");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a GenreType by its ID.
     *
     * @param genre_id The ID of the GenreType.
     * @return A GenreType object corresponding to the provided ID or null if not found.
     */
    public static GenreType getGenreTypeById(int genre_id) {
        String sql = "SELECT * FROM Genre_type WHERE genre_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, genre_id);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getGenreTypeFromResultSet(resultSet);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get genretype by id");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a GenreType by its name.
     *
     * @param genre_name The name of the GenreType.
     * @return A GenreType object corresponding to the provided name or null if not found.
     */
    public static GenreType getGenreTypeByName(String genre_name) {
        String sql = "SELECT * FROM Genre_type WHERE genre_name = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, genre_name);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getGenreTypeFromResultSet(resultSet);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get genretype by name");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a GenreType to a book in the database.
     *
     * @param book_id  The ID of the book.
     * @param genre_id The ID of the GenreType to associate with the book.
     */
    public static void addGenreTypeToBook(int book_id, int genre_id) {
        String sql = "INSERT INTO Book_genre_type(book_id, genre_id) VALUES(?,?)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, book_id, genre_id)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when add genre type to Book");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the GenreType association from a book in the database.
     *
     * @param book_id The ID of the book.
     */
    public static void deleteGenreTypeFromBook(int book_id) {
        String sql = "DELETE FROM Book_genre_type WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, book_id)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when delete genre type from Book");
            throw new RuntimeException(e);
        }
    }
}
