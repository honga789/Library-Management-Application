package dha.libapp.controllers.admin.tabs;

import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.services.admin.BorrowService;
import dha.libapp.syncdao.BookSyncDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Calendar;
import java.util.Date;

public class AdminApproveRequestController {
    @FXML
    private Button approveButton;
    private BorrowRecord tempBorrowRecord;
    private void initializeButton() {
        approveButton.setOnAction(event -> {
            approveBorrow(tempBorrowRecord);
        });
    }
    public void approveBorrow(BorrowRecord borrowRecord) {
        BorrowRecord approvedBorrowRecord = new BorrowRecord(borrowRecord);
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, 30);
        approvedBorrowRecord.setDueDate(calendar.getTime());
        approvedBorrowRecord.setStatus(BorrowStatus.BORROWED);
        try {
            BorrowService.getInstance().updateBorrowRecord(approvedBorrowRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
