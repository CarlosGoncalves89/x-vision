/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import exception.EmailException;

/**
 *
 * @author thiago
 */
public class EmailValidator implements Validator{
    
    public static boolean validate(String email) throws EmailException{
       
        if(email.length() > 10 && email.contains("@")){
            return true;
        } else {
            throw new EmailException("The email can't have less than 10 characteres and must have a @ symbol. Check your email address");
        }
    }
}
