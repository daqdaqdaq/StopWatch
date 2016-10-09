/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.fx;

import hu.daq.watch.BaseWatch;
import hu.daq.watch.Time;
import hu.daq.watch.utility.MilisecConverter;
import hu.daq.watch.utility.NumFieldMilisec;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author DAQ
 */
public class EditableTimeDisplay extends NumFieldMilisec{

    Boolean needhour;
    Boolean needmin;
    Boolean needsec;
    Boolean needtsec;

    public EditableTimeDisplay(Boolean h, Boolean m, Boolean s, Boolean ts) {
        
       
        this.needhour = h;
        this.needmin = m;
        this.needsec = s;
        this.needtsec = ts;
    }

    public void setFontSize(double size){
       
        Font f = this.getFont();
        this.setFont(Font.font(f.getFamily(),size));
    }

    public void setFontFamily(String family){
        Font f = this.getFont();
        this.setFont(Font.font(family,f.getSize()));
    }    
    
    public void attachWatch(BaseWatch sw){
        this.textProperty().bindBidirectional(sw.getComputedmilis(), new MilisecConverter());
        this.editableProperty().bind(sw.getTimeEngineRunning().not());
    }
    
}
