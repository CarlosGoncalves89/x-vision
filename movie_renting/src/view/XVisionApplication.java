package view;

import controller.Controller;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thiago
 */
public class XVisionApplication {
    
    public static void main(String [] args) {
        Controller controller = new Controller(); 
        Welcome welcome = new Welcome(controller);
        welcome.showFrame();
    }
}
