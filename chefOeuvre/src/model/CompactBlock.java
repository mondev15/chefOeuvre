/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class CompactBlock extends Group implements IBlock{
    private IntegerProperty time;
    private IntegerProperty duration;
    private SimpleStringProperty state = new SimpleStringProperty();
    private Rectangle main;
    private CompactBlockSkin forwardBlock;
    private final int HEIGHT = 40;
    private final int INITIAL_LENGTH = 10;
    private Color BACKGROUND_COLOR = Color.rgb(229, 229, 229);
    private Color DRAGGED_COLOR = Color.rgb(150, 150, 255);

    public CompactBlock(){
        this(1000, 10);
    }
    
    public CompactBlock(int t){
        this(t, 10);
    }
    
    public CompactBlock(int t, int d){
        time = new SimpleIntegerProperty(t);
        duration = new SimpleIntegerProperty(d);
        main = new Rectangle(INITIAL_LENGTH, HEIGHT);
        main.setFill(BACKGROUND_COLOR);
        main.setCursor(Cursor.HAND);
        
        time.addListener((observable) -> {
            setTranslateX(((SingleLine)getParent()).getXPos(time.get()));
        });

        state.addListener((Observable observable) -> {
            if("IDLE".equals(state.get())){
                main.setFill(BACKGROUND_COLOR);
            }
            else if("DRAG".equals(state.get())){
                main.setFill(DRAGGED_COLOR);
            }
        });

        forwardBlock = new CompactBlockSkin(this);

        this.getChildren().addAll(main, forwardBlock);
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    public void setTime(int s){
        time.set(s);
    }
    
    public IntegerProperty durationProperty(){
        return duration;
    }
    
    public SimpleStringProperty stateProperty(){
        return state;
    }
    
    public void setWidth(int w){
        main.setWidth(w);
        forwardBlock.setWidth(w);
    }
    
    public Paint getColor(){
        return main.getFill();
    }
}
