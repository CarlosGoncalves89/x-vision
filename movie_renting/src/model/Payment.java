package model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Payment is a representation of any charges or receipts values. 
 * @thiago 
 */
public class Payment implements Model<Payment> {
    
    private Rental rental;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
    private LocalDateTime paymentDate; 
    private boolean done; 
    
    /**
     * Constructs a simple payment with associated rental and total receipt. 
     * @param rental the associated rental
     * @param total rental's total value
     */
    public Payment(Rental rental, BigDecimal total){
        this.rental = rental; 
        this.total = total;
    }

    /***
     * Constructs a complete payment that includes the associate rental, subtotal, discount, total and payment date and time. 
     * @param rental - associated rental
     * @param subtotal - total value without discounts
     * @param discount - discounts received from offer codes
     * @param total - total = subtotal - discount
     * @param paymentDate - the current date time value. 
     */
    public Payment(Rental rental, BigDecimal subtotal, BigDecimal discount, BigDecimal total, LocalDateTime paymentDate) {
        this.rental = rental;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.paymentDate = paymentDate;
    }

    /**
     * Returns the payment source: rental. 
     * @return rental - payment's source
     */
    public Rental getRental() {
        return rental;
    }

    /**
     * Sets the payment source: rental.
     * @param rental - payment's source
     */
    public void setRental(Rental rental) {
        this.rental = rental;
    }

    /**
     * Returns the payment total
     * @return total = subtotal - discount
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Sets the payment total
     * @param total 
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Returns if the payment is done (or registered).
     * @return True if the payment was registered or False otherwise
     */
    public boolean isDone(){
        return this.done; 
    }
    
    /**
     * Saves a rental payment. 
     */
    @Override
    public void save() {
        
        String subtotal = this.subtotal.setScale(2).toString().replace(",", ".");
        String discount = this.discount.setScale(2).toString().replace(",", ".");
        String total = this.total.setScale(2).toString().replace(",", ".");
        Timestamp pdate = Timestamp.valueOf(this.paymentDate); 
        
         String insert = String.format("insert into payment (rental_id, subtotal, "
                + "discount, total, payment_date) values ('%d', '%s', '%s', '%s', '%s')", 
                this.rental.getId(), subtotal, discount, total, pdate);
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(insert);
            this.done = true;
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Don't do anything.
     */
    @Override
    public void update() {
     
    }

    /**
     * Don't do anything
     * @param property
     * @param value
     * @return 
     */
    @Override
    public Payment get(String property, String value) {
        return null;
    }

    /**
     * Returns a list of payments. 
     * @param property - payment table column
     * @param value - value to find
     * @return a list of payment where property = value
     */
    @Override
    public List<Payment> list(String property, String value) {
        String query = String.format("select rental_id, subtotal, discount, total,"
                + " payment_date from payment where %s = '%s'", property, value);
        List<Payment> payments = new ArrayList<>();
        Payment payment = null; 
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String mId = String.valueOf( rs.getInt("rental_id"));        
                BigDecimal subtotal = rs.getBigDecimal("subtotal");
                BigDecimal discount = rs.getBigDecimal("discount");
                BigDecimal total = rs.getBigDecimal("total");
                Timestamp pdate = rs.getTimestamp("payment_date");
                LocalDateTime payment_date = pdate.toLocalDateTime();
                payment = new Payment(rental, subtotal, discount, total, payment_date);
                payment.done = true;
                payments.add(payment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return payments;
    }
    
}
