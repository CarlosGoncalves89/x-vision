package exception;

/**
 * An exception that provides information on a database access error or other errors 
 * during query executions. 
 * 
 * @author Thiago
 */
public class QueryModelException extends Throwable {

     /**
     * Constructs an QueryModelException with a given reason message.
     * @param s - a description of the exception
     */
    public QueryModelException(String s) {
        super(s);
    }
}
