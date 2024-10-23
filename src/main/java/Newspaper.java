/**
 * Class representing a newspaper in library.
 */
public class Newspaper extends Periodical{

    /**
     * Constructor with 4 parameters.
     *
     * @param title       title of Newspaper.
     * @param author      author of Newspaper.
     * @param publisher   publisher of Newspaper.
     * @param issueNumber issue number of Newspaper.
     */
    public Newspaper(String title, String author, String publisher, String issueNumber) {
        super(title, author, publisher, issueNumber);
    }

    /**
     * Constructor for copy from another Newspaper.
     *
     * @param magazine another Newspaper.
     */
    public Newspaper(Magazine magazine) {
        super(magazine);
    }
}
