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
 * @uthors Thiago and Carlos 
 */
public final class Controller {
    
    
    private Customer customer; 
    
    /**
     * Creates a controller object with a new customer session. 
     */
    public Controller(){
        createSession();
    }
    
    /**
     * Creates a new empty customer to new session in the Xvision machine. 
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
     * Adds a movie to a current customer's basket. 
     * @param movieId the movie disc number
     * @param cardNumber the customer credit (debit) card number
     * @param cvv card verification value
     * @param email customer's e-mail address
     * @throws exception.InvalidNumberMoviesException if the new customer wants to rent more than 2 movie dics or registered customer wants to rent more than 4 movies. 
     * @throws exception.QueryModelException if a database query execution has failed. 
     * @throws exception.CardNumberException if the customer card number is not valid
     * @throws exception.CVVException if the cvv is not valid: between 3 or 4 caracter and only numbers. 
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
     * Removes a proposal movie's rental of the customer's basket. 
     * @param movieId movie dic number
     */
    public void removeMovie(Integer movieId){
        this.customer.removeRental(movieId);
    }
    
    
    /**
     * List all proposal movies of the session's customer. 
     * @return 
     */
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
     * Checks out a basket and saves the rentals and its payments. 
     * @param cardNumber customer's credit (or debit) card number
     * @param cvv card verification value
     * @param email customer's email addres
     * @param offerCode available offer code
     * @throws SQLException if a database access error or others errors. 
     * @throws SaveModelException if a database insertion execution has failed. 
     * @throws exception.QueryModelException if a database query execution has failed. 
     * @throws exception.CardNumberException if the customer card number is not valid
     * @throws exception.CVVException if the cvv is not valid: between 3 or 4 caracter and only numbers. 
     */
    public void checkOut(String cardNumber, String cvv, String email, String offerCode) throws SQLException, QueryModelException, 
            SaveModelException, CardNumberException, CVVException{
        this.customer = getCustomer(cardNumber, cvv, email);
        this.customer.checkOut(offerCode);
        this.customer = new Customer();
    }
    
    
    /**
     * Gets a customer if exists in database or creates a new one. 
     * @param cardNumber credit (or debit) customer card. 
     * @param cvv card verification value
     * @param email customer's email address
     * @return an existing customer
     * @throws QueryModelException if a database query execution has failed. 
     * @throws CardNumberException if the customer card number is not valid
     * @throws CVVException if the cvv is not valid: between 3 or 4 caracter and only numbers. 
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
     * Gets the expected return date depending if this is the first customer's Rental and the movies amount.
     * @param rentalDate the rental date
     * @param customer the customer
     * @param moviesAmount the number of proposal rentals. 
     * @return an expected return date 
     * @throws InvalidNumberMoviesException if the moviesAmount is greater than maximum movies number. 
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
        
        // return the expected return date limited until 8 P.M.
        return LocalDateTime.of(expectedRetunDate.toLocalDate(), LocalTime.of(20, 0, 0));
    }
    
    /**
     * Checks if this is the customer first rental and if the movies amout is correct. 
     * @param customer the customer
     * @param moviesAmount number of movie dics.
     * @return True - if the number of rented movies is correct
     * False - otherwise
     */
    private boolean checkFirstRentalRules(Customer customer, int moviesAmount){
         if(!customer.isFirstRental()){
             return moviesAmount > 0 && moviesAmount <= 4;
        } else {
             return moviesAmount > 0 && moviesAmount <= 2;
        }
    }
    
    /**
     * Returns a list of available Movies to be rented. 
     * @return a list of available movies
     * empty list - if all machine movies were rented. 
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
     * Returns a movie using its disc number.
     * @param id the movie disc number
     * @return String [3] - information about returning processing. 
     * @throws SQLException - if a database access error or others errors. 
     * @throws QueryModelException if a database query execution has failed. 
     * @throws UpdateModelException if a database update execution has failed. 
     */
    public String[] returnMovie(int id) throws SQLException, QueryModelException, UpdateModelException {
        Rental rental = new Rental();
        rental = rental.get("movie_id", String.valueOf(id));
        String [] value = null;
        if(rental != null){
            value = rental.finish();
            createSession();
        }else{
            value = new String[3];
            value[0] = "No movie";
            value[1] = "No movie";
            value[2] = "No movie";
        }
        
        return value;
    }
}
