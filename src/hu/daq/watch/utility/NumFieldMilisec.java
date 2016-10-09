/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.utility;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author DAQ
 */
public class NumFieldMilisec extends TextField{

    public NumFieldMilisec() {
        this.setTextFormatter(new TextFormatter<>(new MilisecConverter()));
    }

    public NumFieldMilisec(String text) {
        super(text);
        
        this.setTextFormatter(new TextFormatter<>(new MilisecConverter()));
    }
    
}
