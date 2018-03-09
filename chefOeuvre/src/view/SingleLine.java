/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.InfoBlock;
import model.PresentLine;
import model.Tick;
import model.IBlock;

/**
 *
 * @author Charlelie
 */
public abstract class SingleLine extends Pane {

    protected int LINE_LENGTH;
    protected int LINE_HEIGHT;
    protected final String STATE_IDLE = "IDLE";
    protected final String STATE_DRAG = "DRAG";
    protected final String STATE_PRESENT_OUT = "OUT";
    protected IntegerProperty totalStartTime;
    protected IntegerProperty totalEndTime;
    protected IntegerProperty viewStartTime;
    protected IntegerProperty viewEndTime;
    protected IntegerProperty currentTime;

    public SingleLine() {
        this(1012, 150);
    }

    public SingleLine(int w, int h) {
        LINE_LENGTH = w;
        LINE_HEIGHT = h;
        this.setPrefSize(LINE_LENGTH, LINE_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        currentTime = new SimpleIntegerProperty();
        totalStartTime = new SimpleIntegerProperty();
        totalEndTime = new SimpleIntegerProperty();
        viewStartTime = new SimpleIntegerProperty();
        viewEndTime = new SimpleIntegerProperty();
                
        viewStartTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateBlocks();
                }
            });
        });
        viewEndTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateBlocks();
                }
            });

        });

    }

    public void updateBlocks() {}
        
    public void addBlock(IBlock b) {}
    
    public IntegerProperty currentTimeProperty() {
        return currentTime;
    }

    public IntegerProperty viewStartProperty() {
        return viewStartTime;
    }

    public IntegerProperty viewEndProperty() {
        return viewEndTime;
    }

    public IntegerProperty totalStartTimeProperty() {
        return totalStartTime;
    }

    public IntegerProperty totalEndTimeProperty() {
        return totalEndTime;
    }
    
    public int getXPos(int t) {
        float range = (float) (viewEndTime.get() - viewStartTime.get());
        return (int) (((t - viewStartTime.get()) / range) * LINE_LENGTH);
    }

    public int getTime(int xPos) {
        int range = viewEndTime.get() - viewStartTime.get();
        return (int) ((xPos / (float) LINE_LENGTH) * range + viewStartTime.get());
    }
}
