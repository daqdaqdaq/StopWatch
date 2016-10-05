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
public class UpTicker implements Ticker{
    
    double nanot;
    
    public UpTicker(){
        this.nanot = 0;
    }

    @Override
    public double Tick() {
        if (this.nanot == 0){ this.nanot = System.nanoTime();} 
        double n = System.nanoTime();

        double ret = (n-this.nanot);
        //System.out.println(new Double(n).toString()+" "+ new Double(this.nanot).toString()+" "+new Integer(ret).toString());        
        this.nanot = n;
        return ret;
    }
}
