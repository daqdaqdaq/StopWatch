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


    @Override
    public String toString(Number number) {
        double n = (Math.round((double)number)/100)/10;
        return super.toString(n); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Number fromString(String string) {
        return (Double)super.fromString(string)*1000; //To change body of generated methods, choose Tools | Templates.
    }
    
}
