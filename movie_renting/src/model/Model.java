/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author user
 */
public interface Model <T> {

    public void save();
    public void update();
    public T get(String property, String value);
}