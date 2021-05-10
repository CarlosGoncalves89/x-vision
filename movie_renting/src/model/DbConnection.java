/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @thiag 
 */
public class DbConnection {
    
    private static final String DB_NAME = "xvision"; 
    private static final String USER = "root"; 
    private static final String PWD = "";
    
    private static DbConnection dbConnection;
    private Connection connection;
    private int lastId;
    
    private DbConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/" + DB_NAME;
       this.connection = DriverManager.getConnection(url, USER, PWD);
    }
    
    public static DbConnection getDbConnection() throws SQLException{
        if(dbConnection == null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }
    
    /**
     *
     * @param sql
     * @return
     */
    public ResultSet query(String sql){
        Statement stmt; 
        ResultSet rs = null;
        try{
            stmt = (Statement) connection.createStatement();
            rs = stmt.executeQuery(sql);
        }catch(SQLException e){
           return rs;
        }
        return rs;
    } 
    
    /***
     * 
     * @param sql 
     * @return  
     */
    public int commit(String sql){
       
        PreparedStatement pstatement;
        try{
            pstatement = connection.prepareStatement(sql);
            int result = pstatement.executeUpdate();
            ResultSet generatedKeys = pstatement.getGeneratedKeys();
            if(result > 0)
                if(generatedKeys.next())
                    this.lastId = (int) generatedKeys.getLong(1);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
        
    public int getLastId(){
       return this.lastId;
    }
}
