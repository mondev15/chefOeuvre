/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
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
    private int STEP = 60;
    
    private SingleLine mainLine;
    private SingleLine secondaryLine;
    private RangeSlider rangeSlider;
    private IntegerProperty currentTime;
    private IntegerProperty totalStartTime;
    private IntegerProperty totalEndTime;
    private IntegerProperty clockTime;
    
    public Timeline() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int w = (int)primaryScreenBounds.getWidth();
        currentTime = new SimpleIntegerProperty();
        clockTime = new SimpleIntegerProperty();        
        totalStartTime = new SimpleIntegerProperty();
        totalEndTime = new SimpleIntegerProperty();
        setMainLine(new SingleLine(w, LINE_HEIGHT));

        //setSecondaryLine(new SingleLine(w, LINE_HEIGHT));
        setRangeSlider(new RangeSlider(0, 100, 0, 24));
        
        
        totalStartTime.addListener((observable) -> {
            rangeSlider.setLowValue(0);
        });
        
        totalEndTime.addListener((observable) -> {
            rangeSlider.setHighValue(25);
        });
        
        rangeSlider.lowValueProperty().addListener((observable) -> {
            int range = (totalEndTime.get() - totalStartTime.get())/100;
            mainLine.viewStartProperty().set((int)(rangeSlider.lowValueProperty().get()*range) + totalStartTime.get());
//            secondaryLine.viewStartProperty().set((int)(rangeSlider.lowValueProperty().get()*range) + totalStartTime.get());
        });
        
        rangeSlider.highValueProperty().addListener((observable) -> {
            int range = (totalEndTime.get() - totalStartTime.get())/100;
            mainLine.viewEndProperty().set((int)(rangeSlider.highValueProperty().get()*range) + totalStartTime.get());
//            secondaryLine.viewEndProperty().set((int)(rangeSlider.highValueProperty().get()*range) + totalStartTime.get());
        });
//        secondaryLine.viewEndProperty().bind(rangeSlider.highValueProperty().multiply(100));
//        secondaryLine.viewStartProperty().bind(rangeSlider.lowValueProperty().multiply(100));
        bindTime();
        totalEndTime.set(10000);
        totalStartTime.set(0);
        currentTime.set(100);
        rangeSlider.setHighValue(25);
    }
    
    public SingleLine getMainLine(){
        return mainLine;
    }
    
    public SingleLine getSecondaryLine(){
        return secondaryLine;
    }
    
    public void bindTime(){
        mainLine.currentTimeProperty().bind(currentTime);
        mainLine.totalStartTimeProperty().bind(totalStartTime);
        mainLine.totalEndTimeProperty().bind(totalEndTime);
//        secondaryLine.currentTimeProperty().bind(currentTime);
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
    
    public void setClockTime(int time){
        if(clockTime.get() != 0){
            double range = (totalEndTime.get() - totalStartTime.get())/100.0;
            double step = 1/range;
            currentTime.set(currentTime.get() + 1);
            rangeSlider.setLowValue(rangeSlider.getLowValue() + step);
            rangeSlider.setHighValue(rangeSlider.getHighValue() + step);
        }
        clockTime.set(time);
    }
    
    public IntegerProperty totalStartTimeProperty(){
        return totalStartTime;
    }
    
    public IntegerProperty totalEndTimeProperty(){
        return totalEndTime;
    }
    
    public IntegerProperty clockTimeProperty(){
        return clockTime;
    }
    
    public RangeSlider getRangeSlider(){
        return rangeSlider;
    }
    
    public int hmsToInt(String hms){
        List<String> splitted = new ArrayList(Arrays.asList(hms.split(":")));
        return(Integer.parseInt(splitted.get(0)) * 3600
                + Integer.parseInt(splitted.get(1)) * 60
                + Integer.parseInt(splitted.get(2)));
    }
    
}
