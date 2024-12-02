package dha.libapp.dao;

import dha.libapp.MainApp;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.utils.Database.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAO {

    public static List<BorrowRecord> getAllBorrowRecords() throws SQLException {
        List<BorrowRecord> result = new ArrayList<>();

        try (PreparedStatement preStm = DBUtil.getPrepareStatement(MainApp.getDbConnection(), "SELECT * FROM Borrow_record");
             ResultSet resultSet = preStm.executeQuery()) {
            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                Date borrowDate = resultSet.getDate("borrow_date");
                Date dueDate = resultSet.getDate("return_date");
                BorrowStatus borrowStatus = BorrowStatus.valueOf(resultSet.getString("status"));
                Date returnDate = resultSet.getDate("return_date");
                BorrowRecord temp = new BorrowRecord(userId, bookId, borrowDate, dueDate, borrowStatus, returnDate);
                temp.setBorrowId(borrowId);
                result.add(temp);
                if (resultSet.isClosed()) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static void main(String[] args) throws SQLException {
        List<BorrowRecord> test = new ArrayList<>();
        //Connection connection = DBUtil.connect("jdbc:mysql://b0dhldnmrpv8rotqmh6y-mysql.services.clever-cloud.com/b0dhldnmrpv8rotqmh6y", "uoxesvpdndreask6", "LTpg5gRkVYgDyuiSKjt3");
        try {
            test = getAllBorrowRecords();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (test.isEmpty()) {
            return;
        } else {
            for (BorrowRecord br : test) {
                br.displayInfo();
            }
        }
    }
}
