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
 * @author Thiago
 */
public class XVisionApplication {
    
    public static void main(String [] args) {
        Controller controller = new Controller(); 
        Welcome welcome = new Welcome(controller);
        welcome.showFrame();
    }
}
