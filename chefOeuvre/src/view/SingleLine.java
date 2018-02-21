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
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.Block;

/**
 *
 * @author Charlelie
 */
public class SingleLine extends JPanel{
    
    private final int LINE_LENGTH = 1012;
    private final int LINE_HEIGHT = 200;
    private int startTime = 0;
    private int endTime = 10000;
    private int startView = 3000;
    private int endView = 5000;

    
    private List<Block> blockList;

    
    public SingleLine(){
        blockList = new ArrayList<>();
        this.setBounds(6, 0, LINE_LENGTH, LINE_HEIGHT);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        for (Block block : blockList) {
            if (block.getTime() > startView && block.getTime() < endView){
                block.setLocation(getXPos(block), 0);
                this.add(block);
            }
        }
    }
    
    public void addBlock(Block b){
        blockList.add(b);
        invalidate();
    }
    
    public void setBlockList(List<Block> bs){
        blockList = bs;
        invalidate();
    }
    
    public List<Block> getBlockList(){
        return blockList;
    }
    
    private int getXPos(Block block){
        return (int) (((block.getTime()-startView)/(float)(endView - startView))*WIDTH);
    }
}

