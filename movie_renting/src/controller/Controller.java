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
    
    /**
     *
     * @param movies
     */
    public boolean rentMovies(List<Integer> movies, String cardNumber, String cvv, String email, String offerCode){
        
        Movie movie = new Movie(); 
        Customer customer = this.getCustomer(cardNumber, cvv, email);
        
        LocalDateTime rentalDate = LocalDateTime.now();
        LocalDateTime expectedRetunDate = getExpectedRetunDate(rentalDate, customer, movies.size());
       
        if(!customer.isFirstRental()){
            if(movies.size() > 0 && movies.size() <= 4){
                for(Integer m : movies){
                    Movie m1 = movie.get("movie_id", String.valueOf(m));
                    customer.rent(m1, rentalDate, expectedRetunDate, offerCode);
                }
                customer.save();
            } else{
                //throw
            }
        } else {
           if(movies.size() > 0 && movies.size() <= 2){
               for(Integer m : movies){
                    Movie m1 = movie.get("movie_id", String.valueOf(m));
                    customer.rent(m1, rentalDate, expectedRetunDate, offerCode);
                }
               customer.save();
           }else{
               
           }
        }
        
        return false; 
    }
    
    public boolean checkoutMovie(Movie movie){
        return false;
    }
    
  
    
    public static void main(String [] args){
        
        Movie movie = new Movie(01, "Harry Potter and Philosopher Stone", "An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.", "");
        //movie.save();
        movie.setAvailable(false);
        movie.update();
        movie.setAvailable(true);
        movie.update();
        
        Movie m2 = movie.get("movie_id", "1");
        System.out.println(m2);
        
        List<Movie> movies = movie.list("available", "1");
        System.out.printf("Size list %d", movies.size());
        
        Customer customer = new Customer("1234567891011", "123");
        System.out.println(customer);
        customer.setCVV("321");
        customer.update();
        Customer customer2 = customer.get("card_number", "1234567891011");
        System.out.println(customer2);
        List<Customer> customers = customer.list("1", "1");
        System.out.printf("Size list %d", customers.size());
        //customer.save();
        
    }
    
    private Customer getCustomer(String cardNumber, String cvv, String email){
        Customer customer = new Customer();
        customer = customer.get("card_number", cardNumber);
        if(customer == null)
           customer = new Customer(cardNumber, cvv, email, 1);
        return customer;
    }
    
    private LocalDateTime getExpectedRetunDate(LocalDateTime rentalDate, Customer customer, int moviesAmount){
        
        LocalDateTime expectedRetunDate = null;
        
        if(customer.isFirstRental()){
            LocalDateTime expectedReturnDate = rentalDate.plusDays(1);   
        } else if(moviesAmount == 1){
            LocalDateTime expectedReturnDate = rentalDate.plusDays(1);   
        } else if(moviesAmount == 2){
            LocalDateTime expectedReturnDate = rentalDate.plusDays(2);   
        } else if(moviesAmount == 3){
            LocalDateTime expectedReturnDate = rentalDate.plusDays(3);   
        } else if(moviesAmount == 4) {
            LocalDateTime expectedReturnDate = rentalDate.plusDays(4);   
        } else{
            //throw
        }
        
        return LocalDateTime.of(expectedRetunDate.toLocalDate(), LocalTime.of(8, 0, 0));
    }
}
