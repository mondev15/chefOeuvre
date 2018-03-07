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

/**
 *
 * @author Charlelie
 */
public class Tick extends VBox{
    private IntegerProperty time = new SimpleIntegerProperty();
    private Label timeLabel;
    private Line tick;
    private final int TICK_SIZE = 15;
    private final String format = "%02d";
    
    public Tick(){
        timeLabel = new Label();
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setTranslateX(-25);
        time.addListener((observable) -> {
            setTranslateX(((SingleLine)getParent()).getXPos(time.get()));
            timeLabel.setText(timeToString(time.get()));
        });
        
        tick = new Line(this.getTranslateX(), this.getTranslateY(), this.getTranslateX(), this.getTranslateY()+TICK_SIZE);
        tick.setStroke(Color.WHITE);
        this.getChildren().add(timeLabel);
        this.getChildren().add(tick);
        this.setMouseTransparent(true);
    }
    
    private String timeToString(int time){
        int h = time/3600;
        time -= h*3600;
        int m = time/60;
        time -= m*60;
        return (String.format(format, h) + ":" + String.format(format, m) + ":" + String.format(format, time));
    }
    
    public void setTime(int t){
        time.set(t);
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    
}
