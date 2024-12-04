package dha.libapp.services.members;

import dha.libapp.cache.members.PendingTabCache;
import dha.libapp.controllers.members.MemberViewController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowStatus;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;

import java.util.Calendar;
import java.util.Date;

public class UserBorrowBookService {

    public static void borrowBook(Book book) {
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, 30);
        BorrowRecordSyncDAO.addNewBorrowRecordSync(
                SessionService.getInstance().getUser().getUserId(),
                book.getBookId(),
                current, calendar.getTime(),
                BorrowStatus.PENDING, null,
                new DAOUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Borrow Pending");
                        PendingTabCache.getInstance().getPendingBookList().clear();
                        MemberViewController.getInstance().switchToBorrowPendingTab();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }
        );
    }

}
