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

/**
 * Data Access Object (DAO) for managing the Book entity in the database.
 * Provides methods for performing CRUD operations on the Book table and
 * interacting with related tables (e.g., Book_genre_type).
 */
public class BookDAO {

    /**
     * Constant representing an invalid quantity value.
     */
    public final static int INVALID_QUANTITY = -1;

    /**
     * Converts a ResultSet to a Book object.
     *
     * @param resultSet      The ResultSet containing book data.
     * @param getDeletedBook If true, retrieves deleted books (quantity <= INVALID_QUANTITY).
     * @return A Book object corresponding to the data in the ResultSet, or null if no data is found.
     */
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

            // If the book's quantity is invalid, and we are not fetching deleted books, return null.
            if (!getDeletedBook && quantity <= INVALID_QUANTITY) {
                return null;
            }

            return book;
        } catch (SQLException e) {
            System.out.println("Error when getting Book from ResultSet");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all books from the database.
     *
     * @return A list of all Book objects.
     * @throws RuntimeException If an error occurs during the database query.
     */
    public static List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book ORDER BY book_id DESC";

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
            System.out.println("Error when retrieving all books");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param bookId The ID of the book to retrieve.
     * @return The Book object with the specified ID, or null if no book is found.
     * @throws RuntimeException If an error occurs during the database query.
     */
    public static Book getBookById(int bookId) {
        String sql = "SELECT * FROM Book WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, bookId);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, false);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when retrieving book by ID");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param ISBN The ISBN of the book to retrieve.
     * @return The Book object with the specified ISBN, or null if no book is found.
     * @throws RuntimeException If an error occurs during the database query.
     */
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

    /**
     * Retrieves a deleted book by its ID.
     *
     * @param bookId The ID of the book to retrieve.
     * @return The deleted Book object with the specified ID, or null if no book is found.
     * @throws RuntimeException If an error occurs during the database query.
     */
    public static Book getDeletedBookById(int bookId) {
        String sql = "SELECT * FROM Book WHERE book_id = ? AND quantity <= ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, bookId, INVALID_QUANTITY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, true);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when retrieving deleted book by ID");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a deleted book by its ISBN.
     *
     * @param ISBN The ISBN of the book to retrieve.
     * @return The deleted Book object with the specified ISBN, or null if no book is found.
     * @throws RuntimeException If an error occurs during the database query.
     */
    public static Book getDeletedBookByISBN(String ISBN) {
        String sql = "SELECT * FROM Book WHERE ISBN = ? AND quantity <= ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, ISBN, INVALID_QUANTITY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet, true);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when retrieving deleted book by ISBN");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new book to the database.
     *
     * @param ISBN            The ISBN of the new book.
     * @param title           The title of the new book.
     * @param author          The author of the new book.
     * @param publisher       The publisher of the new book.
     * @param publicationDate The publication date of the new book.
     * @param quantity        The quantity of the new book.
     * @param description     A description of the new book.
     * @param coverImagePath  The cover image path for the new book.
     * @param genreList       The list of genre types associated with the new book.
     * @throws RuntimeException If an error occurs during the database insertion.
     */
    public static void addNewBook(String ISBN, String title, String author, String publisher,
                                  Date publicationDate, int quantity, String description,
                                  String coverImagePath, ArrayList<GenreType> genreList) {
        String sql = "INSERT INTO Book(ISBN, title, author, publisher, publication_date, "
                + "quantity, description, cover_image_path) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, ISBN, title, author, publisher, publicationDate, quantity, description, coverImagePath)) {

            preparedStatement.executeUpdate();
            Book book = getBookByISBN(ISBN);
            if (book != null) {
                for (GenreType genreType : genreList) {
                    GenreTypeDAO.addGenreTypeToBook(book.getBookId(), genreType.getGenreId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error when adding new book");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing book in the database.
     *
     * @param bookId          The ID of the book to update.
     * @param ISBN            The ISBN of the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publisher       The publisher of the book.
     * @param publicationDate The publication date of the book.
     * @param quantity        The quantity of the book.
     * @param description     A description of the book.
     * @param coverImagePath  The cover image path of the book.
     * @param genreList       The list of genre types associated with the book.
     * @throws RuntimeException If an error occurs during the database update.
     */
    public static void updateBook(int bookId, String ISBN, String title, String author, String publisher,
                                  Date publicationDate, int quantity, String description,
                                  String coverImagePath, ArrayList<GenreType> genreList) {
        String sql = "UPDATE Book SET ISBN = ?, title = ?, author = ?, publisher = ?, "
                + "publication_date = ?, quantity = ?, description = ?, cover_image_path = ? "
                + "WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, ISBN, title, author, publisher, publicationDate, quantity, description, coverImagePath, bookId)) {

            preparedStatement.executeUpdate();
            GenreTypeDAO.deleteGenreTypeFromBook(bookId);
            for (GenreType genreType : genreList) {
                GenreTypeDAO.addGenreTypeToBook(bookId, genreType.getGenreId());
            }
        } catch (SQLException e) {
            System.out.println("Error when updating book");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing book in the database based on a Book object.
     *
     * @param book The Book object containing the updated details.
     * @throws RuntimeException If an error occurs during the database update.
     */
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
            System.out.println("Error when updating book");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param bookId The ID of the book to delete.
     * @throws RuntimeException If an error occurs during the database deletion.
     */
    public static void deleteBookById(int bookId) {
        String sql = "UPDATE Book SET quantity = ? WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, INVALID_QUANTITY, bookId)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when deleting book by ID");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a book from the database by its ISBN.
     *
     * @param ISBN The ISBN of the book to delete.
     * @throws RuntimeException If an error occurs during the database deletion.
     */
    public static void deleteBookByISBN(String ISBN) {
        Book book = getBookByISBN(ISBN);
        if (book == null) {
            return;
        }

        deleteBookById(book.getBookId());
    }

    /**
     * Searches for books by title.
     *
     * @param title The title to search for.
     * @return A list of books matching the title.
     * @throws RuntimeException If an error occurs during the database query.
     */
    public static List<Book> searchBookByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE MATCH(title) AGAINST (? IN NATURAL LANGUAGE MODE)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, title);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet, false);
                if (book != null) {
                    books.add(book);
                }
            }

            if (books.isEmpty()) {
                String sql2 = "SELECT * FROM Book WHERE title LIKE ? ORDER BY book_id DESC";
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
            System.out.println("Error when searching book by title");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the most popular (trending) books.
     *
     * @param quantityToGet The number of trending books to retrieve.
     * @return A list of the most popular books.
     * @throws RuntimeException If an error occurs during the database query.
     */
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
            System.out.println("Error when retrieving trending books");
            throw new RuntimeException(e);
        }
    }
}
