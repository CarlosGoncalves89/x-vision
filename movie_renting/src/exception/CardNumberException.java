/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author thiago
 */
public class CardNumberException extends Throwable {

    /**
     * Constructs an exception with s message
     * @param s exception full message
     */
    public CardNumberException(String s) {
        super(s);
    }

}