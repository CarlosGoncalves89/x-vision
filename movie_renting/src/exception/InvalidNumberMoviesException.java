package exception;

/**
 * InvalidNumberMoviesException represents an exception of the number of movies allowed. 
 * 
 * @author Carlos
 */
public class InvalidNumberMoviesException extends Throwable {

     /**
     * Constructs an InvalidNumberMoviesException with a given reason message.
     * @param s - a description of the exception
     */
    public InvalidNumberMoviesException(String s) {
        super(s);
    }

}

