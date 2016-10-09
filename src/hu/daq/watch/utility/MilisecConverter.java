/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.utility;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author DAQ
 */
public class MilisecConverter extends NumberStringConverter{

    public MilisecConverter() {
    }

    public MilisecConverter(Locale locale) {
        super(locale);
    }

    public MilisecConverter(String string) {
        super(string);
    }

    public MilisecConverter(Locale locale, String string) {
        super(locale, string);
    }

    public MilisecConverter(NumberFormat nf) {
        super(nf);
    }


    public String toString(Double number) {
        if (number == null){
            return "";
        }
        
        Double n = ((double)Math.round((float)(number/100)))/10;
        return n.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double fromString(String string) {
        if (string == null){
            return null;
        }
        
        string = string.trim();
        if (string.length() < 1){
            return null;
        }
        
        return (Double.parseDouble(string))*1000; //To change body of generated methods, choose Tools | Templates.
    }
    
}
