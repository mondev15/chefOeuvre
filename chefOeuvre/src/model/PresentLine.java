/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class PresentLine extends Line {

    IntegerProperty time;
    private double sceneX, translateX;
    private int WIDTH = 10;

    public PresentLine() {
        this(0, 0, 0, 0);
    }

    public PresentLine(double xs, double ys, double xe, double ye) {
        super(xs, ys, xe, ye);
        time = new SimpleIntegerProperty();
        setStroke(Color.GOLD);
        setStrokeWidth(WIDTH);

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
            SingleLine parent = (SingleLine) (this.getParent());
            this.time.set(parent.getTime((int) (this.getTranslateX())));
        });
    }

    public IntegerProperty timeProperty() {
        return time;
    }

}
