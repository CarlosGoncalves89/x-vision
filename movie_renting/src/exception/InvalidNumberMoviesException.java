package exception;

/**
 * InvalidNumberMoviesException represents an exception of the number of movies allowed. 
 * @author carlos
 */
public class InvalidNumberMoviesException extends Throwable {

    /**
     * Constructs an exception with s message
     * @param s exception full message
     */
    public InvalidNumberMoviesException(String s) {
        super(s);
    }

}

