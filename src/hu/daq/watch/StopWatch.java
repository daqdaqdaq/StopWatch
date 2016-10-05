/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch;

import hu.daq.timeengine.TimeEngine;

/**
 *
 * @author DAQ
 */
public class StopWatch extends BaseWatch{
 
    public StopWatch(TimeEngine ti,int hours, int mins, int secs){
        super(ti,hours,mins,secs);
        this.setTime();
        
    }

    public StopWatch(TimeEngine ti) {
        super(ti);
    }
    
    @Override
    protected void setTime() {

            this.computedmilis.set(this.currentmilis);
            //System.out.println("StopWatch setting time:"+this.computedmilis.get()+":"+this.currentmilis);
            //System.out.println(this.computedmilis.get());
       
    }

}
