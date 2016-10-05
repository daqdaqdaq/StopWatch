/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Transport class for stopwatch
 * Holds reference to 
 * @author DAQ
 */
public class Time {
    private Integer milistocount;
    public SimpleIntegerProperty hour;
    public SimpleIntegerProperty min;
    public SimpleIntegerProperty sec;
    public SimpleIntegerProperty tsec;    
    
    public Time(SimpleIntegerProperty hour,SimpleIntegerProperty min,SimpleIntegerProperty sec,SimpleIntegerProperty tsec){
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.tsec = tsec;
    }

    public void setMilisToCount(Integer milis){
        this.milistocount = milis;
    }

    public Integer getMilistocount() {
        return milistocount;
    }
    
    private void bindProperty(SimpleIntegerProperty sourceprop, StringProperty targetprop, Integer padding){
        targetprop.bind(sourceprop.asString("%0"+padding.toString()+"d"));
    }
            
    public void bindHour(StringProperty tobind, Integer padding){
        this.bindProperty(this.hour, tobind, padding);
    }
    
    public void bindMin(StringProperty tobind, Integer padding){
        this.bindProperty(this.min, tobind, padding);
    }
    public void bindSec(StringProperty tobind, Integer padding){
        this.bindProperty(this.sec, tobind, padding);
    }
    public void bindTsec(StringProperty tobind, Integer padding){
        this.bindProperty(this.tsec, tobind, padding);
    }   
    
    @Override
    public void finalize(){
        this.hour.unbind();
        this.min.unbind();
        this.sec.unbind();
        this.tsec.unbind();            
    }
}
