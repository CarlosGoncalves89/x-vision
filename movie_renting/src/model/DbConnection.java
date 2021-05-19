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


/**
 * DbConnection represents a database class operation since create a JDBC connection to execute query and DML 
 * operations as insert, update. 
 * @author Thiago 
 */
public class DbConnection {
    
    private static final String DB_NAME = "xvision"; 
    private static final String USER = "root"; 
    private static final String PWD = "";
    
    private static DbConnection dbConnection;
    private Connection connection;
    private int lastId;
    
    /**
     * Creates only one Connection object using the Singleton pattern. 
     * @throws SQLException if the database not exists, connection privilages is not permitted 
     * user and password is not correct. 
     */
    private DbConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/" + DB_NAME;
       this.connection = DriverManager.getConnection(url, USER, PWD);
    }
    
    /***
     * Implements the singleton methods;
     * @return DbConnection object
     * @throws SQLException SQLException if the database not exists, connection privilages is not permitted 
     * user and password is not correct.  
     */
    public static DbConnection getDbConnection() throws SQLException{
        if(dbConnection == null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }
    
    /**
     * Returns a ResultSet object from a specific query 
     * @param sql - select query
     * @return a ResultSet object as the result of the query
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
     *  Executes the DML operations from a statement. 
     * @param sql - insert, update and delete operation
     * @return  the number of lines affected
     */
    public int execute(String sql) throws SQLException{
       
        PreparedStatement pstatement;
        
        pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        int result = pstatement.executeUpdate();
        ResultSet generatedKeys = pstatement.getGeneratedKeys();
        if(result > 0)
            if(generatedKeys.next())
                this.lastId = (int) generatedKeys.getLong(1);
        return result;
    }
    
    /***
     * Returns the last id inserted in database using this connection. 
     * @return last id inserted
     */
    public int getLastId(){
       return this.lastId;
    }
    
    /***
     * Begins a transaction to a block of operations. 
     * @throws SQLException 
     */
    public void beginTransaction() throws SQLException{
        this.connection.setAutoCommit(false);
    }
    
    /**
     * Effetivates the transaction operations. 
     * @throws SQLException 
     */
    public void commit() throws SQLException{
        this.connection.commit();
    }
    
    /***
     * Rolls back to the start point if the operation in a transaction failed. 
     * @throws SQLException 
     */
    public void rollback() throws SQLException{
        this.connection.rollback();
    }
   
}
