import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a loan record for a document borrowed by a user.
 * Contains information about the user, document, borrow date, due date,
 * loan status, and return date.
 */
public class LoanRecord implements IManaged {

    private int recordId;
    private int userId;
    private int documentId;
    private LocalDateTime borrowDate;
    private LocalDate dueDate;
    private LoanStatus loanStatus;
    private LocalDateTime returnDate;

    /**
     * Constructs a LoanRecord object with the specified parameters.
     *
     * @param userId     the ID of the user borrowing the document
     * @param documentId the ID of the document being borrowed
     * @param borrowDate the date and time the document was borrowed
     * @param dueDate    the due date for returning the document
     * @param loanStatus the status of the loan
     */
    LoanRecord(int userId, int documentId,
               LocalDateTime borrowDate, LocalDate dueDate,
               LoanStatus loanStatus) {
        setUserId(userId);
        setDocumentId(documentId);
        setBorrowDate(borrowDate);
        setDueDate(dueDate);
        setLoanStatus(loanStatus);
        setRecordId();
    }

    /**
     * Copy constructor to create a new LoanRecord object from an existing one.
     *
     * @param loanRecord the existing LoanRecord object to copy
     */
    LoanRecord(LoanRecord loanRecord) {
        setUserId(loanRecord.getUserId());
        setDocumentId(loanRecord.getDocumentId());
        setBorrowDate(loanRecord.getBorrowDate());
        setDueDate(loanRecord.getDueDate());
        setLoanStatus(loanRecord.getLoanStatus());
        setRecordId();
    }


    /**
     * Gets the record ID.
     *
     * @return the record ID
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * Gets the user ID associated with this loan record.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for this loan record.
     *
     * @param userId the user ID to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setUserId(int userId) {
        this.userId = userId;
        setRecordId();
        return this;
    }

    /**
     * Gets the document ID associated with this loan record.
     *
     * @return the document ID
     */
    public int getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document ID for this loan record.
     *
     * @param documentId the document ID to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setDocumentId(int documentId) {
        this.documentId = documentId;
        setRecordId();
        return this;
    }

    /**
     * Gets the borrow date of the document.
     *
     * @return the borrow date
     */
    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the borrow date for the loan record.
     *
     * @param borrowDate the borrow date to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
        setRecordId();
        return this;
    }

    /**
     * Gets the due date for returning the document.
     *
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the document.
     *
     * @param dueDate the due date to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Gets the loan status of the document.
     *
     * @return the loan status
     */
    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    /**
     * Sets the loan status for the document.
     *
     * @param loanStatus the loan status to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
        return this;
    }

    /**
     * Gets the return date of the document, if returned.
     *
     * @return the return date
     */
    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date for the document.
     *
     * @param returnDate the return date to set
     * @return the updated LoanRecord object
     */
    public LoanRecord setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    /**
     * Generates a unique record ID using a hash of the user ID, document ID, and borrow date.
     *
     * @return the generated record ID
     */
    public int genId() {
        return Objects.hash(this.getUserId(), this.getDocumentId(), this.getBorrowDate());
    }

    /**
     * Automatically creates and sets the record ID using the genId method.
     */
    public void setRecordId() {
        checkValidInit();
        this.recordId = this.genId();
    }

    /**
     * Returns a string representation of the LoanRecord object.
     *
     * @return a string describing the LoanRecord
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loan Record:");
        sb.append("\n\tUserID: ").append(this.getUserId());
        sb.append("\n\tDocumentID: ").append(this.getDocumentId());
        sb.append("\n\tBorrowDate: ").append(this.getBorrowDate());
        sb.append("\n\tDueDate: ").append(this.getDueDate());
        sb.append("\n\tLoanStatus: ").append(this.getLoanStatus());
        sb.append("\n\tReturnDate: ").append(this.getReturnDate());
        return sb.toString();
    }

    /**
     * Displays information about the loan record in the console.
     */
    public void displayInfo() {
        System.out.println(this);
    }

    /**
     * Checks if two LoanRecord objects are equal based on their record IDs.
     *
     * @param o the object to compare with
     * @return true if the record IDs are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LoanRecord) {
            return this.getRecordId() == ((LoanRecord) o).getRecordId();
        } else {
            return false;
        }
    }

    /**
     * Validates the loan record before setting the record ID.
     * Ensures that the borrow date is not null.
     *
     * @throws IllegalArgumentException if the borrow date is null
     */
    public void checkValidInit() {
        if (this.borrowDate == null) {
            throw new IllegalArgumentException("BorrowDate cannot be null");
        }
    }

    /**
     * Returns the ID of the loan record.
     *
     * @return the record ID
     */
    @Override
    public int getId() {
        return this.recordId;
    }
}
