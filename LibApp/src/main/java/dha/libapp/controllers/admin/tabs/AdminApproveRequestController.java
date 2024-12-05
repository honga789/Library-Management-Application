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

    }
}
