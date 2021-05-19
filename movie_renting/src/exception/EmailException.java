package exception;

/**
 * An exception that provides information on a Email validation error or other errors.
 * 
 * @author Thiago
 */
public class EmailException  extends Throwable {

    /**
     * Constructs an EmailException with a given reason message.
     * @param s - a description of the exception
     */
    public EmailException(String s) {
        super(s);
    }
}
