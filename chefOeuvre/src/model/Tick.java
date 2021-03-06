/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import view.SingleLine;
import view.Timeline;

/**
 * Time ticks for the timeline, same time property
 * 
 * @author Charlelie
 */
public class Tick extends VBox{
    private IntegerProperty time = new SimpleIntegerProperty();
    private Label timeLabel;
    private Line tick;
    private final int TICK_SIZE = 8;
    
    public Tick(){
        timeLabel = new Label();
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setTranslateX(-25);
        time.addListener((observable) -> {
            setTranslateX(((SingleLine)getParent()).getXPos(time.get()));
            timeLabel.setText(TimeFunctions.intTohms(time.get()));
        });
        
        tick = new Line(this.getTranslateX(), this.getTranslateY(), this.getTranslateX(), this.getTranslateY()+TICK_SIZE);
        tick.setStroke(Color.WHITE);
        this.getChildren().add(timeLabel);
        this.getChildren().add(tick);
        this.setMouseTransparent(true);
    }
    
    public void setTime(int t){
        time.set(t);
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    
}
