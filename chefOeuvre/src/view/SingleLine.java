/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Block;
import model.PresentLine;
import model.Tick;

/**
 *
 * @author Charlelie
 */
public abstract class SingleLine extends Pane {

    protected int LINE_LENGTH;
    protected int LINE_HEIGHT;
    protected final String STATE_IDLE = "IDLE";
    protected final String STATE_DRAG = "DRAG";
    protected final String STATE_PRESENT_OUT = "OUT";
    protected IntegerProperty totalStartTime;
    protected IntegerProperty totalEndTime;
    protected IntegerProperty viewStartTime;
    protected IntegerProperty viewEndTime;
    protected IntegerProperty currentTime;
//    private StringProperty state;
//    private IntegerProperty tickState = new SimpleIntegerProperty();
//    private final int TINY_TICKS = 60;
//    private final int MEDIUM_TICKS = 300;
//    private final int BIG_TICKS = 600;
//    private PresentLine presentLine;
//    private Button leftGoBackButton;
//    private Button rightGoBackButton;

    public SingleLine() {
        this(1012, 150);
    }

    public SingleLine(int w, int h) {
        LINE_LENGTH = w;
        LINE_HEIGHT = h;
        this.setPrefSize(LINE_LENGTH, LINE_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        currentTime = new SimpleIntegerProperty();
        totalStartTime = new SimpleIntegerProperty();
        totalEndTime = new SimpleIntegerProperty();
        viewStartTime = new SimpleIntegerProperty();
        viewEndTime = new SimpleIntegerProperty();
        
//        rightGoBackButton = new Button(">>");
//        rightGoBackButton.setTranslateY(LINE_HEIGHT - 40);
//        rightGoBackButton.setTranslateX(LINE_LENGTH - 80);
//        leftGoBackButton = new Button("<<");
//        leftGoBackButton.setTranslateY(LINE_HEIGHT - 40);
//        leftGoBackButton.setTranslateX(40);
        
//        rightGoBackButton.setOnAction((e) -> {centerOnPresent();});
//        leftGoBackButton.setOnAction((e) -> {centerOnPresent();});
        
                
        viewStartTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateBlocks();
                }
            });
        });
        viewEndTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateBlocks();
                }
            });

        });
//        currentTime.addListener((observable) -> {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    presentLine.timeProperty().set(currentTime.get());
//                    updateBlocks();
//                }
//            });
//        });

//        state.addListener((Observable observable) -> {
//            String currentState = state.get();
//            switch (currentState){
//                case STATE_IDLE:
//                    this.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
//                    rightGoBackButton.setVisible(false);
//                    leftGoBackButton.setVisible(false);
//                    break;
//                case STATE_DRAG:
//                    this.setBackground(new Background(new BackgroundFill(Color.rgb(140, 140, 140), CornerRadii.EMPTY, Insets.EMPTY)));
//                    rightGoBackButton.setVisible(false);
//                    leftGoBackButton.setVisible(false);
//                    break;
//                case STATE_PRESENT_OUT:
//                    if (presentLine.timeProperty().get() <= viewStartTime.get()){
//                        rightGoBackButton.setVisible(false);
//                        leftGoBackButton.setVisible(true);
//                    }
//                    else if (presentLine.timeProperty().get() >= viewEndTime.get()){
//                        rightGoBackButton.setVisible(true);
//                        leftGoBackButton.setVisible(false);
//                    }
//            }
//        });

//        state.set(STATE_IDLE);
//        tickState.set(MEDIUM_TICKS);
//        tickState.addListener((observable) -> {updateTicks();});
//        presentLine = new PresentLine(0, 0, 0, LINE_HEIGHT);
//        presentLine.timeProperty().addListener((observable) -> {
//            presentLine.setTranslateX(getXPos(presentLine.timeProperty().get()));
//        });
//        this.getChildren().add(presentLine);
//        this.getChildren().add(leftGoBackButton);
//        this.getChildren().add(rightGoBackButton);

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

    public void updateBlocks() {
        Iterator<Node> iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block) {
                Block b = (Block) node;
                int pos = getXPos(b.timeProperty().get());
                b.setTranslateX(pos);
            }
//            if (node instanceof Tick) {
//                Tick tick = (Tick) node;
//                int pos = getXPos(tick.timeProperty().get());
//                tick.setTranslateX(pos);
//            }            
        }
        
        updatePresentLine();
//        if (presentLine.timeProperty().get() < viewStartTime.get()
//            || presentLine.timeProperty().get() > viewEndTime.get()){
//            state.set(STATE_PRESENT_OUT);
//        }
//        else{
//            state.set(STATE_IDLE);
//        }
//
//        int range = viewEndTime.get() - viewStartTime.get();
//        switch (tickState.get()) {
//            case TINY_TICKS:
//                if (range < 15 * 60) {
//                    break;
//                }
//                if (range >= 15 * 60 && range <= 30 * 60) {
//                    tickState.set(MEDIUM_TICKS);
//                }
//                if (range > 30 * 60) {
//                    tickState.set(BIG_TICKS);
//                }
//            case MEDIUM_TICKS:
//                if (range < 15 * 60) {
//                    tickState.set(TINY_TICKS);
//                }
//                if (range >= 15 * 60 && range <= 30 * 60) {
//                    break;
//                }
//                if (range > 30 * 60) {
//                    tickState.set(BIG_TICKS);
//                }
//            case BIG_TICKS:
//                if (range < 15 * 60) {
//                    tickState.set(TINY_TICKS);
//                }
//                if (range >= 15 * 60 && range <= 30 * 60) {
//                    tickState.set(MEDIUM_TICKS);
//                }
//                if (range > 30 * 60) {
//                    break;
//                }
//        }

    }

//    public void updateTicks() {
//        Iterator<Node> iter = this.getChildren().iterator();
//        while (iter.hasNext()) {
//            Node node = iter.next();
//            if (node instanceof Tick) {
//                iter.remove();
//            }
//        }
//        int tickTime = totalStartTime.get();
//        while (tickTime < totalEndTime.get()) {
//            Tick tick = new Tick();
//            this.getChildren().add(tick);
//            tick.setTime(tickTime);
//            tick.setTranslateY(this.getHeight() - 42);
//            tickTime += tickState.get();
//        }
//    }
    
    public void updatePresentLine(){}
    
//    private void centerOnPresent(){
//        int totalRrange = (totalEndTime.get() - totalStartTime.get())/100;
//        int viewhalfRange = (viewEndTime.get() - viewStartTime.get())/2;
//        RangeSlider r = ((Timeline)this.getParent()).getRangeSlider();
//        if (presentLine.timeProperty().get() - viewhalfRange < totalStartTime.get()){
//            r.setLowValue(0);
//        }
//        else {
//            r.setLowValue(((presentLine.timeProperty().get() - viewhalfRange) - totalStartTime.get())/(totalRrange));
//        }
//        
//        if (presentLine.timeProperty().get() + viewhalfRange > totalEndTime.get()){
//            r.setHighValue(100);
//        }
//        else{
//            r.setHighValue(((presentLine.timeProperty().get() + viewhalfRange) - totalStartTime.get())/(totalRrange));
//        }
//    }

//    public void addBlock(Block b) {
//        int pos = getXPos(b.timeProperty().get());
//        this.getChildren().add(b);
//        b.stateProperty().bindBidirectional(state);
//        b.setTranslateX(pos);
//    }
    
    public void addBlock(Block b) {}
    
    public void setPresentLine(PresentLine pl){}

//    public List<Block> getBlocks(){
//        return this.getChildren();
//    }
    public IntegerProperty currentTimeProperty() {
        return currentTime;
    }

    public IntegerProperty viewStartProperty() {
        return viewStartTime;
    }

    public IntegerProperty viewEndProperty() {
        return viewEndTime;
    }

    public IntegerProperty totalStartTimeProperty() {
        return totalStartTime;
    }

    public IntegerProperty totalEndTimeProperty() {
        return totalEndTime;
    }
    
    public int getXPos(int t) {
        float range = (float) (viewEndTime.get() - viewStartTime.get());
        return (int) (((t - viewStartTime.get()) / range) * LINE_LENGTH);
    }

    public int getTime(int xPos) {
        int range = viewEndTime.get() - viewStartTime.get();
        return (int) ((xPos / (float) LINE_LENGTH) * range + viewStartTime.get());
    }
}
