package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.Book;
import dha.libapp.models.GenreType;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDAO {
    private static Book getBookFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }
            Book book = new Book();
            int bookId = resultSet.getInt("book_id");
            String ISBN = resultSet.getString("ISBN");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            Date publicationDate = resultSet.getDate("publication_date");
            int quantity = resultSet.getInt("quantity");
            String description = resultSet.getString("description");
            book.setBookId(bookId)
                .setISBN(ISBN)
                .setTitle(title)
                .setAuthor(author)
                .setPublisher(publisher)
                .setPublicationDate(publicationDate)
                .setQuantity(quantity)
                .setDescription(description);

            ArrayList<GenreType> genreList = new ArrayList<>();
            String sql = "SELECT * FROM Book_genre_type WHERE book_id = ?";
            try(PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, bookId);
                ResultSet resultSet1 = preparedStatement.executeQuery()) {
                while (resultSet1.next()) {
                    int genreId = resultSet1.getInt("genre_id");
                    genreList.add(GenreTypeDAO.getGenreTypeById(genreId));
                }
            }
            book.setGenreList(genreList);
            return book;
        } catch (SQLException e) {
            System.out.println("Error when get Book from ResultSet");
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getAllBook() {
        List<Book> books = new ArrayList<Book>();
        String sql = "SELECT * FROM Book";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }

            return books;
        } catch (Exception e) {
            System.out.println("Error when get all book");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Book> books = getAllBook();
        for (Book book : books) {
            System.out.println(book);

        }
    }
}
