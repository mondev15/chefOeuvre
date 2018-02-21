/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import model.Block;
import view.SingleLine;
import view.Timeline;

/**
 *
 * @author Charlelie
 */
public class TestTimeline {
    private static Timeline timeline;
    
    public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() {
               timeline = new Timeline();
               timeline.setVisible(true);
               SingleLine sLine1 = timeline.getSingleLine1();
               sLine1.addBlock(
                       new Block(4000,
                       "APPROCHE -> PILOTE",
                       "180 kts",
                       "Left 260°",
                       "ILS 14D",
                       "CALL BACK"));
          }
    });
}
}
