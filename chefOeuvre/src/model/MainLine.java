/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Iterator;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.controlsfx.control.RangeSlider;
import view.SingleLine;
import view.Timeline;

/**
 *
 * @author Charlelie
 */
public class MainLine extends SingleLine {

    private Button leftGoBackButton;
    private Button rightGoBackButton;
    private StringProperty state = new SimpleStringProperty();
    private IntegerProperty tickState = new SimpleIntegerProperty();
    private final int TINY_TICKS = 60;
    private final int MEDIUM_TICKS = 300;
    private final int BIG_TICKS = 600;
    private PresentLine presentLine;
    public MainLine() {
        this(1012, 150);
    }

    public MainLine(int w, int h) {
        super(w, h);

        rightGoBackButton = new Button(">>");
        rightGoBackButton.setTranslateY(LINE_HEIGHT - 40);
        rightGoBackButton.setTranslateX(LINE_LENGTH - 80);
        leftGoBackButton = new Button("<<");
        leftGoBackButton.setTranslateY(LINE_HEIGHT - 40);
        leftGoBackButton.setTranslateX(40);

        rightGoBackButton.setOnAction((e) -> {
            centerOnPresent();
        });
        leftGoBackButton.setOnAction((e) -> {
            centerOnPresent();
        });

        state.addListener((Observable observable) -> {
            String currentState = state.get();
            switch (currentState) {
                case STATE_IDLE:
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
                    rightGoBackButton.setVisible(false);
                    leftGoBackButton.setVisible(false);
                    break;
                case STATE_DRAG:
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(140, 140, 140), CornerRadii.EMPTY, Insets.EMPTY)));
                    rightGoBackButton.setVisible(false);
                    leftGoBackButton.setVisible(false);
                    break;
                case STATE_PRESENT_OUT:
                    if (presentLine.timeProperty().get() <= viewStartTime.get()) {
                        rightGoBackButton.setVisible(false);
                        leftGoBackButton.setVisible(true);
                    } else if (presentLine.timeProperty().get() >= viewEndTime.get()) {
                        rightGoBackButton.setVisible(true);
                        leftGoBackButton.setVisible(false);
                    }
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
        tickState.set(MEDIUM_TICKS);
        tickState.addListener((observable) -> {
            updateTicks();
        });

        presentLine = new PresentLine(0, 0, 0, LINE_HEIGHT);
        presentLine.timeProperty().addListener((observable) -> {
            presentLine.setTranslateX(getXPos(presentLine.timeProperty().get()));
        });
        
        this.getChildren().add(presentLine);
        this.getChildren().add(leftGoBackButton);
        this.getChildren().add(rightGoBackButton);
    }

    private void centerOnPresent() {
        int totalRrange = (totalEndTime.get() - totalStartTime.get()) / 100;
        int viewhalfRange = (viewEndTime.get() - viewStartTime.get()) / 2;
        RangeSlider r = ((Timeline) this.getParent()).getRangeSlider();
        if (presentLine.timeProperty().get() - viewhalfRange < totalStartTime.get()) {
            r.setLowValue(0);
        } else {
            r.setLowValue(((presentLine.timeProperty().get() - viewhalfRange) - totalStartTime.get()) / (totalRrange));
        }

        if (presentLine.timeProperty().get() + viewhalfRange > totalEndTime.get()) {
            r.setHighValue(100);
        } else {
            r.setHighValue(((presentLine.timeProperty().get() + viewhalfRange) - totalStartTime.get()) / (totalRrange));
        }
    }

    @Override
    public void updateBlocks() {
        Iterator<Node> iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block) {
                Block b = (Block) node;
                int pos = getXPos(b.timeProperty().get());
                b.setTranslateX(pos);
            }
            if (node instanceof Tick) {
                Tick tick = (Tick) node;
                int pos = getXPos(tick.timeProperty().get());
                tick.setTranslateX(pos);
            }
        }

        updatePresentLine();
        if (presentLine.timeProperty().get() < viewStartTime.get()
                || presentLine.timeProperty().get() > viewEndTime.get()) {
            state.set(STATE_PRESENT_OUT);
        } else {
            state.set(STATE_IDLE);
        }

        int range = viewEndTime.get() - viewStartTime.get();
        switch (tickState.get()) {
            case TINY_TICKS:
                if (range < 15 * 60) {
                    break;
                }
                if (range >= 15 * 60 && range <= 30 * 60) {
                    tickState.set(MEDIUM_TICKS);
                }
                if (range > 30 * 60) {
                    tickState.set(BIG_TICKS);
                }
            case MEDIUM_TICKS:
                if (range < 15 * 60) {
                    tickState.set(TINY_TICKS);
                }
                if (range >= 15 * 60 && range <= 30 * 60) {
                    break;
                }
                if (range > 30 * 60) {
                    tickState.set(BIG_TICKS);
                }
            case BIG_TICKS:
                if (range < 15 * 60) {
                    tickState.set(TINY_TICKS);
                }
                if (range >= 15 * 60 && range <= 30 * 60) {
                    tickState.set(MEDIUM_TICKS);
                }
                if (range > 30 * 60) {
                    break;
                }
        }
    }
    
    @Override
    public void setPresentLine(PresentLine pl){
        presentLine = pl;
        getChildren().add(presentLine);
    }
    
    @Override
    public void updatePresentLine(){
        int pos = getXPos(presentLine.timeProperty().get());
        presentLine.setTranslateX(pos);
    }
    
    public void updateTicks() {
        Iterator<Node> iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Tick) {
                iter.remove();
            }
        }
        int tickTime = totalStartTime.get();
        while (tickTime < totalEndTime.get()) {
            Tick tick = new Tick();
            this.getChildren().add(tick);
            tick.setTime(tickTime);
            tick.setTranslateY(this.getHeight() - 42);
            tickTime += tickState.get();
        }
    }
    
    @Override
    public void addBlock(Block b) {
        int pos = getXPos(b.timeProperty().get());
        this.getChildren().add(b);
        b.stateProperty().bindBidirectional(state);
        b.setTranslateX(pos);
    }
}
