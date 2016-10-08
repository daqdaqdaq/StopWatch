/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.utility;


import hu.daq.watch.BaseWatch;
import hu.daq.watch.fx.RingDisplay;
import hu.daq.watch.fx.TimeDisplay;
import hu.daq.watch.fx.TimeDisplayTsecHiding;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 *
 * @author DAQ
 */
public class WatchFactory {

    public static Button getStartButton(BaseWatch sw, String buttonlabel){
        Button b = new Button(buttonlabel);
        b.setOnAction((E) -> sw.start());
        return b;
    }

    public static Button getPauseButton(BaseWatch sw, String buttonlabel){
        Button b = new Button(buttonlabel);
        b.setOnAction((E) -> sw.pause());
        return b;
    }    

    public static Button getResetButton(BaseWatch sw, String buttonlabel){
        Button b = new Button(buttonlabel);
        b.setOnAction((E) -> sw.reset());
        return b;
    }    
    

    public static TimeDisplay getWatchDisplay(BaseWatch sw){
       TimeDisplay t = new TimeDisplay(sw.hasHour(),sw.hasMin(),true,true);
       t.attachWatch(sw);
       return t;
    }
    
    public static TimeDisplay getSimpleWatchDisplay(BaseWatch sw){
       TimeDisplay t = new TimeDisplay(sw.hasHour(),sw.hasMin(),true,false);
       t.attachWatch(sw);
       return t;
    }    

    public static TimeDisplay getTsecHidingWatchDisplay(BaseWatch sw){
       TimeDisplayTsecHiding t = new TimeDisplayTsecHiding(sw.hasHour(),sw.hasMin(),true,true);
       t.attachWatch(sw);
       return t;
    }
    
    public static RingDisplay getRingDisplay(BaseWatch sw, double size, Color color){
        RingDisplay r = new RingDisplay(size, color);
        r.attachWatch(sw);
        return r;
    }

}
