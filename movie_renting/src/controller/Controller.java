/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exception.InvalidNumberMoviesException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import model.Customer;
import model.Movie;
import model.Rental;

/**
 *
 * @author 
 */
public class Controller {
    
    
    private Customer customer; 
    
    public Controller(){
        this.customer = new Customer();
    }
    /**
     * @return 
    */
    public List<String[]> listMovies(){
       
        List<String[]> available = this.listAllAvailableMovies();
        List<String[]> moviesCustomer = this.listCustomerMovies();
        
        Iterator<String[]> i = available.iterator();

        while(i.hasNext()) {
            String [] movieS = i.next();
            for(String [] cMovie : moviesCustomer){
                if(movieS[0].equalsIgnoreCase(cMovie[0])){
                    i.remove();
                }
            }
        }
        
        return available;
    }
    
    /**
     *
     * @param movieId
     * @param cardNumber
     * @param cvv
     * @param email
     * @param offerCode 
     * @throws exception.InvalidNumberMoviesException 
     */
    public void addMovie(Integer movieId, String cardNumber, String cvv, String email) throws InvalidNumberMoviesException{
        
        Movie movie = new Movie(); 
        customer = this.getCustomer(cardNumber, cvv, email);
        int moviesAmount = customer.getNumberOpenRentals() + 1;
        
        LocalDateTime rentalDate = LocalDateTime.now();
        LocalDateTime expectedRetunDate = getExpectedRetunDate(rentalDate, customer, moviesAmount);
       
        if(checkFirstRentalRules(customer, moviesAmount)){
            Movie m1 = movie.get("movie_id", String.valueOf(movieId));
            customer.rent(m1, rentalDate, expectedRetunDate);
            
        }else {
            String message = String.format("Invalid Number of Movies in the Shopping Basket: %d."
                    + "If this is your first time renting, you can only rent the maximum number of 2 movies, otherwise the maximum is 4.", moviesAmount);
            throw new InvalidNumberMoviesException(message);
        }
    }
    
    public void removeMovie(Integer movieId){
        this.customer.removeRental(movieId);
    }
    
    
    public List<String[]> listCustomerMovies(){
        List<Movie> movies = customer.listOpenRentalMovies();
        List<String[]> moviesString = new ArrayList<>();
        for(Movie m: movies){
            String [] mString = {String.valueOf(m.getId()), m.getTitle(), m.getDescription()};
            moviesString.add(mString);
        }
        return moviesString;
    }
    
    public void checkOut(String cardNumber, String cvv, String email, String offerCode) throws SQLException{
        this.customer = getCustomer(cardNumber, cvv, email);
        this.customer.checkOut(offerCode);
    }
    
    
    private Customer getCustomer(String cardNumber, String cvv, String email){
        Customer customer = new Customer();
        customer = customer.get("card_number", cardNumber);
        if(customer == null){
           this.customer.setCardNumber(cardNumber);
           this.customer.setCVV(cvv);
           this.customer.setEmail(email);
           customer = this.customer;
        }
        return customer;
    }
    
    private LocalDateTime getExpectedRetunDate(LocalDateTime rentalDate, Customer customer, int moviesAmount) throws InvalidNumberMoviesException{
        
        LocalDateTime expectedRetunDate = null;
        
        if(customer.isFirstRental()){
            expectedRetunDate = rentalDate.plusDays(1);   
        } else if(moviesAmount == 1){
            expectedRetunDate = rentalDate.plusDays(1);   
        } else if(moviesAmount == 2){
            expectedRetunDate = rentalDate.plusDays(2);   
        } else if(moviesAmount == 3){
            expectedRetunDate = rentalDate.plusDays(3);   
        } else if(moviesAmount == 4) {
            expectedRetunDate = rentalDate.plusDays(4);   
        } else{
            String message = String.format("Invalid Number of Movies in the Shopping Basket: %d."
                    + "If this is your first time renting, you can only rent the maximum number of 2 movies, otherwise the maximum is 4.", moviesAmount);
            throw new InvalidNumberMoviesException(message);
        }
        
        return LocalDateTime.of(expectedRetunDate.toLocalDate(), LocalTime.of(8, 0, 0));
    }
    
    private boolean checkFirstRentalRules(Customer customer, int moviesAmount){
         if(!customer.isFirstRental()){
             return moviesAmount > 0 && moviesAmount <= 4;
        } else {
             return moviesAmount > 0 && moviesAmount <= 2;
        }
    }
    
    private List<String[]> listAllAvailableMovies(){
        Movie movie = new Movie();
        List<Movie> movies = movie.list("available", "1");
        List<String[]> moviesString = new ArrayList<>();
        for(Movie m: movies){
            String [] mString = {String.valueOf(m.getId()), m.getTitle(), m.getDescription()};
            moviesString.add(mString);
        }
        return moviesString;
    }

    public String[] returnMovie(int id) throws SQLException {
        Rental rental = new Rental();
        rental = rental.get("movie_id", String.valueOf(id));
        return rental.finish();
    }
}
