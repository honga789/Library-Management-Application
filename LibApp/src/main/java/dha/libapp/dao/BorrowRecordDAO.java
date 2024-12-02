package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.utils.Database.DBUtil;

import java.sql.*;
import java.util.ArrayList;
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
            System.out.println("Error when get borrowrecord from resultset");
            throw new RuntimeException(e);
        }
    }

    public static List<BorrowRecord> getAllBorrowRecords() throws SQLException {
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        String sql = "SELECT * FROM Borrow_record";

        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowRecordList.add(getBorrowRecordFromResultSet(resultSet));
            }
            return borrowRecordList;
        } catch (SQLException e) {
            System.out.println("Error when get all borrowrecord");
            throw new RuntimeException(e);
        }
    }

    public static BorrowRecord getBorrowRecordById(int borrow_id) {
        String sql = "SELECT * FROM Borrow_record WHERE borrow_id = ?";
        try (PreparedStatement preparedStatement = DBUtil.getPrepareStatement(MainApp.getDbConnection(), sql, borrow_id);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return getBorrowRecordFromResultSet(resultSet);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error when get borrowrecord by id");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        List<BorrowRecord> borrowRecordList = getAllBorrowRecords();
        for (BorrowRecord borrowRecord : borrowRecordList) {
            System.out.println(borrowRecord);
        }

        System.out.println();
        System.out.println(getBorrowRecordById(1));
    }
}
