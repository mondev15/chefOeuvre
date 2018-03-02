/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class Block extends Group{
    
    private IntegerProperty time;
    private Label titleLabel;
    private Label hdgLabel;
    private Label flLabel;
    private Label speedLabel;
    private Label infoLabel;
    private VBox content;
    private SimpleStringProperty state = new SimpleStringProperty();
    private double sceneX, translateX;
    private BlockSkin forwardBlock;
    
    
    private final int SIZE = 180;
    
    public Block(int t, String title, String hdg, String fl, String speed, String info){
        content = new VBox();
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        content.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        content.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        content.setCursor(Cursor.HAND);
                
        time = new SimpleIntegerProperty(t);
        titleLabel = new Label(title);
        hdgLabel = new Label(hdg);
        flLabel = new Label(fl);
        speedLabel = new Label(speed);
        infoLabel = new Label(info);
        
        content.getChildren().add(titleLabel);
        content.getChildren().add(hdgLabel);
        content.getChildren().add(flLabel);
        content.getChildren().add(speedLabel);
        content.getChildren().add(infoLabel);
        
        time.addListener((observable) -> {
            setTranslateX(((SingleLine)getParent()).getXPos(time.get()));
        });
                
//        this.setOnDragDetected((event) -> {
//            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
//            
//            ClipboardContent content = new ClipboardContent();
//            content.putString(titleLabel.getText());
//            db.setContent(content);
//            System.out.println("model.Block.<init>()");
//            event.consume();
//        });
        
//        this.setOnMousePressed((event) -> {
//            sceneX = event.getSceneX();
//            translateX = this.getTranslateX();
//            
//        });
//        
//        this.setOnMouseDragged((event) -> {
//            state.set("DRAG");
//            double offsetX = event.getSceneX() - sceneX;
//            double newTranslateX = translateX + offsetX;
//            if (forwardBlock != null){
//                forwardBlock.setTranslateX(newTranslateX);
//            }
//        });
//        
//        this.setOnMouseReleased((event) -> {
//            state.set("IDLE");
//            SingleLine parent = (SingleLine)(getParent());
//            this.setTime( parent.getTime((int)this.getTranslateX()) );
//        });
//        
//        state.addListener((Observable observable) -> {
//            if("IDLE".equals(state.get())){
//                content.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
//            }
//            else if("DRAG".equals(state.get())){
//                content.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//            }
//        });

        state.addListener((Observable observable) -> {
            if("IDLE".equals(state.get())){
                content.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if("DRAG".equals(state.get())){
                content.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        forwardBlock = new BlockSkin(this);

        this.getChildren().addAll(content, forwardBlock);
    }
    
    public Block(){
        this(0, "", "", "", "", "");
    }
    
    public Block(int t){
        this(t, "", "", "", "", "");
    }
    
    public Block(int t, String title){
        this(t, title, "", "", "", "");
    }
    
    public Block(int t, String title, String hdg){
        this(t, title, hdg, "", "", "");
    }

    public Block(int t, String title, String hdg, String fl){
        this(t, title, hdg, fl, "", "");
    }
    
    public Block(int t, String title, String hdg, String fl, String speed){
        this(t, title, hdg, fl, speed, "");
    }

    public SimpleStringProperty stateProperty(){
        return state;
    }
    
    public void setTime(int s){
        time.set(s);
    }
    
    public void setTitle(String s){
        titleLabel.setText(s);
    }
    
    public void setHDG(String s){
        hdgLabel.setText(s);
    }
    
    public void setFL(String s){
        flLabel.setText(s);
    }
    
    public void setSpeed(String s){
        speedLabel.setText(s);
    }
    
    public void setInfo(String s){
        infoLabel.setText(s);
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    public Label getTitle(){
        return titleLabel;
    }
    
    public Label getHDG(){
        return hdgLabel;
    }
    
    public Label getFL(){
        return flLabel;
    }
    
    public Label getSpeed(){
        return speedLabel;
    }
    
    public Label getInfo(){
        return infoLabel;
    }
}
