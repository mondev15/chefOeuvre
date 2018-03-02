/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.scene.control.Control;
import javafx.stage.Screen;
import org.controlsfx.control.RangeSlider;


/**
 *
 * @author Charlelie
 */
public class Timeline extends VBox{

    private int LINE_HEIGHT = 150;
    
    private SingleLine mainLine;
    private SingleLine secondaryLine;
    private RangeSlider rangeSlider;
    private IntegerProperty currentTime;
    
    public Timeline() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int w = (int)primaryScreenBounds.getWidth();
        currentTime = new SimpleIntegerProperty();
        setSecondaryLine(new SingleLine(w, LINE_HEIGHT));
        setMainLine(new SingleLine(w, LINE_HEIGHT));
        setRangeSlider(new RangeSlider(0, 100, 30, 50));
        mainLine.viewEndProperty().bind(rangeSlider.highValueProperty().multiply(100));
        mainLine.viewStartProperty().bind(rangeSlider.lowValueProperty().multiply(100));
        secondaryLine.viewEndProperty().bind(rangeSlider.highValueProperty().multiply(100));
        secondaryLine.viewStartProperty().bind(rangeSlider.lowValueProperty().multiply(100));
        bindTime();
    }
    
    public SingleLine getMainLine(){
        return mainLine;
    }
    
    public SingleLine getSecondaryLine(){
        return secondaryLine;
    }
    
    public void bindTime(){
        mainLine.currentTimeProperty().bind(currentTime);
        secondaryLine.currentTimeProperty().bind(currentTime);
    }
    
    public void setMainLine(SingleLine line){
        mainLine = line;
        this.getChildren().add(mainLine);
    }
    
    public void setSecondaryLine(SingleLine line){
        secondaryLine = line;
        this.getChildren().add(secondaryLine);
    }
    
    public void setRangeSlider(RangeSlider rSlider){
        rangeSlider = rSlider;
        this.getChildren().add(rangeSlider);
    }
    
    public void setCurrentTime(int time){
        currentTime.set(time);
    }
    
    private int string2Time(String stime){
        String[] hms = stime.split(":");
        return Integer.parseInt(hms[0]) * 3600 + Integer.parseInt(hms[1]) * 60 + Integer.parseInt(hms[2]);
    }
    
}
