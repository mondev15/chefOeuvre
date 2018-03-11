/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class InfoBlockSkin extends VBox{

    private int time;
    private Label titleLabel;
    private double sceneX, translateX;
    private SimpleStringProperty state = new SimpleStringProperty();
    private List<Label> listInfosLabels;
    
    private final double OPACITY = 0.6;
    private final int SIZE = 180;
    
    public InfoBlockSkin(InfoBlock b) {
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        setBackground(b.getBackground());
        setCursor(Cursor.HAND);
        setOpacity(OPACITY);
        
        
        time = b.timeProperty().get();
        titleLabel = new Label();
        titleLabel.textProperty().bind(b.getTitle().textProperty());
        
        listInfosLabels = new ArrayList<>();
        for (int i = 0; i < b.getListInfos().size(); i++) {
        	listInfosLabels.add(new Label());
        	listInfosLabels.get(i).textProperty().bind(b.getListInfos().get(i).textProperty());
        }
        
        b.stateProperty().bindBidirectional(state);
        
        getChildren().add(titleLabel);
        getChildren().addAll(listInfosLabels);
        
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
            SingleLine parent = (SingleLine)(b.getParent());
            b.setTime( parent.getTime((int)(this.getTranslateX() + b.getTranslateX())) );
            this.setTranslateX(0);
        });
       
    }
    
}
