/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Block;

/**
 *
 * @author Charlelie
 */
public class SingleLine extends Pane{
    
    private int LINE_LENGTH = 1012;
    private int LINE_HEIGHT = 150;
    private int totalStartTime = 0;
    private int totalEndTime = 10000;
    private IntegerProperty viewStartTime;
    private IntegerProperty viewEndTime;
    private IntegerProperty currentTime;
    private SimpleStringProperty state = new SimpleStringProperty();
    
    public SingleLine(){
        this(1012, 150);
    }
    
    public SingleLine(int w, int h){
        LINE_LENGTH = w;
        LINE_HEIGHT = h;
        this.setPrefSize(LINE_LENGTH, LINE_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        currentTime = new SimpleIntegerProperty();
        viewStartTime = new SimpleIntegerProperty();
        viewEndTime = new SimpleIntegerProperty();
        viewStartTime.addListener((observable) -> {updateBlocks();});
        viewEndTime.addListener((observable) -> {updateBlocks();});
        currentTime.addListener((observable) -> {updateBlocks();});

        state.addListener((Observable observable) -> {
            if("IDLE".equals(state.get())){
                this.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if("DRAG".equals(state.get())){
                this.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        
        state.set("IDLE");
//        
//        this.setOnDragOver(new EventHandler<DragEvent>() {
//            public void handle(DragEvent event) {
//                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
//                    /* allow for both copying and moving, whatever user chooses */
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//                event.consume();
//            }
//        });
//        
//        this.setOnDragEntered(new EventHandler<DragEvent>() {
//            public void handle(DragEvent event) {
//            /* the drag-and-drop gesture entered the target */
//            /* show to the user that it is an actual gesture target */
//                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
//                    state.set("DRAG");
//                }
//                event.consume();
//            }
//        });
//        
//        this.setOnDragExited(new EventHandler<DragEvent>() {
//            public void handle(DragEvent event) {
//                /* mouse moved away, remove the graphical cues */
//                state.set("IDLE");
//                event.consume();
//            }
//        });
        
    }
    
    public void updateBlocks(){
        for(Node node : this.getChildren()){
            if(node instanceof Block){
                Block b = (Block)node;
                int pos = getXPos(b.timeProperty().get());
                b.setTranslateX(pos);
            }
        }
    }
    
    public void addBlock(Block b){
        int pos = getXPos(b.timeProperty().get());
        this.getChildren().add(b);
        b.stateProperty().bindBidirectional(state);
        b.setTranslateX(pos);
    }
    
//    public List<Block> getBlocks(){
//        return this.getChildren();
//    }
    
    public IntegerProperty currentTimeProperty(){
        return currentTime;
    }
    
    public IntegerProperty viewStartProperty(){
        return viewStartTime;
    }
    
    public IntegerProperty viewEndProperty(){
        return viewEndTime;
    }
    
    public int getXPos(int t){
        float range = (float)(viewEndTime.get() - viewStartTime.get());
        return (int) (((t-viewStartTime.get())/range)*LINE_LENGTH);
    }
    
    public int getTime(int xPos){
        int range = viewEndTime.get() - viewStartTime.get();
        return (int) ((xPos/(float)LINE_LENGTH)*range + viewStartTime.get());
    }
}