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
    private final int VERTICAL_PADDING = 10;
    private final Color BACKGROUND_COLOR = Color.rgb(64, 64, 64);
    private final Color DRAGGED_COLOR = Color.rgb(102, 92, 92);

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
            ((Timeline) this.getParent().getParent()).centerOnPresent();
        });
        leftGoBackButton.setOnAction((e) -> {
            ((Timeline) this.getParent().getParent()).centerOnPresent();
        });

        state.addListener((Observable observable) -> {
            String currentState = state.get();
            switch (currentState) {
                case STATE_IDLE:
                    this.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                    rightGoBackButton.setVisible(false);
                    leftGoBackButton.setVisible(false);
                    break;
                case STATE_DRAG:
                    this.setBackground(new Background(new BackgroundFill(DRAGGED_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                    rightGoBackButton.setVisible(false);
                    leftGoBackButton.setVisible(false);
                    break;
                case STATE_PRESENT_OUT: //Managed by the Timeline
                    break;
            }
        });

        state.set(STATE_IDLE);

        tickState.set(MEDIUM_TICKS);
        tickState.addListener((observable) -> {
            updateTicks();
        });

        this.getChildren().add(leftGoBackButton);
        this.getChildren().add(rightGoBackButton);
    }

    @Override
    public void updateBlocks() {
        Iterator<Node> iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof InfoBlock) {
                InfoBlock b = (InfoBlock) node;
                int pos = getXPos(b.timeProperty().get());
                b.setTranslateX(pos);
            }
            if (node instanceof Tick) {
                Tick tick = (Tick) node;
                int pos = getXPos(tick.timeProperty().get());
                tick.setTranslateX(pos);
            }
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
            tick.setTranslateY(this.getHeight() - 36);
            tickTime += tickState.get();
        }
    }

    public Button getRightGoBackButton() {
        return rightGoBackButton;
    }

    public Button getLeftGoBackButton() {
        return leftGoBackButton;
    }

    public StringProperty stateProperty() {
        return state;
    }

    @Override
    public void addBlock(IBlock ib) {
        InfoBlock b = (InfoBlock) ib;
        int pos = getXPos(b.timeProperty().get());
        this.getChildren().add(b);
        b.stateProperty().bindBidirectional(state);
        b.setTranslateX(pos);
        b.setTranslateY(VERTICAL_PADDING);
    }
}
