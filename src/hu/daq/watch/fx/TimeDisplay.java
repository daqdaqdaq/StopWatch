/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.fx;

import hu.daq.watch.BaseWatch;
import hu.daq.watch.Time;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author DAQ
 */
public class TimeDisplay extends HBox implements Settable {

    Label hour;
    Label min;
    Label sec;
    Label tsec;
    Label firstcolon;
    Label secondcolon;
    Label thirdcolon;
    Boolean needhour;
    Boolean needmin;
    Boolean needsec;
    Boolean needtsec;
    BaseWatch sw;
    TimeSetPopOver tsop;
    RemoteTransmitter transmitter; 

    public TimeDisplay(Boolean h, Boolean m, Boolean s, Boolean ts) {
        
        this.hour = new Label("");
        this.min = new Label("");
        //this.min.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));        
        this.sec = new Label("");
        this.tsec = new Label("");
        this.firstcolon = new Label(":");
        this.secondcolon = new Label(":");
        this.thirdcolon = new Label(".");        
        this.needhour = h;
        this.needmin = m;
        this.needsec = s;
        this.needtsec = ts;
        this.build();
    }

    private void build() {
        this.hour.setWrapText(false);
        
        this.min.setWrapText(false);
        this.sec.setWrapText(false);
        this.tsec.setWrapText(false);
        this.firstcolon.setWrapText(false);
        this.secondcolon.setWrapText(false);
        this.thirdcolon.setWrapText(false);       
        
        


        if (this.needhour) {
            this.getChildren().add(this.hour);

            
        }

        if (this.needmin){
            
           if (this.needhour ){
             //  this.getChildren().add(this.firstcolon);
               

           }
           this.getChildren().add(this.min);
           //this.getChildren().add(new Label("00"));

        }
       
        if (this.needsec){
           if (this.needmin){
               this.getChildren().add(this.secondcolon);

           }
           this.getChildren().add(this.sec);

        }

        if (this.needtsec){
           if (this.needsec){
               this.getChildren().add(this.thirdcolon);
 
           }
           this.getChildren().add(this.tsec);

        } 
        //initiate a setfont with the deafault font to resize the tsec label
        this.setFont(this.hour.getFont());
        this.setAlignment(Pos.CENTER);
        //this.
        //this.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));        
    }
   
    public void attachTransmitter(RemoteTransmitter transmitter){
        this.transmitter = transmitter;
    }
    
    public void transmittTime(int milisec){
        if (this.transmitter!=null){
            this.transmitter.transmit(milisec);
        }
    }
    
    public void enableTimeSetPopOver(){
        this.tsop = new TimeSetPopOver(this);
        this.setOnMouseClicked((ev) -> {
            this.tsop.show(this);
        });        
    }
    
    public void setFont(Font font){
        this.hour.setFont(font);
        this.min.setFont(font);
        this.sec.setFont(font);
        //this.tsec.setFont(font);        
        this.tsec.setFont(font);
        this.firstcolon.setFont(font);
        this.secondcolon.setFont(font);
        //this.thirdcolon.setFont(font);
        this.thirdcolon.setFont(font);
        
    }
    
    public void setColor(Color color){
        this.firstcolon.setTextFill(color);
        this.secondcolon.setTextFill(color);
        this.thirdcolon.setTextFill(color);
        this.hour.setTextFill(color);
        this.min.setTextFill(color);
        this.sec.setTextFill(color);        
        this.tsec.setTextFill(color);                
    }
    
    public void setFontSize(double size){
       
        Font f = this.hour.getFont();
        this.setFont(Font.font(f.getFamily(),size));
    }

    public void setFontFamily(String family){
        Font f = this.hour.getFont();
        this.setFont(Font.font(family,f.getSize()));
    }    
    
    public void attachWatch(BaseWatch sw){
        this.sw = sw;
        Time t = sw.getObservableTime();
        t.bindHour(this.hour.textProperty(), 1);        
        if (this.needhour){ //if we have a hour digit pad the mins to 2
            t.bindMin(this.min.textProperty(), 2);
        } else {
            t.bindMin(this.min.textProperty(), 1);            
        }
        if (this.needmin){
            t.bindSec(this.sec.textProperty(), 2);
        } else {
            t.bindSec(this.sec.textProperty(), 1);        
        }
        t.bindTsec(this.tsec.textProperty(), 1);
    }

    @Override
    public void setTime(int milisecs) {
        this.sw.set(milisecs);
    }

    public BaseWatch getSw() {
        return sw;
    }
    

}
