package model;

import exception.CVVException;
import exception.CardNumberException;
import exception.QueryModelException;
import exception.SaveModelException;
import exception.UpdateModelException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import validator.CVVValidator;
import validator.CardNumberValidator;

/**
 * Customer represents a person who rents a movie in X-vision machine. 
 * 
 * @thiago
 */
public class Customer implements Model<Customer>{
    
    private String cardNumber; 
    private String cvv;
    private String email;
    private int firstRental; 
    private List<Rental> rentals; 
    
    /**
     * Default constructor creates a customer to be used only with the Model's methods. 
     * 
     */
    public Customer(){
        this.rentals = new ArrayList<>();
        this.firstRental = 1;
        this.email = "";
    }
    
    /**
     * Creates a customer using only the credit (or debit) card number and card 
     * verification value. 
     * @param cardNumber credit card number
     * @param cvv card verification value
    */
    public Customer(String cardNumber, String cvv) throws CardNumberException, CVVException{
        this();
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    /**
     * Creates a customer using only credit (or debit) card number, card 
     * verification value (cvv) and if this customer is doing the 
     * first rental.
     * 
     * @param cardNumber - credit (or debit) card number
     * @param cvv - card verification value
     * @param firstRental - if this is the first customer rental
     */
    public Customer(String cardNumber, String cvv, int firstRental) throws CardNumberException, CVVException {
        this(cardNumber, cvv);
        this.firstRental = firstRental;
    }
    
        /**
     * Creates a customer using only credit (or debit) card number, card 
     * verification value (cvv), email address and if this customer is doing the 
     * first rental. 
     * @param cardNumber - credit (or debit) card number
     * @param cvv - card verification value
     * @param email - the costumer's email address (used to receive the receipt and/or news)
     * @param firstRental - if this is the first customer rental
     */
    public Customer(String cardNumber, String cvv, String email, int firstRental) throws CardNumberException, CVVException {
        this(cardNumber, cvv, firstRental);
        setEmail(email);
    }

    
    /**
     * Sets a new card number value if the cardNumber has length between 12 and 16 digits.
     * @param cardNumber - a credit (or debit) card number 
     * @throws exception.CardNumberException 
     */
    public void setCardNumber(String cardNumber) throws CardNumberException{       
        System.out.println("cardNumber" + cardNumber);
        if(cardNumber.equals("") || CardNumberValidator.validate(cardNumber))
            this.cardNumber = cardNumber;
    }
    
    /**
     * Returns the card number. 
     * @return a credit (or debit) card number. 
     */
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    /***
     * Sets a new card verification value to a Customer's card if cvv has length equals 3.  
     * @param cvv - card verification value
     */
    public void setCVV(String cvv) throws CVVException{
        if(cvv.equals("") || CVVValidator.validate(cvv))
            this.cvv = cvv;
        else
            throw new CVVException("The Card Verification Value contains alpha-numeric values or size different of 3 or 4 digits.");
    }
    
    /***
     * Sets a new email address. Sets the new email value only if the email has 
     * an '@' symbol and length less or equal than 40 caracters.
     * @param email email address to receive the receipts or news movies messages.
     */
    public void setEmail(String email){
        email = email.trim();
        email = email.replaceAll(" ", "");
        if(email.contains("@") && email.length() <= 40)        
            this.email = email;
    }
    
    /***
     * Returns the customer email address or 'None' if the email is empty.
     * @return the customer's email address 
     */
    public String getEmail(){
        return this.email.length() == 0 ? "None" : this.email; 
    }
    
    /**
     * Returns if it is the first customer's rental. 
     * @return 
     *      if this is the first customer's rental. 
     */
    public boolean isFirstRental(){
        return this.firstRental == 1;
    }
    
    /**
     * Updates customer status as a non-first use.
     */
    public void updateFirstRental(){
        if(this.firstRental == 1)
            this.firstRental = 0;
    }
    
    public void addRentals(List<Rental> rentals){
        this.rentals = rentals;
    }
    
    public List<Rental> getRentals(){
        return this.rentals;
    }
    /**
     * Creates a rental of a movie from a rentalDate until a expected return date.The customer
 can use offer codes to get discounts in the total movie rental value. 
     * @param movie the movie rented
     * @param rentalDate the current date
     * @param expectedReturnDate the rental date plus a number of days 
     */
    public void createRental(Movie movie, LocalDateTime rentalDate, LocalDateTime expectedReturnDate){
        Rental rental = new Rental(movie, this, "", rentalDate, expectedReturnDate); 
        movie.setAvailable(false);
        rentals.add(rental);
    }
    
    /**
     * Checkouts the movies and applies if this rental the offer code
     * @param offerCode
     * @throws SQLException
     * @throws SaveModelException 
     */
    public void checkOut(String offerCode) throws SQLException, SaveModelException{
        for(Rental rental: rentals){
            if(!rental.isFinished() && !rental.isPaymentDone()){
                rental.doFirstPayment(offerCode);
                rental.getMovie().setAvailable(false);
            }
        }
        if(isFirstRental()){   
            System.out.println("first rental");
            this.firstRental = 0;
            this.save();
        }else{
            System.out.println("second rental");
            this.firstRental = 0;
            System.out.println("siz: "+rentals.size());
            for(Rental rental : rentals){
                rental.setCustomer(this);
                rental.save();
            }
        }
    }
    /**
     * Returns if offercode was used in any other rental. 
     * @param offerCode codes to get discounts
     * @return True - if the offer code was never used.
     * False - if any other rental used the offer code
     */
    public boolean isAvailableOfferCode(String offerCode){
        for(Rental rental: rentals){
            if(rental.equals(offerCode)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns a list of the Customer's open rentals. 
     *  @return a list of rental that does not have the payment made.  
     */
    public List<Movie> listSessionMovies(){
        List<Movie> movies = new ArrayList<>();
        for(Rental rental : rentals){
            if(!rental.isFinished() && !rental.isPaymentDone()){
                movies.add(rental.getMovie());
            }
        }
        return movies;
    }
    
    /**
     * Removes a rental of a specified movie by its id if the rental is not finished.
     * @param movieId the Movie's dvd code
     */
    public void removeRental(int movieId){
        Rental current = null;
        for(Rental rental : rentals){
            if(!rental.isFinished() &&  rental.getMovie().getId() == movieId)
                current = rental;
        }
        if(current != null)
            rentals.remove(current);
    }
    
    /**
     * Returns the number of rentals without returns. 
     * @return amount of rentals of the customer that was not finished. 
     */
    public int getNumberOpenRentals(){
        int soma = 0;
        soma = this.rentals.stream().filter(rental -> (!rental.isFinished())).map(_item -> 1).reduce(soma, Integer::sum);
        return soma;
    }
    

    /**
     * Saves the customer object, its rentals not finished and payments. 
     *
     */
    @Override
    public void save() throws SaveModelException{
        
        try {
            
            if(this.cardNumber == null || !CardNumberValidator.validate(this.cardNumber))
                throw new SaveModelException("The card number can't be null. Check the value.");
            else if(this.cvv == null ||  !CVVValidator.validate(cvv))
                throw new SaveModelException("The card verification value can't be null. Check your cvv.");
            
            String insert = String.format("insert into customer (card_number, cvv, "
                    + "email, first_rental) values ('%s', '%s', '%s', '%d')",
                    this.cardNumber, this.cvv, this.email, this.firstRental);
            DbConnection dbConnection = null;
            dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(insert);
            for(Rental rental : rentals){
                if(!rental.isFinished()){
                    rental.save();
                }
            }
        } catch (CardNumberException ex) {
            throw new SaveModelException(ex.getMessage());
        } catch (SQLException ex) {
            String message = "Persistent problem happened while Customer were saving. Check the main causes: " + ex.getMessage();
            throw new SaveModelException(message);
        }
    }

    /***
     * Updates the customer information, its rentals situation and payments. 
     * 
     */
    @Override
    public void update() throws UpdateModelException {
        
        try {
            if(this.cardNumber == null || !CardNumberValidator.validate(this.cardNumber))
                throw new UpdateModelException("The card number can't be null. Check the value.");
            else if(this.cvv == null ||  CVVValidator.validate(cvv))
                throw new UpdateModelException("The card verification value can't be null. Check your cvv.");
            
            String updateSql = String.format("update customer set cvv = '%s', email = '%s', "
                + "first_rental = '%d' where card_number = '%s'", 
                this.cvv, this.email, this.firstRental, this.cardNumber);
            
            DbConnection dbConnection = null;
            dbConnection.execute(updateSql);
            for(Rental rental : rentals){
                if(!rental.isFinished()){
                    rental.update();
                }
            }
            
        } catch (CardNumberException ex) {
             throw new UpdateModelException(ex.getMessage());
        } catch (SQLException ex) {
            String message = "Persistent problem happened while Customer were updating. Check the main causes: " + ex.getMessage();
            throw new UpdateModelException(message);
        }
        
        
       
    }

    /**
     * Returns a customer object if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Customer object with its rentals and associated payments. 
     */
    @Override
    public Customer get(String property, String value) throws QueryModelException {
        
        String query = String.format("select card_number, cvv, email, first_rental from customer"
                + " where %s = '%s'", property, value);
        Customer customer = null; 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String ccardNumber = rs.getString("card_number");
                String ccvv = rs.getString("cvv");
                String cemail = rs.getString("email");
                Integer cfirstRental = rs.getInt("first_rental");
                customer = new Customer(ccardNumber, ccvv, cemail, cfirstRental);
                Rental rental = new Rental();
                rental.setCustomer(customer);
            }
            
        } catch (SQLException | CardNumberException | CVVException ex) {
            String message = "Select problem happened while we were getting a customer. Check the main causes: " + ex.getMessage();
            throw new QueryModelException(message);
        }
        
        return customer;
    }

    /***
     * Returns a list of customers if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Customer object with its rentals and associated payments. 
     */
    @Override
    public List<Customer> list(String property, String value) throws QueryModelException {
         String query = String.format("select card_number, cvv, email, first_rental from"
                 + " customer where %s = '%s'", property, value);
        
        List<Customer> customers = new ArrayList<>(); 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String ccardNumber = rs.getString("card_number");
                String ccvv = rs.getString("cvv");
                String cemail = rs.getString("email");
                Integer cfirstRental = rs.getInt("first_rental");
                Customer customer = new Customer(ccardNumber, ccvv, cemail, cfirstRental);
                Rental rental = new Rental();
                rental.setCustomer(customer);
                customer.rentals = rental.list("card_number", ccardNumber);
                customers.add(customer);
            }
            
        } catch (SQLException | CardNumberException | CVVException ex) {
            String message = "Select problem happened while we were getting a customer. Check the main causes: " + ex.getMessage();
            throw new QueryModelException(message);
        }
        
        return customers;
    }

    /**
     * Returns hashcode using the customer attributes.
     * @return an integer hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.cardNumber);
        hash = 97 * hash + Objects.hashCode(this.cvv);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + this.firstRental;
        return hash;
    }

    /**
     * Returns if this object is equals to obj.
     * @param obj an object
     * @return if this object has the same state of obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        return true;
    }

    /**
     * Returns a String representation of the object. 
     * @return 
     */
    @Override
    public String toString() {
        return "Customer{" + "cardNumber=" + getCardNumber() + ", cvv=" + cvv + ","
                + " email=" + getEmail() + ", firstRental=" + isFirstRental() + '}';
    } 
}
