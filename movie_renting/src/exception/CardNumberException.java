package exception;

/**
 * An exception that provides information on a Card Number validation error or 
 * other errors. 
 * @author Thiago
 */
public class CardNumberException extends Throwable {

    /**
     * Constructs an CardNumberException with a given reason message.
     * @param s - a description of the exception
     */
    public CardNumberException(String s) {
        super(s);
    }

}