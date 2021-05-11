/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Carlos
 * @param <T>
 */

public interface Model <T> {

    public void save();
    
    /***/
    public void update();
    
    /**
     * @param property
     * @param value
     * @return */
    public T get(String property, String value);
<<<<<<< HEAD
}
=======
    
    /**
     * @param property
     * @param value
     * @return */
    public List<T> list(String property, String value);
}
>>>>>>> c00e5e1... Update Model interface
