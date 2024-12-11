package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.utils.Database.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO class to handle database operations for BorrowRecord.
 */
public class BorrowRecordDAO {

    /**
     * Converts a ResultSet to a BorrowRecord object.
     *
     * @param resultSet The ResultSet containing the data from the database.
     * @return A BorrowRecord object or null if the ResultSet is null.
     */
    private static BorrowRecord getBorrowRecordFromResultSet(ResultSet resultSet) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }
            int borrow_id = resultSet.getInt("borrow_id");
            int user_id = resultSet.getInt("user_id");
            int book_id = resultSet.getInt("book_id");
            Date borrow_date = resultSet.getDate("borrow_date");
            Date due_date = resultSet.getDate("due_date");
            BorrowStatus status = BorrowStatus.valueOf(resultSet.getString("status"));
            Date return_date = resultSet.getDate("return_date");

            return new BorrowRecord(borrow_id, user_id, book_id, borrow_date, due_date,
                    status, return_date);
        } catch (SQLException e) {
            System.out.println("Error when get borrow record from result");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all borrow records from the database.
     *
     * @return A list of all BorrowRecord objects.
     */
    public static List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get all borrow record");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a borrow record by its ID.
     *
     * @param borrow_id The ID of the borrow record.
     * @return The BorrowRecord object corresponding to the provided ID or null if not found.
     */
    public static BorrowRecord getBorrowRecordById(int borrow_id) {
        String sql = "SELECT * FROM Borrow_record WHERE borrow_id = ?";
        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, borrow_id);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBorrowRecordFromResultSet(resultSet);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get borrow record by id");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all borrow records by user ID.
     *
     * @param user_id The user ID for which to retrieve borrow records.
     * @return A list of BorrowRecord objects corresponding to the user ID.
     */
    public static List<BorrowRecord> getAllBorrowRecordsByUserId(int user_id) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE user_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, user_id);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get borrow record by user_id");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all borrow records by book ID.
     *
     * @param book_id The book ID for which to retrieve borrow records.
     * @return A list of BorrowRecord objects corresponding to the book ID.
     */
    public static List<BorrowRecord> getAllBorrowRecordsByBookId(int book_id) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE book_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, book_id);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get borrow record by book_id");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all borrow records by status.
     *
     * @param status The status of the borrow records to retrieve.
     * @return A list of BorrowRecord objects with the specified status.
     */
    public static List<BorrowRecord> getAllBorrowRecordsByStatus(BorrowStatus status) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE status = ? ORDER BY borrow_date, user_id";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, status.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get borrow record by status");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves borrow records by user ID and status.
     *
     * @param user_id The user ID for which to retrieve borrow records.
     * @param status  The status of the borrow records.
     * @return A list of BorrowRecord objects for the user with the specified status.
     */
    public static List<BorrowRecord> getAllBorrowRecordsByUserIdAndStatus(int user_id, BorrowStatus status) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE user_id = ? AND status = ? ORDER BY borrow_date DESC";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, user_id, status.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get borrow record by user_id and status");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new borrow record to the database.
     *
     * @param userId     The user ID who is borrowing the book.
     * @param bookId     The book ID being borrowed.
     * @param borrowDate The date when the book is borrowed.
     * @param dueDate    The due date for returning the book.
     * @param status     The status of the borrow record.
     * @param returnDate The return date, if applicable.
     */
    public static void addNewBorrowRecord(int userId, int bookId, Date borrowDate, Date dueDate,
                                          BorrowStatus status, Date returnDate) {
        String sql = "INSERT INTO Borrow_record(user_id, book_id, borrow_date, due_date, "
                + "status, return_date) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, userId, bookId, borrowDate, dueDate, status.toString(), returnDate)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when add new borrow record");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing borrow record in the database.
     *
     * @param borrow_id  The borrow record ID to update.
     * @param borrowDate The new borrow date.
     * @param dueDate    The new due date.
     * @param status     The new status.
     * @param returnDate The new return date.
     */
    public static void updateBorrowRecord(int borrow_id, Date borrowDate, Date dueDate,
                                          BorrowStatus status, Date returnDate) {
        String sql = "UPDATE Borrow_record SET borrow_date = ?, due_date = ?, status = ?, "
                + "return_date = ? WHERE borrow_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, borrowDate, dueDate, status.toString(), returnDate, borrow_id)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when update borrow record");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing borrow record based on the BorrowRecord object.
     *
     * @param borrowRecord The BorrowRecord object containing updated information.
     */
    public static void updateBorrowRecord(BorrowRecord borrowRecord) {
        String sql = "UPDATE Borrow_record SET borrow_date = ?, due_date = ?, status = ?, "
                + "return_date = ? WHERE borrow_id = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, borrowRecord.getBorrowDate(), borrowRecord.getDueDate(), borrowRecord.getStatus().toString(),
                borrowRecord.getReturnDate(), borrowRecord.getBorrowId())) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when update borrow record");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates borrow records for a user based on initial and final statuses.
     *
     * @param user_id       The user ID.
     * @param initialStatus The initial status of the borrow records.
     * @param finalStatus   The final status to update the borrow records to.
     */
    public static void updateBorrowRecordByUserIdAndStatus(int user_id,
                                                           BorrowStatus initialStatus,
                                                           BorrowStatus finalStatus) {
        String sql = "UPDATE Borrow_record SET status = ? WHERE user_id = ? AND status = ?";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, finalStatus.toString(), user_id, initialStatus.toString())) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when update borrow record");
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for borrow records based on username and status.
     *
     * @param username The username of the user whose borrow records to retrieve.
     * @param status   The status of the borrow records to retrieve.
     * @return A list of borrow records that match the criteria.
     */
    public static List<BorrowRecord> searchBorrowRecordsByUsernameAndStatus(String username, BorrowStatus status) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT br.* FROM borrow_record br "
                + "JOIN user u ON br.user_id = u.user_id "
                + "LEFT JOIN deleted_user du ON u.user_id = du.deleted_user_id "
                + "WHERE (u.user_name LIKE ? OR du.deleted_user_name LIKE ?) "
                + "AND br.status = ? "
                + "ORDER BY borrow_date, br.user_id";
        username = username + "%";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(),
                sql, username, username, status.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BorrowRecord borrowRecord = getBorrowRecordFromResultSet(resultSet);
                if (borrowRecord != null) {
                    borrowRecordList.add(borrowRecord);
                }
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when search borrow record by username");
            throw new RuntimeException(e);
        }
    }
}
