/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Card Verification Value validator.
 * @author thiago
 */
public class CVVValidator implements Validator {
    
    /**
     * Validates if the card validation value if correct
     * @param cvv
     * @return True if is only digits and has 3 or 4 digits. 
     * False otherwise
     */
    public static boolean validate(String cvv){
 
        //regex to check if is only number characteres and size between 3 or 4 digits. 
        String regex = "^[0-9]{3,4}$";
        Pattern p = Pattern.compile(regex);
        if (cvv == null){
            throw new NullPointerException("Card Verification value can't be null. Check your cvv number");
        } else{
            Matcher matcher = p.matcher(cvv);
            return matcher.matches();
        }
    }
}
