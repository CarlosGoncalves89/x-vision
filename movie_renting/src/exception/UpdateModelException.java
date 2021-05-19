package exception;

/**
 * An exception that provides information on a database access error or other errors 
 * during update executions. 
 * 
 * @author Thiago
 */
public class UpdateModelException extends Throwable {

    /**
     * Constructs an UpdateModelException with a given reason message.
     * @param s - a description of the exception
     */
    public UpdateModelException(String s) {
        super(s);
    }
}
