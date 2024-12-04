package dha.libapp.models;

import java.util.Date;

public class BorrowRecord {

    private int borrowId;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date dueDate;
    private BorrowStatus status;
    private Date returnDate;

    public int getBorrowId() {
        return borrowId;
    }

    public BorrowRecord setBorrowId(int borrowId) {
        this.borrowId = borrowId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public BorrowRecord setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public BorrowRecord setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public BorrowRecord setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public BorrowRecord setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public BorrowRecord setStatus(BorrowStatus status) {
        this.status = status;
        return this;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public BorrowRecord setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }



    public BorrowRecord() {
    }

    public BorrowRecord(int borrowId, int userId, int bookId, Date borrowDate, Date dueDate, BorrowStatus status, Date returnDate) {
        this.borrowId = borrowId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
        this.returnDate = returnDate;
    }
    public BorrowRecord(BorrowRecord borrowRecord) {
        this.borrowId = borrowRecord.borrowId;
        this.userId = borrowRecord.userId;
        this.bookId = borrowRecord.bookId;
        this.borrowDate = borrowRecord.borrowDate;
        this.dueDate = borrowRecord.dueDate;
        this.status = borrowRecord.status;
        this.returnDate = borrowRecord.returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord [borrowId=" + borrowId + ", userId=" +
                userId + ", bookId=" + bookId + ", borrowDate=" +
                borrowDate + ", dueDate=" + dueDate +
                ", status=" + status + ", returnDate=" + returnDate + "]";
    }

    public void displayInfo() {
        System.out.println(toString());
    }

}
