import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Objects;

public class LoanRecord {

    private int recordId;
    private int userId;
    private int documentId;
    private LocalDateTime borrowDate;
    private LocalDate dueDate;
    private LoanStatus loanStatus;
    private LocalDateTime returnDate;

    public int getRecordId() {
        return recordId;
    }

    public int getUserId() {
        return userId;
    }

    public LoanRecord setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public LoanRecord setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public LoanRecord setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanRecord setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public LoanRecord setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
        return this;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public LoanRecord setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    LoanRecord(int userId, int documentId,
               LocalDateTime borrowDate, LocalDate dueDate,
               LoanStatus loanStatus) {
        setUserId(userId);
        setDocumentId(documentId);
        setBorrowDate(borrowDate);
        setDueDate(dueDate);
        setLoanStatus(loanStatus);
        //set recordId after other
        setRecordId();

    }

    LoanRecord(LoanRecord loanRecord) {
        setUserId(loanRecord.getUserId());
        setDocumentId(loanRecord.getDocumentId());
        setBorrowDate(loanRecord.getBorrowDate());
        setDueDate(loanRecord.getDueDate());
        setLoanStatus(loanRecord.getLoanStatus());
        //set recordId after other
        setRecordId();
    }
    public int genId() {
        //use Hash to generate recordId from userId, documentId and BorrowDate
        return Objects.hash(this.getUserId(), this.getDocumentId(), this.getBorrowDate());
    }
    /**
     auto create recordID with genId.
     * */
    public void setRecordId() {
        checkValidInit();
        this.recordId = this.genId();
    }

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
    public void displayInfo() {
        System.out.println(this);
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof LoanRecord) {
            return this.getRecordId() == ((LoanRecord) o).getRecordId();
        } else {
            return false;
        }
    }
    public void checkValidInit() {
        if (this.borrowDate == null) {
            throw new IllegalArgumentException("BorrowDate cannot be null");
        }
    }

}
