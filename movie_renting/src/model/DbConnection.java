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
    
    public Connection conn;
    
    public DbConnection() throws SQLException
    {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies","root","");
        System.out.println("Database connction successful!");
       
    }
    
    public ResultSet fetch(String query)
    {
        try{
            Statement stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
        public ResultSet fetch(String query, String email)
    {
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public void exec(String query)
    {
        try{
            Statement stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
