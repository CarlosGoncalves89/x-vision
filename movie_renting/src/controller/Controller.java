/*
 * Controller handles the views requests. 
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.DbConnection;

/**
 *
 * @author user
 */
public class Controller {
    
    private DbConnection connection;
    
    public Controller() throws SQLException{
        this.connection = new DbConnection();
    }
    
    public ResultSet query(){
        ResultSet rs = this.connection.fetch("Select id,name,description,thumbnail from movies where taken = 0");
        return rs;
    }
    
}
