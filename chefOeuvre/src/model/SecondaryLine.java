/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Iterator;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import view.SingleLine;
import view.Timeline;

/**
 * Top row of the timeline, recieving CompactBlocks
 * Updates the position of its own blocks
 * 
 * @author Charlelie
 */
public class SecondaryLine extends SingleLine {

    private StringProperty state = new SimpleStringProperty();
    private DropShadow shadow;
    private final int VERTICAL_PADDING = 5;
    private final Color BACKGROUND_COLOR = Color.rgb(71, 78, 102);
    private final Color DRAGGED_COLOR = Color.rgb(98, 108, 140);
    private final Color SHADOW_COLOR = Color.rgb(48, 49, 51);

    public SecondaryLine() {
        this(1012, 150);
    }

    public SecondaryLine(int w, int h) {
        super(w, h);
        
        shadow = new DropShadow(10, 0, 3, SHADOW_COLOR);
        setEffect(shadow);
                
        state.addListener((Observable observable) -> {
            String currentState = state.get();
            switch (currentState) {
                case STATE_IDLE:
                    this.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case STATE_DRAG:
                    this.setBackground(new Background(new BackgroundFill(DRAGGED_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case STATE_PRESENT_OUT:
                    break;
            }
        });
        
        currentTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ((Timeline) getParent().getParent()).getPresentLine().timeProperty().set(currentTime.get());
                    updateBlocks();
                }
            });
        });

        state.set(STATE_IDLE);
    }
    
    @Override
    public void updateBlocks(){
        Iterator<Node> iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof CompactBlock) {
                CompactBlock b = (CompactBlock) node;
                int pos = getXPos(b.timeProperty().get());
                int width = getXPos(b.timeProperty().get() + b.durationProperty().get()) - pos;
                b.setWidth(width);
                b.setTranslateX(pos);
            }
        }
    }
    
    @Override
    public void addBlock(IBlock ib) {
        CompactBlock b = (CompactBlock) ib;
        int pos = getXPos(b.timeProperty().get());
        this.getChildren().add(b);
        b.stateProperty().bindBidirectional(state);
        b.setTranslateX(pos);
        b.setTranslateY(VERTICAL_PADDING);
    }
}
