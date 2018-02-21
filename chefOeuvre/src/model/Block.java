/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Charlelie
 */
public class Block extends JPanel{
    
    private int time;
    private JLabel titleLabel;
    private JLabel hdgLabel;
    private JLabel flLabel;
    private JLabel speedLabel;
    private JLabel infoLabel;
    private JPanel content;
    
    private final int SIZE = 180;
    
    public Block(int t, String title, String hdg, String fl, String speed, String info){
        this.setBounds(0, 0, SIZE, SIZE);
        this.setBackground(Color.cyan);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        time = t;
        titleLabel = new JLabel(title);
        content = new JPanel();
        content.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        hdgLabel = new JLabel(hdg);
        flLabel = new JLabel(fl);
        speedLabel = new JLabel(speed);
        infoLabel = new JLabel(info);
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


    
    @Override
    public void paint(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        
        this.add(titleLabel);
        
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(10));
        g2.drawLine(0, 30, SIZE, 30);
        g2.setStroke(oldStroke);
        
        
        if (hdgLabel.getText() != ""){
            content.add(hdgLabel);
        }
        
        if (flLabel.getText() != ""){
            content.add(flLabel);
        }
        
        if (speedLabel.getText() != ""){
            content.add(speedLabel);
        }

        if (infoLabel.getText() != ""){
            content.add(infoLabel);
        }
        
        this.add(content);

    }
    
    public void setTitle(String s){
        titleLabel.setText(s);
        invalidate();
    }
    
    public void setHDG(String s){
        hdgLabel.setText(s);
        invalidate();
    }
    
    public void setFL(String s){
        flLabel.setText(s);
        invalidate();
    }
    
    public void setSpeed(String s){
        speedLabel.setText(s);
        invalidate();
    }
    
    public void setInfo(String s){
        infoLabel.setText(s);
        invalidate();
    }
    
    public int getTime(){
        return time;
    }
    
}
