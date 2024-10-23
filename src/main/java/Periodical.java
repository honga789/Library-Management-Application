import java.util.Objects;

/**
 * Abstract class representing a periodical document in library.
 */
public abstract class Periodical extends Document {
    private String issueNumber;     // Issue number of periodical document.
    private String frequency;       // Frequency of periodical document.

    /**
     * Constructor with 4 parameters.
     *
     * @param title title of Periodical document.
     * @param author author of Periodical document.
     * @param publisher publisher of Periodical document.
     * @param issueNumber issue number of Periodical document.
     */
    public Periodical(String title, String author, String publisher, String issueNumber) {
        super(title, author, publisher);
        this.issueNumber = issueNumber;
        setDocumentId();
        setDocumentType();
    }

    /**
     * Constructor for copy from another Periodical document.
     *
     * @param periodical another Periodical document.
     */
    public Periodical(Periodical periodical) {
        super(periodical);
        this.issueNumber = periodical.getIssueNumber();
        this.frequency = periodical.getFrequency();
    }

    /**
     * Set title for a periodical document.
     * When change title, it changes documentId.
     *
     * @param title title of periodical document.
     * @return this periodical document to support method chaining.
     */
    @Override
    public Document setTitle(String title) {
        super.setTitle(title);
        setDocumentId();
        return this;
    }

    /**
     * Set publisher for a periodical document.
     * When change publisher, it changes documentId.
     *
     * @param publisher publisher of periodical document.
     * @return this periodical document to support method chaining.
     */
    @Override
    public Document setPublisher(String publisher) {
        super.setPublisher(publisher);
        setDocumentType();
        return this;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    /**
     * Set issue number for a periodical document.
     * When change issue number, it changes documentId.
     *
     * @param issueNumber issue number of periodical document.
     * @return this periodical document to support method chaining.
     */
    public Periodical setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
        setDocumentId();
        return this;
    }

    public String getFrequency() {
        return frequency;
    }

    public Periodical setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    /**
     * Generate documentId for periodical document base on title, publisher,
     * and issueNumber using hash.
     *
     * @return documentId for periodical document base on title, publisher and issuerNumber value.
     */
    @Override
    public int genId() {
        return Objects.hash(this.getTitle(), this.getPublisher(), this.getIssueNumber());
    }

    /**
     * Convert from Periodical document to String.
     *
     * @return String of Periodical document.
     */
    @Override
    public String toString() {
        return this.getDocumentType().toString() + ":"
                + "\n\tIssue Number: " + this.getIssueNumber()
                + "\n\tFrequency: " + this.getFrequency()
                + "\n\t" + super.toString();
    }

    /**
     * Check if initialization is valid to generate ID.
     * Throw illegal argument exception when title, publisher, author, issue number
     * is null or empty.
     */
    @Override
    public void checkValidInit() {
        super.checkValidInit();
        if (issueNumber == null || issueNumber.isEmpty()) {
            throw new IllegalArgumentException("Issue Number cannot be empty");
        }
    }
}
