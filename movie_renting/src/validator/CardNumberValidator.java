/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import exception.CardNumberException;

/**
 * CardNumberValidator validates if full card number is correct. 
 * @author carlos
 */
public class CardNumberValidator implements Validator {
    
    /**
     * Validates if the cardNumber is correct and valid. 
     * @param cardNumber full card number
     * @return True if card number has a correct size, prefix values and check sum. 
     * False other wise
     * @throws CardNumberException if any condition was not satisfied. 
     */
    public static boolean validate(String cardNumber) throws CardNumberException{
        
        if(cardNumber.length() >= 13 && cardNumber.length() <= 16){
           if(matchPrefix(cardNumber, 4) || matchPrefix(cardNumber, 5) || matchPrefix(cardNumber, 37)
                   || matchPrefix(cardNumber, 6)){
               int sumDoubleEvenOdd = sumDoubleEven(cardNumber) + sumOdd(cardNumber);
               System.out.println(sumDoubleEvenOdd);
               if(sumDoubleEvenOdd % 10 == 0){
                   return true;
               } else {
                   throw new CardNumberException("Incorrect card number value. Check your card number.");
               }
           }else{
               throw new CardNumberException("The usual card number must start with 4, 5, 37 or 6. Others prefixes values is not supported. Check your card number.");
           }
        } else {
            throw new CardNumberException("The card number must have between 13 and 16 digits. Check your card number.");
        }
    }
    
    /**
     * Matches the prefix card number with the integer value prefix. 
     * @param number full card number 
     * @param firstDigits one or more prefixes numbers
     * @return True - if the prefix value is equal the firstDigits param
     * False otherwise
     */
    private static boolean matchPrefix(String number, int firstDigits){
        String firstD = String.valueOf(firstDigits);
        if(number.length() > firstD.length()){
            String firsts = number.substring(0, firstD.length());
            if(firstD.equalsIgnoreCase(firsts)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the sum of double even positions digits.  
     * @param cardNumber
     * @return 
     */
    private static int sumDoubleEven(String cardNumber){
        int sum = 0, dValue;
        String digit;
        for(int i = cardNumber.length() - 2; i >= 0; i -= 2){
            digit = String.valueOf(cardNumber.charAt(i));
            dValue = Integer.valueOf(digit) * 2; 
            sum += getDigit(dValue);
        }
        return sum;
    }
    
    /**
     * Returns the sum of all digits in odd positions. 
     * @param cardNumber full card number
     * @return the sum of odd position digits
     */
    private static int sumOdd(String cardNumber){
        int sum = 0; 
        for(int i = cardNumber.length() - 1; i >= 0; i -= 2){
            String digit = String.valueOf(cardNumber.charAt(i));
            Integer dValue = Integer.valueOf(digit);
            sum += getDigit(dValue); 
        }
        return sum;
    }
    
    /***
     * Returns the correct digit to odd or double even number sum. 
     * @param digit integer value between 0 and 18
     * @return the digit if digit < 9 
     * the sum of digit / 10 and digit % 10
     */
    private static int getDigit(int digit){
        if(digit < 9){
            return digit;
        }else{
             return (digit / 10 + digit % 10);
        }
    }
}
