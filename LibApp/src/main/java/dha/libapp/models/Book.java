package dha.libapp.models;

import java.util.ArrayList;
import java.util.Date;

public class Book {
    private int bookId;
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private Date publicationDate;
    private int quantity;
    private String description;
    private String coverImagePath;
    private ArrayList<GenreType> genreList;

    public Book() {
    }

    public Book(int bookId, String ISBN, String title, String author, String publisher,
                Date publicationDate, int quantity, String description, String coverImagePath,
                ArrayList<GenreType> genreList)
    {
        this.bookId = bookId;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.quantity = quantity;
        this.description = description;
        this.coverImagePath = coverImagePath;
        this.genreList = genreList;
    }

    public Book(Book book) {
        this.bookId = book.getBookId();
        this.ISBN = book.getISBN();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.publicationDate = book.getPublicationDate();
        this.quantity = book.getQuantity();
        this.description = book.getDescription();
        this.coverImagePath = book.getCoverImagePath();
        this.genreList = book.getGenreList();
    }

    public int getBookId() {
        return bookId;
    }

    public Book setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getISBN() {
        return ISBN;
    }

    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Book setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Book setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public Book setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
        return this;
    }

    public ArrayList<GenreType> getGenreList() {
        return genreList;
    }

    public Book setGenreList(ArrayList<GenreType> genreList) {
        this.genreList = genreList;
        return this;
    }
    @Override
    public String toString() {
        String genreString = "";
        for (GenreType genreType : genreList) {
            genreString += genreType.getGenreName() + ", ";
        }
        genreString = genreString.substring(0, genreString.length() - 2);
        return String.format(
                "\nName: %s\nAuthor: %s\nGenres: %s\n ",
                title, author, genreString
        );
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }
}
