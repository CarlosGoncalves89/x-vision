/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author carlos
 */
public class CVVException extends Throwable {

    /**
     * Constructs an exception with s message
     * @param s exception full message
     */
    public CVVException(String s) {
        super(s);
    }

}
