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
public class CountdownWatch extends BaseWatch{

    
    public CountdownWatch(TimeEngine ti,int hours, int mins, int secs){
        super(ti,hours,mins,secs);
        this.setTime();
    }

    public CountdownWatch(TimeEngine ti) {
        super(ti);
    }

    
    
    @Override
        public void set(int milisec){
        //System.out.println("Watch is setting to:"+milisec);
        if (milisec > this.milistocount) {
            milisec = (int) this.milistocount;
        } else if (milisec < 0) {
            milisec = 0;
        }
        this.currentmilis = this.milistocount-milisec;
        //System.out.println("Watch is set to:"+this.currentmilis);
        this.checkForTimeout();
        this.setTime();        
       
        /*if (milisec> this.milistocount) {
            milisec = (int)this.milistocount;
        } else  if (milisec<0) {
            milisec = 0;
        } 
        this.currentmilis = this.milistocount-milisec;
        this.setTime();
        */
    }
        
    @Override
    protected void setTime() {
            this.computedmilis.set(this.milistocount-this.currentmilis);
            //System.out.println("CountdownWatch setting time:"+this.computedmilis.get()+":"+this.currentmilis);            
            //System.out.println(this.computedmilis.get());
    }

}
