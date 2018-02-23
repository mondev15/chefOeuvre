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
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author Charlelie
 */
public class Block extends VBox{
    
    private int time;
    private Label titleLabel;
    private Label hdgLabel;
    private Label flLabel;
    private Label speedLabel;
    private Label infoLabel;
    private VBox content;
    
    private final int SIZE = 180;
    
    public Block(int t, String title, String hdg, String fl, String speed, String info){
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        this.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        
        time = t;
        titleLabel = new Label(title);
        content = new VBox();
        hdgLabel = new Label(hdg);
        flLabel = new Label(fl);
        speedLabel = new Label(speed);
        infoLabel = new Label(info);
        
        this.getChildren().add(titleLabel);
        content.getChildren().add(hdgLabel);
        content.getChildren().add(flLabel);
        content.getChildren().add(speedLabel);
        content.getChildren().add(infoLabel);
        
        this.getChildren().add(content);
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


    
//    @Override
//    public void paint(Graphics g){
//        
//        Graphics2D g2 = (Graphics2D) g;
//        
//        this.add(titleLabel);
//        
//        Stroke oldStroke = g2.getStroke();
//        g2.setStroke(new BasicStroke(10));
//        g2.drawLine(0, 30, SIZE, 30);
//        g2.setStroke(oldStroke);
//        
//        
//        if (hdgLabel.getText() != ""){
//            content.add(hdgLabel);
//        }
//        
//        if (flLabel.getText() != ""){
//            content.add(flLabel);
//        }
//        
//        if (speedLabel.getText() != ""){
//            content.add(speedLabel);
//        }
//
//        if (infoLabel.getText() != ""){
//            content.add(infoLabel);
//        }
//        
//        this.add(content);
//
//    }
    
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
    
    public int getTime(){
        return time;
    }
    
}
