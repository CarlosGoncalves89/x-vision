package exception;

/**
 * An exception that provides information on a Card Verification Value validation
 * error or other errors.
 * @author Carlos
 */
public class CVVException extends Throwable {

    /**
     * Constructs an CVVException with a given reason message.
     * @param s - a description of the exception
     */
    public CVVException(String s) {
        super(s);
    }

}
