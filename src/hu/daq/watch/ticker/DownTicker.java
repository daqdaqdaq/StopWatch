/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.ticker;

/**
 *
 * @author DAQ
 */
public class DownTicker implements Ticker{
    
    double nanot;
    
    public DownTicker(){
        this.nanot = System.nanoTime();
    }

    @Override
    public double Tick() {
        double n = System.nanoTime();
        double ret = (this.nanot-n);
        this.nanot = n;
        return ret;
    }
  
    
}
