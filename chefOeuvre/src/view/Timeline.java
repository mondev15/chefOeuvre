/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.layout.VBox;

/**
 *
 * @author Charlelie
 */
public class Timeline extends VBox{

    private SingleLine mainLine;
    private SingleLine secondaryLine;
    
    public Timeline() {
        mainLine = new SingleLine();
        secondaryLine = new SingleLine();
    }
    
    public void setMainLine(SingleLine line){
        mainLine = line;
        this.getChildren().add(mainLine);
    }
    
    public void setSecondaryLine(SingleLine line){
        secondaryLine = line;
        this.getChildren().add(secondaryLine);
    }
    
}
