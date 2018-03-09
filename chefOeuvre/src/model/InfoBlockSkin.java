/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    private Label hdgLabel;
    private Label flLabel;
    private Label speedLabel;
    private Label infoLabel;
    private double sceneX, translateX;
    private SimpleStringProperty state = new SimpleStringProperty();

    
    
    private final int SIZE = 180;
    
    public InfoBlockSkin(InfoBlock b) {
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setCursor(Cursor.HAND);
        setOpacity(0.4);
        
        
        time = b.timeProperty().get();
        titleLabel = new Label();
        titleLabel.textProperty().bind(b.getTitle().textProperty());
        hdgLabel = new Label();
        hdgLabel.textProperty().bind(b.getHDG().textProperty());
        flLabel = new Label();
        flLabel.textProperty().bind(b.getFL().textProperty());
        speedLabel = new Label();
        speedLabel.textProperty().bind(b.getSpeed().textProperty());
        infoLabel = new Label();
        infoLabel.textProperty().bind(b.getInfo().textProperty());
        
        b.stateProperty().bindBidirectional(state);
        
        getChildren().addAll(titleLabel, hdgLabel, flLabel, speedLabel, infoLabel);
        
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
