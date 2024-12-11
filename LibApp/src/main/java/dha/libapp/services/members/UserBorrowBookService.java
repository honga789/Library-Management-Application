package dha.libapp.services.members;

import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.members.PendingTabCache;
import dha.libapp.controllers.members.MemberViewController;
import dha.libapp.models.Book;
import dha.libapp.models.BorrowStatus;
import dha.libapp.services.SessionService;
import dha.libapp.syncdao.BorrowRecordSyncDAO;
import dha.libapp.syncdao.utils.DAOUpdateCallback;

import java.util.Calendar;
import java.util.Date;

/**
 * Service class that handles the logic for borrowing a book for a user.
 * This service is responsible for adding a new borrow record and updating the UI accordingly.
 */
public class UserBorrowBookService {

    /**
     * Borrows a book for the current user.
     * The book is borrowed for 30 days from the current date, and the borrow status is set to "PENDING".
     * If the book is available, a new borrow record is created and the UI is updated to reflect the pending status.
     *
     * @param book The book to be borrowed.
     */
    public static void borrowBook(Book book) {
        if (book == null || book.getQuantity() < 1) {
            return;
        }

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
//                        PendingTabCache.getInstance().getPendingBookList().clear();
                        CacheFactory.getCache(PendingTabCache.class).clearAll();
                        MemberViewController.getInstance().switchToBorrowPendingTab();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }
        );
    }

}
