/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
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

    Rental() {
        
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

    public Customer getCostumer() {
        return customer;
    }

    public void setCostumer(Customer costumer) {
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
    public void save() {
        String insert = String.format("insert into rental (movie_id, card_number, "
                + "offer_code, rental_date, expected_date, return_date, finished) values"
                + " ('%s', '%s', '%s', '%d')", 
                this.movie.getId(), this.customer.getCardNumber(), this.getOfferCode(), this.getRentalDate().toString(), this.getExpectedReturnDate().toString(),
                this.getReturnDate().toString(), 0);
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.commit(insert);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
         String updateSql = String.format("update movie set title = '%s', description = '%s', "
                + "thumbnail = '%s', available = '%d' where movie_id = '%d'", 
                this);
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.commit(updateSql);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Rental get(String property, String value) {
        String query = String.format("select movie_id, title, description, thumbnail,"
                + " available from movie where %s = '%s'", property, value);
        Movie movie = null; 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String mId = String.valueOf( rs.getInt("movie_id"));
                String mTitle = rs.getString("title");
                String mDescription = rs.getString("description");
                String mThumbnail = rs.getString("thumbnail");
                Integer mAvailable = rs.getInt("available");
                movie = new Movie(Integer.valueOf(mId), mTitle, mDescription, mThumbnail); 
                movie.setAvailable(mAvailable == 1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public List<Rental> list(String property, String value) {
         String query = String.format("select movie_id, title, description, thumbnail,"
                + " available from movie where %s = '%s'", property, value);
        List<Movie> movies = new ArrayList<>(); 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String mId = String.valueOf( rs.getInt("movie_id"));
                String mTitle = rs.getString("title");
                String mDescription = rs.getString("description");
                String mThumbnail = rs.getString("thumbnail");
                Integer mAvailable = rs.getInt("available");
                Movie movie = new Movie(Integer.valueOf(mId), mTitle, mDescription, mThumbnail); 
                movie.setAvailable(mAvailable == 1);
                movies.add(movie);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
