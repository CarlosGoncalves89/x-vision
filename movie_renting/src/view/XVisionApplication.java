/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thiag
 */
public class XVisionApplication {
    
    public static void main(String [] args) throws SQLException{
            Controller c = new Controller(); 
            ResultSet rs = c.query();
            while (rs.next()) {
                String id = String.valueOf( rs.getInt("id"));
                String title = rs.getString("name");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                System.out.printf("%s %s %s %s", id, title, description, thumbnail); 
            }
    }
}
