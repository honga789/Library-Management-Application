import java.io.Serializable;
import java.util.Date;

/**
 * Abstract class representing a document in the library.
 */
public abstract class Document implements IManaged {
    private int documentId;               // ID of the document.
    private DocumentType documentType;    // Type of the document.
    private String title;                 // Title of the document.
    private String publisher;             // Publisher of the document.
    private String author;                // Author of the document.
    private Date publicationDate;         // Publication Date of the document.
    private String language;              // Language of the document.
    private String location;              // Location in the library.
    private String genre;                 // Genre of the document.
    private int quantity;                 // Quantity of the document.
    private int pages;                    // Number of pages in the document.
    private double price;                 // Price of the document.
    private String description;           // Description for the document.
    private String coverImagePath;        // The cover image path of the document.

    /**
     * Default constructor.
     */
    public Document() {}

    /**
     * Constructor with three parameters.
     *
     * @param title title of Document.
     * @param publisher publisher of Document.
     * @param author author of Document.
     */
    public Document(String title, String publisher, String author) {
        this.title = title;
        this.publisher = publisher;
        this.author = author;
    }

    /**
     * Constructor for copy from another Document.
     *
     * @param document another Document.
     */
    public Document(Document document) {
        this.documentId = document.getDocumentId();
        this.documentType = document.getDocumentType();
        this.title = document.getTitle();
        this.publisher = document.getPublisher();
        this.author = document.getAuthor();
        this.publicationDate = document.getPublicationDate();
        this.language = document.getLanguage();
        this.location = document.getLocation();
        this.genre = document.getGenre();
        this.quantity = document.getQuantity();
        this.pages = document.getPages();
        this.price = document.getPrice();
        this.description = document.getDescription();
        this.coverImagePath = document.getCoverImagePath();
    }

    public int getDocumentId() {
        return documentId;
    }

    /**
     * Automatically set Document ID when call it.
     * If some value to create ID is null or empty, it will throw exceptions.
     */
    public void setDocumentId() {
        checkValidInit();
        this.documentId = this.genId();
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Automatically set Document type when call it.
     */
    public void setDocumentType() {
        if (this instanceof Book) {
            this.documentType = DocumentType.BOOK;
        } else if (this instanceof Magazine) {
            this.documentType = DocumentType.MAGAZINE;
        } else if (this instanceof Newspaper) {
            this.documentType = DocumentType.NEWSPAPER;
        }
    }

    public String getTitle() {
        return title;
    }

    public Document setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public Document setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Document setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Document setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Document setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Document setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Document setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Document setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public Document setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Document setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Document setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public Document setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
        return this;
    }

    public abstract int genId();

    /**
     * Convert from Document to String.
     *
     * @return String of Document.
     */
    @Override
    public String toString() {
        return "ID: " + this.documentId
                + "\n\tTitle: " + this.title
                + "\n\tAuthor: " + this.author
                + "\n\tPublication Date: " + this.publicationDate
                + "\n\tLanguage: " + this.language
                + "\n\tLocation: " + this.location
                + "\n\tGenre: " + this.genre
                + "\n\tQuantity: " + this.quantity
                + "\n\tPages: " + this.pages
                + "\n\tPrice: " + this.price
                + "\n\tDescription: " + this.description
                + "\n\tCover Image Path: " + this.coverImagePath;
    }

    /**
     * Compare Document with another Object.
     *
     * @param obj another Object.
     * @return result of compare.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Document) {
            Document doc = (Document) obj;
            return this.documentId == doc.getDocumentId();
        }
        return false;
    }

    /**
     * Display information of Document.
     */
    public void displayInfo() {
        System.out.println(this);
    }

    /**
     * Check if initialization is valid to generate ID.
     * Throw illegal argument exception when title, publisher, author
     * is null or empty.
     */
    public void checkValidInit() {
        if ( (title == null || title.isEmpty())
                || (publisher == null || publisher.isEmpty())
                || (author == null || author.isEmpty()) ) {

            throw new IllegalArgumentException("title, publisher, author "
                    + "cannot not be null or empty");
        }
    }

    /**
     * get id of class type Document.
     *
     * @return id of type Document.
     */
    @Override
    public int getId() {
        return this.documentId;
    }
}
