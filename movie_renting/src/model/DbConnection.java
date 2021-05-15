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
 *
 * @thiago 
 */
public class DbConnection {
    
    private static final String DB_NAME = "xvision"; 
    private static final String USER = "root"; 
    private static final String PWD = "";
    
    private static DbConnection dbConnection;
    private Connection connection;
    private int lastId;
    
    /**
     * 
     * @throws SQLException 
     */
    private DbConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/" + DB_NAME;
       this.connection = DriverManager.getConnection(url, USER, PWD);
    }
    
    /***
     * 
     * @return
     * @throws SQLException 
     */
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
     * 
     * @return 
     */
    public int getLastId(){
       return this.lastId;
    }
    
    /***
     * 
     * @throws SQLException 
     */
    public void beginTransaction() throws SQLException{
        this.connection.setAutoCommit(false);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    public void commit() throws SQLException{
        this.connection.commit();
    }
    
    /***
     * 
     * @throws SQLException 
     */
    public void rollback() throws SQLException{
        this.connection.rollback();
    }
   
}
