/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thiag
 */
public class DbConnection {

    private static final String DB_NAME = "movies"; 
    private static final String USER = "root"; 
    private static final String PWD = "";
    
    private static DbConnection dbConnection;
    private Connection connection;
    
    private DbConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/" + DB_NAME;
       this.connection = DriverManager.getConnection(url, USER, PWD);
    }
    
    public static DbConnection getConnection() throws SQLException{
        if(dbConnection != null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }
}
