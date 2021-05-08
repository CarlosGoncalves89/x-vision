/*
 * Controller handles the views requests. 
 */
package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Costumer;
import model.DbConnection;
import model.Movie;

/**
 *
 * @author user
 */
public class Controller {
 
    private final DbConnection dbConnection;
    private final List<Movie> movies; 
    
    public Controller() throws SQLException{
        this.movies = new ArrayList<>();
        this.dbConnection = DbConnection.getConnection();
    }
    
    public List<Movie> listMovies(){
        return this.movies;
    }
    
    public boolean rentMovie(Movie movie, Costumer costumer, String offerCode){
        return false; 
    }
    
    public boolean checkoutMovie(Movie movie){
        return false;
    }
    
    public void createPayment(){
        
    }
}
