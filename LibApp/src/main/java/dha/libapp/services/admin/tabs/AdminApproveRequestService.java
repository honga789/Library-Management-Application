package dha.libapp.services.admin.tabs;

import dha.libapp.controllers.admin.tabs.AdminApproveRequestController;
import dha.libapp.controllers.admin.tabs.AdminReturnRequestController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowRecord;
import dha.libapp.models.BorrowStatus;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AdminApproveRequestService {
    public static void renderApproveBooks() {
        AdminApproveRequestController.getInstance().setApproveListViewVisible(true);

        BorrowRecordSyncDAO.getAllBorrowRecordsByStatusSync(BorrowStatus.PENDING, new DAOExecuteCallback<List<BorrowRecord>>() {
            @Override
            public void onSuccess(List<BorrowRecord> result) {
                AdminApproveRequestController.getInstance().renderApproveBooks(result);

                AdminApproveRequestController.getInstance().setLoadingPaneVisible(false);
                AdminApproveRequestController.getInstance().setApproveListViewVisible(true);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException();
            }
        });
    }
}
