/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @thiag 
 */
public class Customer implements Model<Customer>{
    
    private String cardNumber; 
    private String cvv;
    private String email;
    private int firstRental; 
    private List<Rental> rentals; 
    
    public Customer(){
        
    }
    
    public Customer(String card, String cvv){
        setCardNumber(card);
        setCVV(cvv);
        this.rentals = new ArrayList<>();
        this.firstRental = 1;
        this.email = "";
    }

    public Customer(String cardNumber, String cvv, String email, int firstRental) {
        this(cardNumber, cvv);
        setEmail(email);
    }

    public Customer(String cardNumber, String cvv, int firstRental) {
        this(cardNumber, cvv);
        this.firstRental = firstRental;
    }
    
    
    public void setCardNumber(String cardNumber){       
        cardNumber = cardNumber.trim();
        if(cardNumber.length() >= 13 && cardNumber.length() <= 16)
            this.cardNumber = cardNumber;
    }
    
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    public void setCVV(String cvv){
        cvv = cvv.trim();
        if(cvv.length() == 3)
            this.cvv = cvv;
    }
    
    public void setEmail(String email){
        email = email.trim();
        if(email.contains("@") && email.length() <= 40)        
            this.email = email;
    }
    
    public String getEmail(){
        return this.email.length() == 0 ? "None" : this.email; 
    }
    
    public boolean isFirstRental(){
        return this.firstRental == 1;
    }
    
    public void updateFirstRental(){
        if(this.firstRental == 1)
            this.firstRental = 0;
    }
    
    public void rent(Movie movie, LocalDateTime rentalDate, LocalDateTime expectedReturnDate, String offerCode){
        Rental rental = new Rental(movie, this, offerCode, rentalDate, expectedReturnDate); 
        rental.doFirstPayment(offerCode);
        movie.setAvailable(false);
        rentals.add(rental);
        this.updateFirstRental();
    }
    
    public boolean isAvailableOfferCode(String offerCode){
        for(Rental rental: rentals){
            if(rental.equals(offerCode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void save() {
          String insert = String.format("insert into customer (card_number, cvv, "
                + "email, first_rental) values ('%s', '%s', '%s', '%d')", 
                this.cardNumber, this.cvv, this.email, this.firstRental);
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.commit(insert);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
         String updateSql = String.format("update customer set cvv = '%s', email = '%s', "
                + "first_rental = '%d' where card_number = '%s'", 
                this.cvv, this.email, this.firstRental, this.cardNumber);
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.commit(updateSql);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Customer get(String property, String value) {
        
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
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customer;
    }

    @Override
    public List<Customer> list(String property, String value) {
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
                customers.add(customer);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.cardNumber);
        hash = 97 * hash + Objects.hashCode(this.cvv);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + this.firstRental;
        return hash;
    }

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

    @Override
    public String toString() {
        return "Customer{" + "cardNumber=" + getCardNumber() + ", cvv=" + cvv + ","
                + " email=" + getEmail() + ", firstRental=" + isFirstRental() + '}';
    }
    
}
