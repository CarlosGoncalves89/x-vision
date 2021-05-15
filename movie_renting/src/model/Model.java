package model;

import java.sql.SQLException;
import java.util.List;

/**
 * Models represents any type (or table) thats its objects will be stored or manipulated in a database. 
    @author Carlos 
 * @param <T> T represents the Type name. 
 */
public interface Model<T> {
    
    /**
     * Saves the entity object to a database, file or list using the attributes values. 
     */
    public void save() throws SQLException;
    
    /**
     * Updates the entity object and store it in a database, file or list using the object state.  
     */
    public void update() throws SQLException;
    
    /*
     * Retuns an object of Type T if the condition property = value is true.
     * @param property - any field of the object
     * @param value - value to a specific field.
     * @return a T object
     */

    public T get(String property, String value) throws SQLException;
    
    /**
     * Returns a list of objects of Type T if the condition property = value is true.
     * @param property - any field of the object
     * @param value - value to a specific field.
     * @return a list of T objects
     */
    public List<T> list(String property, String value) throws SQLException; 
}
