/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.timeengine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author DAQ
 */
public class TimeEngine {

    private Timeline tl;
    private final SimpleStringProperty date;
    private final SimpleStringProperty time;
    private final SimpleDoubleProperty milisecs;
    private double nanosectmp;
    private boolean started;
    private final SimpleBooleanProperty running;

    public TimeEngine() {
        this.started = false;
        this.date = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.milisecs = new SimpleDoubleProperty(0);
        this.running = new SimpleBooleanProperty(false);
        this.nanosectmp = 0;
        this.makeTimeline();
    }

    private void makeTimeline() {
        this.tl = new Timeline(
                new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
                    this.processDateTime(LocalDateTime.now());
                    this.processMilis();
                }),
                new KeyFrame(Duration.millis(50))
        );
        this.tl.setCycleCount(Animation.INDEFINITE);
    }

    private void processDateTime(LocalDateTime ldt) {

        LocalTime lt = ldt.toLocalTime();
        String tmptime = lt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        //System.out.println(tmptime);
        if (!this.time.getValue().equals(tmptime)) {
            this.time.set(tmptime);
        }

        LocalDate dt = ldt.toLocalDate();
        String tmpdate = dt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        //System.out.println(tmpdate);
        if (!this.date.getValue().equals(tmpdate)) {
            this.date.set(tmpdate);
        }

    }

    private void processMilis() {
        //System.out.println("Alles in ordnung");
        double nano = System.nanoTime();
        if (this.running.get()) {

            //System.out.println((int)(this.nanos/1000000)+":"+this.milisecs);
            this.milisecs.set(((nano - this.nanosectmp) / 1000000));

        }
        this.nanosectmp = nano;

    }

    public void init() {
        this.tl.play();
        this.started = true;
        this.running.set(true);
        this.nanosectmp = System.nanoTime();
    }

    public void start() {
        this.tl.play();
        this.running.set(true);
       
    }

    public void pause() {
        this.running.set(false);
        //this.tl.pause();
    }

    public SimpleStringProperty getDate() {
        return date;
    }

    public SimpleStringProperty getTime() {
        return time;
    }

    public SimpleDoubleProperty getMilisecs() {
        return this.milisecs;
    }

    public SimpleBooleanProperty getRunning() {
        return running;
    }

    public Boolean isStarted() {
        return this.started;
    }

    public void hibernate(){
        this.tl.pause();
    }
    
    public void finalize() {
        this.tl.stop();
    }

}
