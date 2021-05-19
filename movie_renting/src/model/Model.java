package model;

import exception.QueryModelException;
import exception.SaveModelException;
import exception.UpdateModelException;
import java.util.List;

/**
 * Models represents any type (or table) thats its objects will be stored or manipulated in a database. 
 * @carlos 
 * @param <T> - T represents the Type name. 
 */
public interface Model<T> {
    
    /**
     * Saves the entity object to a database, file or list using the attributes values. 
     * @throws exception.SaveModelException
     */
    public void save() throws SaveModelException;
    
    /**
     * Updates the entity object and store it in a database, file or list using the object state.  
     * @throws exception.UpdateModelException
     */
    public void update() throws UpdateModelException;
    
    /**
     * Retuns an object of Type T if the condition property = value is true.
     * @param property - any field of the object
     * @param value - value to a specific field.
     * @return a T object
     * @throws exception.QueryModelException
     */
    public T get(String property, String value) throws QueryModelException;
    
    /**
     * Returns a list of objects of Type T if the condition property = value is true.
     * @param property - any field of the object
     * @param value - value to a specific field.
     * @return a list of T objects
     * @throws exception.QueryModelException
     */
    public List<T> list(String property, String value) throws QueryModelException; 
}
