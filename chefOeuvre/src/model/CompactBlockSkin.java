/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class CompactBlockSkin extends Rectangle {

    private int time;
    private double sceneX, translateX;
    private SimpleStringProperty state = new SimpleStringProperty();
    private final int HEIGHT = 40;
    private final int INITIAL_LENGTH = 10;
    private final double OPACITY = 0.6;

    public CompactBlockSkin(CompactBlock cb) {
        setWidth(INITIAL_LENGTH);
        setHeight(HEIGHT);
        setCursor(Cursor.HAND);
        setFill(cb.getColor());
        setOpacity(OPACITY);

        time = cb.timeProperty().get();
        
        cb.stateProperty().bindBidirectional(state);
        
        this.setOnMousePressed((event) -> {
            sceneX = event.getSceneX();
            translateX = this.getTranslateX();
        });

        this.setOnMouseDragged((event) -> {
            state.set("DRAG");
            double offsetX = event.getSceneX() - sceneX;
            double newTranslateX = translateX + offsetX;
            setTranslateX(newTranslateX);
        });

        this.setOnMouseReleased((event) -> {
            state.set("IDLE");
            SingleLine parent = (SingleLine) (cb.getParent());
            cb.setTime(parent.getTime((int) (this.getTranslateX() + cb.getTranslateX())));
            this.setTranslateX(0);
        });
    }
}
