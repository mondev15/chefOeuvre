/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Block;

/**
 *
 * @author Charlelie
 */
public class SingleLine extends Pane{
    
    private final int LINE_LENGTH = 1012;
    private final int LINE_HEIGHT = 200;
    private int startTime = 0;
    private int endTime = 10000;
    private int startView = 3000;
    private int endView = 5000;

    

    
    public SingleLine(){
        this.setPrefSize(LINE_LENGTH, LINE_HEIGHT);
    }
    
//    @Override
//    public void paint(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        
//        for (Block block : blockList) {
//            if (block.getTime() > startView && block.getTime() < endView){
//                block.setLocation(getXPos(block), 0);
//                this.add(block);
//            }
//        }
//    }
    
    public void addBlock(Block b){
        int pos = getXPos(b);
        this.getChildren().add(b);
        b.setTranslateX(pos);
    }
    
    private int getXPos(Block block){
        return (int) (((block.getTime()-startView)/(float)(endView - startView))*LINE_LENGTH);
    }
}

