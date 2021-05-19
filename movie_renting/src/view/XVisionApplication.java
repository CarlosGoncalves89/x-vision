package view;

import controller.Controller;

/**
 * XVisionApplication is the application main class (the point start).
 * @author thiago
 */
public class XVisionApplication {
    
    public static void main(String [] args) {
        //Creates the unique controller object. 
        Controller controller = new Controller(); 
        Welcome welcome = new Welcome(controller);
        welcome.showFrame();
    }
}
