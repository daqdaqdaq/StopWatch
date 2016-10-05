/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author DAQ
 */
public class SimplePositiveIntegerProperty extends SimpleIntegerProperty{

    public SimplePositiveIntegerProperty() {
    }

    public SimplePositiveIntegerProperty(int initialValue) {
        super(initialValue);
    }

    public SimplePositiveIntegerProperty(Object bean, String name) {
        super(bean, name);
    }

    public SimplePositiveIntegerProperty(Object bean, String name, int initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public void set(int newValue) {
        if (newValue<0){
            super.set(0);
        } else{
            super.set(newValue); 
        }
    }
    
    
}
