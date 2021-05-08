/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author thiag
 */
public class Costumer {
    
    private String cardNumber; 
    private String cvv;
    private String email;
    private Boolean firstRental;
    
    public Costumer(String card, String cvv){
        this.cardNumber = card; 
        this.cvv = cvv;
    }
    
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    public void setCVV(String cvv){
        this.cvv = cvv;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email; 
    }
    
}
