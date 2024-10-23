/**
 * Class representing a magazine in library.
 */
public class Magazine extends Periodical{

    /**
     * Constructor with 4 parameters.
     *
     * @param title       title of Magazine.
     * @param author      author of Magazine.
     * @param publisher   publisher of Magazine.
     * @param issueNumber issue number of Magazine.
     */
    public Magazine(String title, String author, String publisher, String issueNumber) {
        super(title, author, publisher, issueNumber);
    }

    /**
     * Constructor for copy from another Magazine.
     *
     * @param magazine another Magazine.
     */
    public Magazine(Magazine magazine) {
        super(magazine);
    }
}
