import java.util.Objects;

/**
 * Class representing a book in library.
 */
public class Book extends Document {
    private String ISBN;        // ISBN code of book.
    private String edition;     // Edition of book.

    /**
     * Constructor with 4 parameters.
     *
     * @param title title of Book.
     * @param publisher publisher of Book.
     * @param author author of Book.
     * @param ISBN ISBN code of Book.
     */
    public Book(String title, String author, String publisher, String ISBN) {
        super(title, author, publisher);
        this.ISBN = ISBN;
        setDocumentId();
        setDocumentType();
    }

    /**
     * Constructor for copy from another Book.
     *
     * @param book another Book.
     */
    public Book(Book book) {
        super(book);
        this.ISBN = book.getISBN();
        this.edition = book.getEdition();
    }

    public String getISBN() {
        return ISBN;
    }

    /**
     * Set ISBN code for a book.
     * When change ISBN, it changes documentId.
     *
     * @param ISBN ISBN code of book.
     * @return this book to support method chaining.
     */
    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        setDocumentId();
        return this;
    }

    public String getEdition() {
        return edition;
    }

    public Book setEdition(String edition) {
        this.edition = edition;
        return this;
    }

    /**
     * Generate documentId for book base on ISBN value using hash.
     *
     * @return documentId for book base on ISBN value.
     */
    @Override
    public int genId(){
        return Objects.hash(this.getISBN());
    }

    /**
     * Convert from Book to String.
     *
     * @return String of Book.
     */
    @Override
    public String toString() {
        return this.getDocumentType().toString() + ":"
                + "\n\tISBN: " + this.getISBN()
                + "\n\tEdition: " + this.getEdition()
                + "\n\t" + super.toString();
    }

    /**
     * Check if initialization is valid to generate ID.
     * Throw illegal argument exception when title, publisher, author, ISBN
     * is null or empty.
     */
    @Override
    public void checkValidInit() {
        super.checkValidInit();
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
    }
}
