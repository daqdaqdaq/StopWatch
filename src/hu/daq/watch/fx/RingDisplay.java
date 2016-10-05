/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.fx;

import hu.daq.watch.BaseWatch;
import hu.daq.watch.Time;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

/**
 *
 * @author DAQ
 */
public class RingDisplay extends StackPane{
    TimeDisplay td;
    Color color;
    List<Shape> segments;
    Integer currentsegment;
    double sizes;
    
    public RingDisplay(double size, Color color) {
        //super();
        this.sizes = size;
        //this.setAlignment(Pos.CENTER);
        //this.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1)))); 
        this.segments = new ArrayList<Shape>();
        this.td = new TimeDisplay(false,false,true,false);
        this.td.setFont(new Font(30));
        this.td.setAlignment(Pos.CENTER);
        this.td.setColor(color);
        this.color = color;
        this.setWidth(size);
        this.setHeight(size);
        this.setMaxSize(size,size);
        this.setMinSize(size,size);
        this.setPrefSize(size,size);        
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    }
    

    public static Shape getSegment(double centerx, double centery, double outradius, double inradius, double angle, Color color) {
        Arc outer = new Arc(centerx, centery, outradius, outradius, 1, angle-1);
        outer.setType(ArcType.ROUND);

        Arc inner = new Arc(centerx, centery, inradius, inradius, 1, angle-1);
        inner.setType(ArcType.ROUND);

        Shape segment = Shape.subtract(outer, inner);
        segment.setFill(color);
        return segment;
    }
    
    
    public void attachWatch(BaseWatch sw){
        Time t = sw.getObservableTime();
        this.td.attachWatch(sw);
        this.buildSegments((int)t.getMilistocount()/1000);
        this.currentsegment = this.segments.size();

        sw.getComputedmilis().addListener((ObservableValue<? extends Number> observable,Number ov,Number nv)->{
            if ( ceil((double)nv/1000)!=this.currentsegment ){
                this.segments.get(this.segments.size()-this.currentsegment).setFill(Color.TRANSPARENT);
                this.currentsegment = (int)ceil((double)nv/1000);
            }
        });

    }
    
    private void buildSegments(int count){
        double radius = (this.sizes-10)/2;
        double center = this.sizes/2;
        double angle = 360/count;
        Group ring = new Group();
        for (int i=0; i<count; i++){
            Shape seg = getSegment(center,center,radius, radius*0.7,angle,this.color);
            this.segments.add(seg);
            seg.getTransforms().add(new Rotate(-90+(i+1)*angle,center,center));
            ring.getChildren().add(seg);
        }
        this.getChildren().add(ring);
        //this.layout();
        //System.out.println(this.getHeight()+":"+this.getWidth());
        this.getChildren().add(this.td);
        //this.getChildren().add(new Label("Javafx is an idiot"));
        //StackPane.setAlignment(this.td, Pos.CENTER_LEFT);

    }
    
    
}
