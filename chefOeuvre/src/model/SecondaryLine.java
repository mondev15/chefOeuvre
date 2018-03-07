/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class SecondaryLine extends SingleLine {

    private StringProperty state = new SimpleStringProperty();
    private PresentLine presentLine;

    public SecondaryLine() {
        this(1012, 150);
    }

    public SecondaryLine(int w, int h) {
        super(w, h);

        state.addListener((Observable observable) -> {
            String currentState = state.get();
            switch (currentState) {
                case STATE_IDLE:
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(100, 0, 100), CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case STATE_DRAG:
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(140, 0, 140), CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case STATE_PRESENT_OUT:
                    break;
            }
        });
        
        currentTime.addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    presentLine.timeProperty().set(currentTime.get());
                    updateBlocks();
                }
            });
        });

        state.set(STATE_IDLE);
    }

    @Override
    public void addBlock(Block b) {
        int pos = getXPos(b.timeProperty().get());
        this.getChildren().add(b);
        b.stateProperty().bindBidirectional(state);
        b.setTranslateX(pos);
    }

    @Override
    public void setPresentLine(PresentLine pl) {
        presentLine = pl;
        //getChildren().add(presentLine);
    }
    
    @Override
    public void updatePresentLine(){
        int pos = getXPos(presentLine.timeProperty().get());
        presentLine.setTranslateX(pos);
    }
}
