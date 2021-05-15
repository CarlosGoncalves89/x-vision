/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 * @author Carlos 
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

    public Rental(Movie movie, Customer costumer, String offerCode, LocalDateTime rentalDate, LocalDateTime expectedReturnDate) {
        this.movie = movie;
        this.customer = costumer;
        this.offerCode = offerCode;
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.finished = false; 
        this.payments = new ArrayList<>();
    }    

    public Rental() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setCustomer(Customer costumer) {
        this.customer = costumer;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        offerCode = offerCode.trim();
        if(offerCode.length() > 0 && offerCode.length() <= 10)
            this.offerCode = offerCode;
    }
    
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
        }
    }
    
    public LocalDateTime getReturnDate(){
        return this.returnDate;
    }
    
    public long diffDays(){
        return 0;
    }

    /**
     *
     * @return
     */
    public Boolean isFinished() {
        return finished;
    }

    public void finishRental() {
        if(this.finished)
            this.finished = true;
    }
    
    public boolean isPaymentDone(){
        return this.payments.size() > 0;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) {
        if(expectedReturnDate != null && expectedReturnDate.isAfter(this.rentalDate))
            this.expectedReturnDate = expectedReturnDate;
    }
    
    public void doFirstPayment(String offerCode){
        
        BigDecimal subtotal = new BigDecimal("2.99").setScale(2);
        BigDecimal discount = applyDiscount(offerCode);
        BigDecimal total = subtotal.subtract(discount).setScale(2);
        Payment payment = new Payment(this, subtotal, discount, total, LocalDateTime.now());
        this.payments.add(payment);
    }

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
    
    @Override
    public void save() throws SQLException {
        
        Timestamp rentald = Timestamp.valueOf(this.rentalDate); 
        Timestamp rentale = Timestamp.valueOf(this.expectedReturnDate);
                
        String insert = String.format("insert into rental (movie_id, card_number, "
                + "offer_code, rental_date, expected_date, finished) values"
                + " ('%d', '%s', '%s', '%s', '%s', '%d')", 
                this.movie.getId(), this.customer.getCardNumber(), this.getOfferCode(), rentald, rentale.toString(),
                0);
        
        System.out.println(insert);
        
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(insert);
            
            this.id = dbConnection.getLastId();
            System.out.println(this.id);
            
            for(Payment payment : payments){
                if(!payment.isDone()){
                    payment.save();
                    System.out.println("Feito");
                }
            }
            movie.setAvailable(false);
            movie.update();
    }

    @Override
    public void update() throws SQLException{
        
        int end = -1;
        if(this.finished)
            end = 1;
        else
            end = 0;
                  
        
        this.movie.getId();
        this.customer.getCardNumber();
        Timestamp rentald = Timestamp.valueOf(this.rentalDate); 
        Timestamp rentale = Timestamp.valueOf(this.expectedReturnDate);
        Timestamp rentalr = this.returnDate != null ? Timestamp.valueOf(this.returnDate) : null;
        
        String updateSql;
        updateSql = String.format("update rental set movie_id = '%d', card_number = '%s', "
                + "offer_code = '%s', rental_date = '%s', expected_date = '%s', return_date = '%s', finished = '%d' where rental_id = '%d'", 
                this.movie.getId(), this.customer.getCardNumber(), this.offerCode, rentald, rentale,
                rentalr, end, this.id);
                System.out.println(updateSql);
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(updateSql);
    }

    @Override
    public Rental get(String property, String value) {
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
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rental;
    }

    @Override
    public List<Rental> list(String property, String value) {
         String query = String.format("select rental_id, movie_id, card_number, "
                + "offer_code, rental_date, expected_date, return_date, finished from rental where %s = '%s'", property, value);
        Rental rental = null; 
        List<Rental> rentals = new ArrayList<>();
        
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
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rentals;
    }

    public String[] finish() throws SQLException {
        
        LocalDateTime returnDate = LocalDateTime.now(); 
        long days = expectedReturnDate.until( returnDate, ChronoUnit.DAYS );
        String [] info = new String[3]; 
        
        if(days == 0){
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
