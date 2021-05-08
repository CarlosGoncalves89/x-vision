/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author user
 */
public class Rental {
    
    private Movie movie;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
   
     public void setRentalDate(LocalDateTime rentalDate){
        if(rentalDate != null){
            this.rentalDate = rentalDate;
        }
    }
    
    public LocalDateTime getRentalDate(){
        return this.rentalDate;
    }
    
    public void setReturnDate(LocalDateTime returnDate){
        if(returnDate != null && returnDate.isAfter(this.rentalDate)){
            this.returnDate = returnDate;
        } else{
             //TODO throw an exception
        }
    }
    
    public LocalDateTime getReturnDate(){
        return this.returnDate;
    }
    
    public long diffDays(){
        return 0;
    }
    
}
