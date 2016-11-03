/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch;

import hu.daq.timeengine.TimeEngine;
import static java.lang.Math.floor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author DAQ
 */
public abstract class BaseWatch {

    protected TimeoutListener tl;
    protected static final double MILISEC = 1000;
    protected TimeEngine ti;
    public SimplePositiveIntegerProperty hour;
    public SimplePositiveIntegerProperty min;
    public SimplePositiveIntegerProperty sec;
    public SimplePositiveIntegerProperty tsec;
    protected SimpleDoubleProperty computedmilis;
    private boolean running;
    protected double milistocount;
    protected double currentmilis;
    private ChangeListener listener;

    public BaseWatch(TimeEngine ti, int hours, int mins, int secs) {
        this.running = false;
        this.hour = new SimplePositiveIntegerProperty(0);
        this.min = new SimplePositiveIntegerProperty(0);
        this.sec = new SimplePositiveIntegerProperty(0);
        this.tsec = new SimplePositiveIntegerProperty(0);
        this.computedmilis = new SimpleDoubleProperty();
        this.setTimeToCount(hours, mins, secs);
        this.currentmilis = 0;

        this.listener = (observable, ov, nv) -> {
                    //System.out.println("New value "+nv);
                    if (this.running) {
                        if (!this.checkForTimeout()) {
                            this.currentmilis += (double) nv;
                            if (this.currentmilis > this.milistocount) {
                                this.currentmilis = this.milistocount;
                            }

                            this.setTime();
                        }
                    }
                };

        this.setTimeEngine(ti);
        this.bindDigits();
    }

    public BaseWatch(TimeEngine ti) {
        this(ti, 0, 0, 0);
    }

    public void setTimeToCount(int hours, int mins, int secs) {
        this.setTimeToCount(hours * 3600 * MILISEC + mins * 60 * MILISEC + secs * MILISEC);
    }

    public void setTimeToCount(double milisec) {
        //System.out.println("Set time to count:" + milisec);
        this.milistocount = milisec;
        this.currentmilis = 0;
        //System.out.println("Time has set to:" + this.milistocount);
    }

    public int getTimeToCount() {
        return (int) this.milistocount;
    }

    public void start() {
        //System.out.println("Watch is starting " + this.milistocount);
        this.running = true;
    }

    public void pause() {
        this.running = false;
    }

    public void reset() {
        //this.running = false;
        //this.set(0);
        this.currentmilis = 0;
        this.setTime();
    }

    public void set(int hour, int min, int sec, int tsec) {
        this.set((int) (hour * 3600 * MILISEC + min * 60 * MILISEC + sec * MILISEC + tsec * MILISEC / 100));
    }

    public void set(int milisec) {
        //System.out.println("Watch is setting to:"+milisec);
        if (milisec > this.milistocount) {
            milisec = (int) this.milistocount;
        } else if (milisec < 0) {
            milisec = 0;
        }
        this.currentmilis = milisec;
        //System.out.println("Watch is set to:"+this.currentmilis);
        this.checkForTimeout();
        this.setTime();
    }

    public void jumpToEnd() {
        //System.out.println("Watch is jumping to end..");
        this.currentmilis = this.milistocount;
        this.checkForTimeout();
        this.setTime();
    }

    private void bindDigits() {
        this.computedmilis.addListener((observable, ov, nv) -> {
            this.hour.set(((Double) floor((double) nv / (3600 * MILISEC))).intValue());
            this.min.set(((Double) floor(((double) nv / (60 * MILISEC)) % 60)).intValue());
            this.sec.set(((Double) floor(((double) nv / (MILISEC)) % 60)).intValue());
            this.tsec.set(((Double) (((double) nv % MILISEC) / 100)).intValue());
        });
        /*
         This stops working after a while because the bindings' weak listener getting garbage collected
         this.hour.bind(Bindings.createIntegerBinding(() -> {
         return ((Double) floor(this.computedmilis.getValue() / (3600 * MILISEC))).intValue();
         }, this.computedmilis));

         this.min.bind(Bindings.createIntegerBinding(() -> {
         return ((Double) floor((this.computedmilis.getValue() / (60 * MILISEC)) % 60)).intValue();
         }, this.computedmilis));

         this.sec.bind(Bindings.createIntegerBinding(() -> {
         return ((Double) floor((this.computedmilis.getValue() / MILISEC) % 60)).intValue();
         }, this.computedmilis));

         this.tsec.bind(Bindings.createIntegerBinding(() -> {
         return ((Double)(this.computedmilis.getValue()%(MILISEC/100))).intValue();
         }, this.computedmilis));
         */
    }
    public void setTimeEngine(TimeEngine ti){
        if (this.ti!= null){
            this.ti.getMilisecs().removeListener(listener);
        }
        this.ti = ti;
        this.ti.getMilisecs().addListener(listener);
    }


    protected boolean checkForTimeout() {
        //System.out.println("Checking for timeout");
        if (this.currentmilis == this.milistocount && this.tl != null) {
            //System.out.println("Timeout");
            this.setTime();
            tl.timeout();
            return true;
        }
        return false;
    }

    public BaseWatch addTimeoutListener(TimeoutListener tl) {
        this.tl = tl;
        return this;
    }

    public TimeoutListener getTimeoutListener() {
        return tl;
    }

    public Time getObservableTime() {
        Time t = new Time(this.hour, this.min, this.sec, this.tsec);
        t.setMilisToCount((int) this.milistocount);
        return t;
    }

    public Boolean hasHour() {
        return this.milistocount >= 3600 * MILISEC;
    }

    public Boolean hasMin() {
        return this.milistocount >= 60 * MILISEC;
    }

    public SimpleDoubleProperty getComputedmilis() {
        return computedmilis;
    }

    public SimpleBooleanProperty getTimeEngineRunning() {
        return this.ti.getRunning();
    }

    public int getRemainingTime() {
        return (int) (this.milistocount - this.currentmilis);
    }

    protected abstract void setTime();

}
