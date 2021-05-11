/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Movie;

/**
 *
 * @author 
 */
public class Controller {
    
    private List<Movie> movies; 
    
    public Controller() throws SQLException{
        this.movies = null; 
    }
    
    /**
     * @return 
    */
    public List<Movie> listMovies(){
        if(this.movies == null){
            Movie movie = new Movie();
            this.movies = movie.list("available", "1");
        }
        return this.movies;
    }
    
  
    
    public boolean checkoutMovie(Movie movie){
        return false;
    }
    
 
    
    private Customer getCustomer(String cardNumber, String cvv, String email){
        Customer customer = new Customer();
        customer = customer.get("card_number", cardNumber);
        if(customer == null)
           customer = new Customer(cardNumber, cvv, email, 1);
        return customer;
    }
    
    
}
