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
    public final static int INVALID_QUANTITY = -1;

    private static Book getBookFromResultSet(ResultSet resultSet, boolean getDeletedBook) {
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
            String coverImagePath = resultSet.getString("cover_image_path");
            book.setBookId(bookId)
                .setISBN(ISBN)
                .setTitle(title)
                .setAuthor(author)
                .setPublisher(publisher)
                .setPublicationDate(publicationDate)
                .setQuantity(quantity)
                .setDescription(description)
                .setCoverImagePath(coverImagePath);

            ArrayList<GenreType> genreList = new ArrayList<>();
            String sql = "SELECT * FROM Book_genre_type WHERE book_id = ?";
            try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, bookId);
                ResultSet resultSet1 = preparedStatement.executeQuery()) {

                while (resultSet1.next()) {
                    int genreId = resultSet1.getInt("genre_id");
                    genreList.add(GenreTypeDAO.getGenreTypeById(genreId));
                }
            }

            book.setGenreList(genreList);
            if (!getDeletedBook && quantity <= INVALID_QUANTITY) {
                return null;
            }
            return book;
        } catch (SQLException e) {
            System.out.println("Error when get Book from ResultSet");
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getAllBook() {
        List<Book> books = new ArrayList<Book>();
        String sql = "SELECT * FROM Book "
                    + "ORDER BY book_id DESC";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet, false);
                if (book != null) {
                    books.add(book);
                }
            }
            return books;
        } catch (Exception e) {
            System.out.println("Error when get all book");
            throw new RuntimeException(e);
        }
    }

    public static Book getBookById(int bookId) {
        String sql = "SELECT * FROM Book WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, bookId);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, false);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get Book by id");
            throw new RuntimeException(e);
        }
    }

    public static Book getBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, ISBN);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, false);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Book getDeletedBookById(int bookId) {
        String sql = "SELECT * FROM Book WHERE book_id = ? AND quantity <= ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, bookId, INVALID_QUANTITY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, true);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get deleted book by id");
            throw new RuntimeException(e);
        }
    }

    public static Book getDeletedBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN = ? AND quantity <= ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, ISBN, INVALID_QUANTITY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, true);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get deleted book by id");
            throw new RuntimeException(e);
        }
    }

    public static void addNewBook(String ISBN, String title, String author, String publisher,
                                  Date publicationDate, int quantity, String description,
                                  String coverImagePath, ArrayList<GenreType> genreList) {
        String sql = "INSERT INTO Book(ISBN, title, author, publisher, publication_date, "
                + "quantity, description, cover_image_path) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement prepareStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, ISBN, title, author, publisher, publicationDate, quantity, description, coverImagePath)) {

            prepareStatement.executeUpdate();
            Book book = getBookByISBN(ISBN);
            if (book != null) {
                for (GenreType genreType : genreList) {
                    GenreTypeDAO.addGenreTypeToBook(book.getBookId(), genreType.getGenreId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error when add new book");
            throw new RuntimeException(e);
        }
    }

    public static void updateBook(int book_id, String ISBN, String title, String author, String publisher,
                                  Date publicationDate, int quantity, String description,
                                  String coverImagePath, ArrayList<GenreType> genreList) {
        String sql = "UPDATE Book SET ISBN = ?, title = ?, author = ?, publisher = ?, "
                    + "publication_date = ?, quantity = ?, description = ?, cover_image_path = ? "
                    + "WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, ISBN, title, author, publisher, publicationDate, quantity, description, coverImagePath, book_id)) {

            preparedStatement.executeUpdate();
            GenreTypeDAO.deleteGenreTypeFromBook(book_id);
            for (GenreType genreType : genreList) {
                GenreTypeDAO.addGenreTypeToBook(book_id, genreType.getGenreId());
            }
        } catch (SQLException e) {
            System.out.println("Error when update book");
            throw new RuntimeException(e);
        }
    }

    public static void updateBook(Book book) {
        String sql = "UPDATE Book SET ISBN = ?, title = ?, author = ?, publisher = ?, "
                + "publication_date = ?, quantity = ?, description = ?, cover_image_path = ? "
                + "WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, book.getISBN(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                book.getPublicationDate(), book.getQuantity(), book.getDescription(),
                book.getCoverImagePath(), book.getBookId())) {

            preparedStatement.executeUpdate();
            GenreTypeDAO.deleteGenreTypeFromBook(book.getBookId());
            for (GenreType genreType : book.getGenreList()) {
                GenreTypeDAO.addGenreTypeToBook(book.getBookId(), genreType.getGenreId());
            }
        } catch (SQLException e) {
            System.out.println("Error when update book");
            throw new RuntimeException(e);
        }
    }

    public static void deleteBookById(int bookId) {
        String sql = "UPDATE Book SET quantity = ? WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, INVALID_QUANTITY, bookId)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when delete book by id");
            throw new RuntimeException(e);
        }
    }

    public static void deleteBookByISBN(String ISBN) {
        Book book = getBookByISBN(ISBN);
        if (book == null) {
            return;
        }

        deleteBookById(book.getBookId());
    }

    public static List<Book> searchBookByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE MATCH(title) AGAINST "
                    + "(? IN NATURAL LANGUAGE MODE)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, title);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet, false);
                if (book != null) {
                    books.add(book);
                }
            }

            if (books.isEmpty()) {
                String sql2 = "SELECT * FROM Book WHERE title LIKE ? "
                            + "ORDER BY book_id DESC";
                title = '%' + title + '%';

                try (PreparedStatement preparedStatement1 = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql2, title);
                    ResultSet resultSet1 = preparedStatement1.executeQuery()) {

                    while (resultSet1.next()) {
                        Book book = getBookFromResultSet(resultSet1, false);
                        if (book != null) {
                            books.add(book);
                        }
                    }
                }
            }
            return books;
        } catch (Exception e) {
            System.out.println("Error when search book by title");
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getTrendingBooks(int quantityToGet) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, COUNT(br.book_id) AS num "
                    + "FROM Borrow_record br JOIN Book b ON br.book_id = b.book_id "
                    + "WHERE b.quantity > 0 GROUP BY br.book_id ORDER BY num DESC LIMIT ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, quantityToGet);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet, false);
                if (book != null) {
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Error when get trending books");
            throw new RuntimeException(e);
        }
    }
}
