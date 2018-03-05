/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import model.Tick;

/**
 *
 * @author Charlelie
 */
public class SingleLine extends Pane{
    
    private int LINE_LENGTH = 1012;
    private int LINE_HEIGHT = 150;
    private final String STATE_IDLE = "IDLE";
    private final String STATE_DRAG = "DRAG";
    private int totalStartTime = 0;
    private int totalEndTime = 10000;
    private IntegerProperty viewStartTime;
    private IntegerProperty viewEndTime;
    private IntegerProperty currentTime;
    private StringProperty state = new SimpleStringProperty();
    private IntegerProperty tickState = new SimpleIntegerProperty();
    private final int TINY_TICKS = 60;
    private final int MEDIUM_TICKS = 300;
    private final int BIG_TICKS = 600;
    
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
            if(STATE_IDLE.equals(state.get())){
                this.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if(STATE_DRAG.equals(state.get())){
                this.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        
        state.set(STATE_IDLE);
        tickState.addListener((observable) -> {updateTicks();});
        tickState.set(MEDIUM_TICKS);
        
        
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
            if(node instanceof Tick){
                Tick tick = (Tick)node;
                int pos = getXPos(tick.timeProperty().get());
                tick.setTranslateX(pos);
            }
        }
        
        int range = viewEndTime.get() - viewStartTime.get();
        switch (tickState.get()){
            case TINY_TICKS:
                if(range < 15*60){
                    break;
                }
                if(range >= 15*60 && range <= 30*60){
                    tickState.set(MEDIUM_TICKS);
                }
                if(range > 30*60){
                    tickState.set(BIG_TICKS);
                }
            case MEDIUM_TICKS:
                if(range < 15*60){
                    tickState.set(TINY_TICKS);
                }
                if(range >= 15*60 && range <= 30*60){
                    break;
                }
                if(range > 30*60){
                    tickState.set(BIG_TICKS);
                }
            case BIG_TICKS:
                if(range < 15*60){
                    tickState.set(TINY_TICKS);
                }
                if(range >= 15*60 && range <= 30*60){
                    tickState.set(MEDIUM_TICKS);
                }
                if(range > 30*60){
                    break;
                }
        }        
        
    }
    
    public void updateTicks(){
        System.out.println("view.SingleLine.updateTicks()");
        Iterator<Node> iter = this.getChildren().iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if(node instanceof Tick){
                iter.remove();
            }
        }
        
        int tickTime = totalStartTime;
        while(tickTime < totalEndTime){
            System.out.println("yo");
            Tick tick = new Tick();
            this.getChildren().add(tick);
            tick.setTime(tickTime);
            tickTime += tickState.get();
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
    
    private void addTicks(){
        
    }
}