/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Carlos 
 * @param <T>
 */
public interface Model<T> {
    
    /**
     * Saves the entity object to a database, file or list using the attributes values. 
     */
    public void save() throws SQLException;
>>>>>>> de86cd0... Customer methods to return and checkout payment. 
    
    /**
     * 
     */
    public void update() throws SQLException;
    
    /**
     * @param property
     * @param value
     * @return */
<<<<<<< HEAD
    public T get(String property, String value);
<<<<<<< HEAD
}
=======
=======
    public T get(String property, String value) throws SQLException;
>>>>>>> de86cd0... Customer methods to return and checkout payment. 
    
    /**
     * @param property
     * @param value
     * @return */
    public List<T> list(String property, String value) throws SQLException; 
}
>>>>>>> c00e5e1... Update Model interface
