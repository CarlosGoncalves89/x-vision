/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import exception.CVVException;
import exception.CardNumberException;
import exception.QueryModelException;
import exception.SaveModelException;
import exception.UpdateModelException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @carlos 
 */
public class Rental implements Model<Rental> {
    
    private Integer id; 
    private Movie movie; 
    private Customer customer; 
    private String offerCode;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private LocalDateTime expectedReturnDate;
    private Boolean finished;
    private List<Payment> payments; 

    /**
     * 
     * @param movie
     * @param costumer
     * @param offerCode
     * @param rentalDate
     * @param expectedReturnDate 
     */
    public Rental(Movie movie, Customer costumer, String offerCode, LocalDateTime rentalDate, LocalDateTime expectedReturnDate) {
        this.movie = movie;
        this.customer = costumer;
        this.offerCode = offerCode;
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.finished = false; 
        this.payments = new ArrayList<>();
    }    

    /**
     * Default Rental constructor. 
     */
    public Rental() {
        
    }

    /**
     * Returns the rental id
     * @return 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns customer
     * @return Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns movie
     * @return movie rented
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets movie
     * @param movie 
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Sets customer
     * @param customer 
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the payments
     * @return 
     */
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * Returns offerCode
     * @return 
     */
    public String getOfferCode() {
        return offerCode;
    }
    
    /**
     * Sets offer code. 
     * @param offerCode 
     */
    public void setOfferCode(String offerCode) {
        offerCode = offerCode.trim();
        if(offerCode.length() > 0 && offerCode.length() <= 10)
            this.offerCode = offerCode;
    }
    
    /**
     * Sets rental date. 
     * @param rentalDate 
     */
    public void setRentalDate(LocalDateTime rentalDate){
        if(rentalDate != null){
            this.rentalDate = rentalDate;
        }
    }
    
    /**
     * Returns rental date. 
     * @return rental date
     */
    public LocalDateTime getRentalDate(){
        return this.rentalDate;
    }
    
    /**
     * Sets return date if its is after the rental date.
     * @param returnDate return date. 
     */
    public void setReturnDate(LocalDateTime returnDate){
        if(returnDate != null && returnDate.isAfter(this.rentalDate)){
            this.returnDate = returnDate;
        }
    }
    
    /**
     * 
     * @return 
     */
    public LocalDateTime getReturnDate(){
        return this.returnDate;
    }
    

    /**
     * Returns the rental condition
     * @return True - if is finished
     * False - otherwise
     */
    public Boolean isFinished() {
        return finished;
    }

    /**
     * Finishes this rental. 
     */
    public void finishRental() {
        if(this.finished)
            this.finished = true;
    }
    
    /**
     * Returns if the rental payment was made.
     * @return true - if rentals' payment list if grater than 0.
     */
    public boolean isPaymentDone(){
        return this.payments.size() > 0;
    }

    /**
     * Returns expected return date
     * @return expected return date
     */
    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }
    
    /***
     * Sets the Expected Return date if only the expected date is greater than rental date.
     * @param expectedReturnDate expected return date. 
     */
    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) {
        if(expectedReturnDate != null && expectedReturnDate.isAfter(this.rentalDate))
            this.expectedReturnDate = expectedReturnDate;
    }
    
    /**
     * Does the first payment to rent a movie. 
     * @param offerCode
     */
    public void doFirstPayment(String offerCode){
        
        BigDecimal subtotal = new BigDecimal("2.99").setScale(2);
        BigDecimal discount = applyDiscount(offerCode);
        BigDecimal total = subtotal.subtract(discount).setScale(2);
        Payment payment = new Payment(this, subtotal, discount, total, LocalDateTime.now());
        this.payments.add(payment);
    }

    /**
     * Applies some offer code if the code is available to user for the customer.
     * @param offerCode code to some offers
     * @return the discount value from offer code.
     */
    private BigDecimal applyDiscount(String offerCode){
        BigDecimal discount = null;
         if(customer.isFirstRental() && customer.isAvailableOfferCode(offerCode) && offerCode.equals("FREE123")){
            discount = new BigDecimal("2.99").setScale(2);
            this.setOfferCode(offerCode);
        } else {
            discount = new BigDecimal("0.00").setScale(2);
        }
         return discount;
    }
    
    /***
     * Saves the rental object and its payments
     * @throws SQLException 
     */
    @Override
    public void save() throws SaveModelException {
        
        //Checks if attributes is not null and has the correct values. 
        if(this.movie == null || this.movie.getId() == 0)
            throw new SaveModelException("Rental's movie can't be null or movie code can't be zero. Check your movie list");
        if(this.customer == null)
            throw new SaveModelException("Rental's customer can't be null. Check your card number"); 
        if(this.rentalDate == null ||  this.rentalDate.isAfter(expectedReturnDate))
            throw new SaveModelException("Rental's date can't be null or after the expected return date. Maybe your machine clock is not working correctly."); 
        if(this.expectedReturnDate == null)
            throw new SaveModelException("Rental's expected date can't be null. Creating rental had a problem."); 
        
        Timestamp rentald = Timestamp.valueOf(this.rentalDate); 
        Timestamp rentale = Timestamp.valueOf(this.expectedReturnDate);
        
        try {
             String insert = String.format("insert into rental (movie_id, card_number, "
                + "offer_code, rental_date, expected_date, finished) values"
                + " ('%d', '%s', '%s', '%s', '%s', '%d')", 
                this.movie.getId(), this.customer.getCardNumber(), this.getOfferCode(), rentald, rentale.toString(),
                0);
             System.out.println(insert);
             
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(insert);
            
            this.id = dbConnection.getLastId();
            for(Payment payment : payments){
                if(!payment.isDone()){
                    payment.save();
                }
            }
            movie.setAvailable(false);
            movie.update();
        } catch (SQLException ex) {
            throw new SaveModelException("Persistent problem happened while Customer were saving. Check the main causes:" +ex.getMessage()); 
        }
    }

    /**
     * Updates the rental object and its payments. 
     * @throws SQLException 
     */
    @Override
    public void update() throws UpdateModelException{
        
        //Checks if attributes is not null and has the correct values. 
         if(this.movie == null || this.movie.getId() == 0)
            throw new UpdateModelException("Rental's movie can't be null or movie code can't be zero. Check your movie list");
        if(this.customer == null)
            throw new UpdateModelException("Rental's customer can't be null. Check your card number"); 
        if(this.rentalDate == null ||  this.rentalDate.isAfter(expectedReturnDate))
            throw new UpdateModelException("Rental's date can't be null or after the expected return date. Maybe your machine clock is not working correctly."); 
        if(this.expectedReturnDate == null)
            throw new UpdateModelException("Rental's expected date can't be null. Creating rental had a problem."); 
        if(returnDate == null)
            throw new UpdateModelException("Rental's return date can't be null in update operation."); 
        
        int end = this.finished ? 1 : 0; 
        
        Timestamp rentald = Timestamp.valueOf(this.rentalDate); 
        Timestamp rentale = Timestamp.valueOf(this.expectedReturnDate);
        Timestamp rentalr = Timestamp.valueOf(this.returnDate);
        
        try {
            String updateSql;
            updateSql = String.format("update rental set movie_id = '%d', card_number = '%s', "
                + "offer_code = '%s', rental_date = '%s', expected_date = '%s', return_date = '%s', finished = '%d' where rental_id = '%d'", 
                this.movie.getId(), this.customer.getCardNumber(), this.offerCode, rentald, rentale,
                rentalr, end, this.id);
            
            DbConnection dbConnection;
            dbConnection = DbConnection.getDbConnection();
            int execute = dbConnection.execute(updateSql);
                    
        } catch (SQLException ex) {
            throw new UpdateModelException("Persistent problem happened while Rental were saving. Check the main causes:" +ex.getMessage()); 
        }
            
    }

    /***
     * Returns a Rental object if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Movie object with its rentals and associated payments. 
     */
    @Override
    public Rental get(String property, String value) throws QueryModelException
    {
        
        String query = String.format("select rental_id, movie_id, card_number, "
                + "offer_code, rental_date, expected_date, return_date, finished "
                + "from rental where %s = '%s' and finished = 0", property, value);
        
        Rental rental = null; 
        
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                
                int id = rs.getInt("rental_id"); 
                int movie_id = rs.getInt("movie_id");
                String card_number = rs.getString("card_number");
                
                String offerCode = rs.getString("offer_code");
                
                Timestamp r_date = rs.getTimestamp("rental_date");
                Timestamp e_date = rs.getTimestamp("expected_date");
                Timestamp re_date = rs.getTimestamp("return_date");     
                
                LocalDateTime rental_date = r_date.toLocalDateTime();
                LocalDateTime expected_date = e_date.toLocalDateTime();
                LocalDateTime return_date = (re_date != null) ? re_date.toLocalDateTime() : null;
                 
                int finished = rs.getInt("finished");
                
                Movie m = new Movie();
                m = m.get("movie_id", String.valueOf(movie_id));
                Payment p = new Payment(null, null);
                List<Payment> payments = p.list("rental_id", String.valueOf(id));
                
                Customer customer = new Customer(card_number, "");
                rental = new Rental(m, customer, offerCode, rental_date, expected_date);
                rental.setReturnDate(return_date);
                rental.id = id;
                rental.finished = (finished == 1);
                rental.payments = payments;
            }
            
        } catch (SQLException | CardNumberException | CVVException ex) {
            throw new QueryModelException("Select problem happened while we were getting a customer. Check the main causes:" +ex.getMessage()); 
        } 
        return rental;
    }

     /**
      *Returns a list of rentals if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Customer object with its rentals and associated payments. 
     */
    @Override
    public List<Rental> list(String property, String value) throws QueryModelException {
        
        String query = String.format("select rental_id, movie_id, card_number, "
                + "offer_code, rental_date, expected_date, return_date, finished from rental where %s = '%s'", property, value);
        
        Rental rental = null; 
        List<Rental> rentals = new ArrayList<>();
        
        
        try{
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
            while (rs!=  null && rs.next()) {

                int id = rs.getInt("rental_id");
                int movie_id = rs.getInt("movie_id");
                String card_number = rs.getString("card_number");

                String offerCode = rs.getString("offer_code");

                Timestamp r_date = rs.getTimestamp("rental_date"); 
                Timestamp e_date = rs.getTimestamp("expected_date");
                Timestamp re_date = rs.getTimestamp("return_date");

                LocalDateTime rental_date = r_date.toLocalDateTime();
                LocalDateTime expected_date = e_date.toLocalDateTime();


                int finished = rs.getInt("finished");

                Movie m = new Movie();
                m = m.get("movie_id", String.valueOf(movie_id));
                Payment p = new Payment(null, null);
                List<Payment> payments = p.list("rental_id", String.valueOf(id));


                Customer customer = new Customer();
                customer = customer.get("card_number", card_number);

                rental = new Rental(m, customer, offerCode, rental_date, expected_date);
                LocalDateTime return_date = null;
                if(re_date != null) {
                    return_date = re_date.toLocalDateTime();
                }
                rental.setReturnDate(return_date);
                rental.finished = (finished == 1);
                rental.payments = payments;
                rentals.add(rental);
            }
        }catch(SQLException ex){
            throw new QueryModelException("Select problem happened while we were getting a customer. Check the main causes:" +ex.getMessage()); 
        }
        
        return rentals;
    }

    /**
     * Returns the information about the returning movie action.This method finishes the rental and process any needed charges payments. 
     * @return information about the returning process.
     * @throws SQLException 
     * @throws exception.UpdateModelException 
     */
    public String[] finish() throws SQLException, UpdateModelException {
        
        LocalDateTime returnDate = LocalDateTime.now(); 
        long days = expectedReturnDate.until( returnDate, ChronoUnit.DAYS );
        String [] info = new String[3]; 
        
        if(days == 0 || days == -1){
            this.returnDate = returnDate;
            this.finished = true;
            this.movie.setAvailable(true);
            this.update();
            this.movie.update();
            info[0] = "You returned the movie in the correct time. ";
            info[1] = "You don't neet to pay extra charges";
            info[2] = "Thank you for chosse us.\nIf you want to rent another movie, just go to home page.";
            return info;
        } else if(days > 0 && days < 10){
            BigDecimal fixedValue = new BigDecimal("1.50");
            BigDecimal daysB = new BigDecimal(days);
            BigDecimal newValue = fixedValue.multiply(daysB);
            Payment payment = new Payment(this, newValue, BigDecimal.ZERO, newValue, returnDate);
            this.returnDate = returnDate;
            this.finished = true;
            this.movie.setAvailable(true);
            this.update();
            this.movie.update();
            info[0] = "Your late returning was " + days + "days";
            info[1] = "You paid some extra charge in total of "+newValue.setScale(2).toString();
            info[2] = "Thank you for chosse us. If you want to rent another movie do it in our home page.";
            return info;
        } else {
            BigDecimal max = new BigDecimal("15.00");
            Payment payment = new Payment(this, max, BigDecimal.ZERO, max, returnDate);
            this.returnDate = returnDate;
            this.finished = true;
            this.update();
            info[0] = "Your late returning was " + days + "days";
            info[1] = "You paid the maximum extra charge in total of " + max.setScale(2).toString();
            info[2] = "Now, the Movie disc is yours.\nThank you for chosse us.\nIf you want to rent another movie, just go to home page.";
            return info;
        }
    }
    
}
