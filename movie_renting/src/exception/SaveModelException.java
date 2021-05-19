package exception;

/**
 * An exception that provides information on a database access error or other errors 
 * during insert executions. 
 * 
 * @author Thiago
 */
public class SaveModelException extends Throwable {

    /**
     * Constructs an SaveModelException with a given reason message.
     * @param s - a description of the exception
     */
    public SaveModelException(String s) {
        super(s);
    }
    
}
