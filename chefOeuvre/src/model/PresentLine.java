/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import ivy.IvyManager;
import java.awt.Stroke;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import view.SingleLine;
import view.Timeline;

/**
 *
 * @author Charlelie
 */
public class PresentLine extends Line {
    IntegerProperty time;
    private double sceneX, translateX;
    private final String STATE_IDLE = "IDLE";
    private final Paint COLOR = Color.rgb(255, 180, 0);
    private final Color SHADOW_COLOR = Color.rgb(51, 50, 48);
    private final int WIDTH = 10;
    private DropShadow shadow;
    
    public PresentLine() {
        this(0, 0, 0, 0);
    }

    public PresentLine(double xs, double ys, double xe, double ye) {
        super(xs, ys, xe, ye);
        
        shadow = new DropShadow(WIDTH, 8, 0, SHADOW_COLOR); 
        
        this.setStroke(COLOR);
        this.setStrokeWidth(WIDTH);
        this.setEffect(shadow);
        
        time = new SimpleIntegerProperty();

        this.setOnMousePressed((event) -> {
            sceneX = event.getSceneX();
            translateX = this.getTranslateX();

        });

        this.setOnMouseDragged((event) -> {
            double offsetX = event.getSceneX() - sceneX;
            double newTranslateX = translateX + offsetX;
            setTranslateX(newTranslateX);
        });

        this.setOnMouseReleased((event) -> {
            Timeline timeline = (Timeline) (this.getParent());
            SingleLine mainLine = timeline.getMainLine();
            this.time.set(mainLine.getTime((int) (this.getTranslateX())));
            timeline.stateProperty().set(STATE_IDLE);
            IvyManager.setClockTime(time.get());
        });
    }

    public IntegerProperty timeProperty() {
        return time;
    }

}
