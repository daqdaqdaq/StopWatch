/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.fx;

import javafx.beans.value.ObservableValue;

/**
 *
 * @author DAQ
 */
public class TimeDisplayTsecHiding extends TimeDisplay {

    public TimeDisplayTsecHiding(Boolean h, Boolean m, Boolean s, Boolean ts) {
        super(h, m, s, ts);
        
        this.tsec.managedProperty().bind(this.tsec.visibleProperty());
        this.thirdcolon.visibleProperty().bind(this.tsec.visibleProperty());
        this.thirdcolon.managedProperty().bind(this.tsec.visibleProperty());        
        this.sec.textProperty().addListener((ObservableValue<? extends String> observable, String ov, String nv)->{
        
            if (Integer.parseInt(nv)<10&&!this.tsec.isVisible()){
                this.tsec.setVisible(true);
            }
            if (Integer.parseInt(nv)>=10&&this.tsec.isVisible()){
                this.tsec.setVisible(false);
            }            
        });
    }
    
}
