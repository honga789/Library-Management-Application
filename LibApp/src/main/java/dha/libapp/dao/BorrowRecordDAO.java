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

public class BorrowRecordDAO {
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

    public static List<BorrowRecord> getAllBorrowRecords()  {
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

    public static List<BorrowRecord> getAllBorrowRecordsByStatus(BorrowStatus status) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE status = ?";

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

    public static List<BorrowRecord> getAllBorrowRecordsByUserIdAndStatus(int user_id, BorrowStatus status) {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record WHERE user_id = ? AND status = ?";

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
}
