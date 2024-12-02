package dha.libapp.dao;

import com.sun.tools.javac.Main;
import dha.libapp.MainApp;
import dha.libapp.models.GenreType;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreTypeDAO {
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

    public static void main(String[] args) {
        List<GenreType> genreTypeList = getAllGenreType();
        for (GenreType genreType : genreTypeList) {
            System.out.println(genreType);
        }

        System.out.println();
        System.out.println(getGenreTypeById(1));
    }
}
