package controller;

import exception.CVVException;
import exception.CardNumberException;
import exception.InvalidNumberMoviesException;
import exception.QueryModelException;
import exception.SaveModelException;
import exception.UpdateModelException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Customer;
import model.Movie;
import model.Rental;

/**
 * Controller receives the requests from view forms. 
 * @thiago and @carlos 
 */
public final class Controller {
    
    
    private Customer customer; 
    
    /**
     * 
     */
    public Controller(){
        createSession();
    }
    
    /**
     * 
     */
    public void createSession(){
        this.customer = new Customer();
    }
    
    
    /**
     * Returns a list of movie without customer proposal rents. 
     * @return the list of available movies with the baket movies to rent. 
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
     * Adds a movie to a current customer user. 
     * @param movieId
     * @param cardNumber
     * @param cvv
     * @param email 
     * @throws exception.InvalidNumberMoviesException 
     */
    public void addMovie(Integer movieId, String cardNumber, String cvv, String email) throws InvalidNumberMoviesException, QueryModelException, CardNumberException, CVVException{
        
        Movie movie = new Movie(); 
        this.customer = this.getCustomer(cardNumber, cvv, email);
        System.out.println(customer);
        int moviesAmount = customer.getNumberOpenRentals() + 1;
        
        LocalDateTime rentalDate = LocalDateTime.now();
        LocalDateTime expectedRetunDate = getExpectedRetunDate(rentalDate, customer, moviesAmount);
       
        if(checkFirstRentalRules(customer, moviesAmount)){
            Movie m1 = movie.get("movie_id", String.valueOf(movieId));
            customer.createRental(m1, rentalDate, expectedRetunDate);
            
        }else {
            String message = String.format("Invalid Number of Movies in the Shopping Basket: %d."
                    + "If this is your first time renting, you can only rent the maximum number of 2 movies, otherwise the maximum is 4.", moviesAmount);
            throw new InvalidNumberMoviesException(message);
        }
    }
    
    /**
     * 
     * @param movieId 
     */
    public void removeMovie(Integer movieId){
        this.customer.removeRental(movieId);
    }
    
    
    public List<String[]> listCustomerMovies(){
        List<Movie> movies = customer.listSessionMovies();
        List<String[]> moviesString = new ArrayList<>();
        for(Movie m: movies){
            String [] mString = {String.valueOf(m.getId()), m.getTitle(), m.getDescription()};
            moviesString.add(mString);
        }
        return moviesString;
    }
    
    /**
     * 
     * @param cardNumber
     * @param cvv
     * @param email
     * @param offerCode
     * @throws SQLException
     * @throws QueryModelException
     * @throws SaveModelException
     * @throws CardNumberException
     * @throws CVVException 
     */
    public void checkOut(String cardNumber, String cvv, String email, String offerCode) throws SQLException, QueryModelException, 
            SaveModelException, CardNumberException, CVVException{
        this.customer = getCustomer(cardNumber, cvv, email);
        System.out.println(this.customer);
        this.customer.checkOut(offerCode);
        this.customer = new Customer();
    }
    
    
    /**
     * 
     * @param cardNumber
     * @param cvv
     * @param email
     * @return
     * @throws QueryModelException
     * @throws CardNumberException
     * @throws CVVException 
     */
    private Customer getCustomer(String cardNumber, String cvv, String email) throws QueryModelException, CardNumberException, CVVException{
        Customer customer = new Customer();
        customer = customer.get("card_number", cardNumber);
        System.out.println(customer);
        if(customer == null){
           this.customer.setCardNumber(cardNumber);
           this.customer.setCVV(cvv);
           this.customer.setEmail(email);
           customer = this.customer;
        }else{
            customer.addRentals(this.customer.getRentals());
            customer.updateFirstRental();
        }
        return customer;
    }
    
    /**
     * 
     * @param rentalDate
     * @param customer
     * @param moviesAmount
     * @return
     * @throws InvalidNumberMoviesException 
     */
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
        
        return LocalDateTime.of(expectedRetunDate.toLocalDate(), LocalTime.of(18, 0, 0));
    }
    
    /**
     * 
     * @param customer
     * @param moviesAmount
     * @return 
     */
    private boolean checkFirstRentalRules(Customer customer, int moviesAmount){
         if(!customer.isFirstRental()){
             return moviesAmount > 0 && moviesAmount <= 4;
        } else {
             return moviesAmount > 0 && moviesAmount <= 2;
        }
    }
    
    /**
     * 
     * @return 
     */
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

    /**
     * 
     * @param id
     * @return
     * @throws SQLException
     * @throws QueryModelException
     * @throws UpdateModelException 
     */
    public String[] returnMovie(int id) throws SQLException, QueryModelException, UpdateModelException {
        Rental rental = new Rental();
        rental = rental.get("movie_id", String.valueOf(id));
        String [] value = rental.finish();
        createSession();
        return value;
    }
}
