/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.fx;

import hu.daq.watch.BaseWatch;
import hu.daq.watch.utility.ValidatingTextField;
import java.util.Arrays;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.controlsfx.control.PopOver;

/**
 *
 * @author DAQ
 */
public class TimeSetPopOver extends PopOver {

    TextField timefield;
    TimeDisplay td;

    public TimeSetPopOver(TimeDisplay td) {
        super();
        this.td = td;
        this.setDetachable(false);
        this.setDetached(false);
        this.setArrowLocation(ArrowLocation.TOP_CENTER);
        this.timefield = new TextField();
        this.timefield.setMinWidth(150);
        this.timefield.setMinWidth(150);        
        this.timefield.setFont(new Font(24));
        this.timefield.setPrefWidth(30);
        this.setContentNode(this.timefield);

        this.timefield.setOnAction((ev) -> {
            this.setTime();
        });


    }
    
    
  /*  private void setValidationPattern(TimeDisplay td){
        BaseWatch bw = td.getSw();
        if (bw.hasMin()){
            //this.timefield.setValidationPattern("[0-5]?[0-9]{1}:[0-5]{1}[0-9]{1}(\\.[1-9])?");
            this.timefield.setValidationPattern("00:00.0");
            this.timefield.setText("00:00.0");
        } else{
            this.timefield.setValidationPattern("00.0");
            this.timefield.setText("00.0");            
        }
    }*/
    
    private void setTime() {
        if (this.timefield.getText() != null && this.timefield.getText().length() > 0) {
            String min;
            String sec;
            String tsec;
            String[] workstr = this.timefield.getText().split(":");
            if (workstr.length == 2){
                min = workstr[0];
                String[] secpart = workstr[1].split(".");
                System.out.println("secpart:"+Arrays.asList(secpart));
                if (secpart.length == 2){
                    sec = secpart[0];
                    tsec = secpart[1]; 
                } else {
                    sec = workstr[1];
                    tsec = "0";
                }
            } else {
                min = "0";
                String[] secpart = this.timefield.getText().split("\\.");
                System.out.println("secpart:"+Arrays.asList(secpart));
                if (secpart.length == 2){
                    sec = secpart[0];
                    tsec = secpart[1]; 
                } else {
                    sec = this.timefield.getText();
                    tsec = "0";
                }            
            }
            int milisecs = Integer.parseInt(min)*60000+Integer.parseInt(sec)*1000+Integer.parseInt(tsec)*100;
            this.td.setTime(milisecs);
            //sends the time via the remote transmitter if it's present
            this.td.transmittTime(milisecs);
        }
        this.hide();

    }

}
