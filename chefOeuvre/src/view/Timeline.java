/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import model.IBlock;
import model.InfoBlock;
import model.MainLine;
import model.PresentLine;
import model.SecondaryLine;
import org.controlsfx.control.RangeSlider;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import model.CompactBlock;

/**
 *
 * @author Charlelie
 */
public class Timeline extends Pane {

    Ivy bus;

    private int LINE_HEIGHT = 150;
    private int SECONDARY_LINE_HEIGHT = 50;
    protected final String STATE_IDLE = "IDLE";
    protected final String STATE_DRAG = "DRAG";
    protected final String STATE_PRESENT_OUT = "OUT";

    private VBox lines;
    private MainLine mainLine;
    private SingleLine secondaryLine;
    private RangeSlider rangeSlider;
    private IntegerProperty totalStartTime;
    private IntegerProperty totalEndTime;
    private PresentLine presentLine;
    private PresentLine leftPresentLineSkin;
    private PresentLine rightPresentLineSkin;
    private StringProperty state = new SimpleStringProperty();

    public Timeline() {
        bus = new Ivy("Timeline", "Timeline CONNECTED", null);
        try {
            bus.start("127.255.255.255:2010");
        } catch (IvyException e) {
            e.printStackTrace();
        }

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int w = (int) primaryScreenBounds.getWidth();
        lines = new VBox();
        totalStartTime = new SimpleIntegerProperty();
        totalEndTime = new SimpleIntegerProperty();
        presentLine = new PresentLine(0, 0, 0, LINE_HEIGHT + SECONDARY_LINE_HEIGHT);
        leftPresentLineSkin = new PresentLine(0, SECONDARY_LINE_HEIGHT / 2, 0, LINE_HEIGHT + SECONDARY_LINE_HEIGHT / 2);
        leftPresentLineSkin.setOpacity(0.2);
        leftPresentLineSkin.setTranslateX(15);
        rightPresentLineSkin = new PresentLine(0, SECONDARY_LINE_HEIGHT / 2, 0, LINE_HEIGHT + SECONDARY_LINE_HEIGHT / 2);
        rightPresentLineSkin.setOpacity(0.2);
        rightPresentLineSkin.setTranslateX(w - 15);
        rightPresentLineSkin.setVisible(false);
        leftPresentLineSkin.setVisible(false);
        setSecondaryLine(new SecondaryLine(w, SECONDARY_LINE_HEIGHT));
        setMainLine(new MainLine(w, LINE_HEIGHT));
        setRangeSlider(new RangeSlider(0, 100, 0, 24));

        this.getChildren().addAll(lines, presentLine, leftPresentLineSkin, rightPresentLineSkin);

        state.bindBidirectional(mainLine.stateProperty());

        rightPresentLineSkin.timeProperty().addListener((observable) -> {
            presentLine.timeProperty().set(rightPresentLineSkin.timeProperty().get());
            rightPresentLineSkin.setTranslateX(w - 15);
        });

        leftPresentLineSkin.timeProperty().addListener((observable) -> {
            presentLine.timeProperty().set(leftPresentLineSkin.timeProperty().get());
            leftPresentLineSkin.setTranslateX(15);
        });

        presentLine.timeProperty().addListener((observable) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updatePresentLine();
                    mainLine.updateBlocks();
                    secondaryLine.updateBlocks();
                    for (Node node : mainLine.getChildren()) {
                        if (node instanceof InfoBlock) {
                            InfoBlock block = (InfoBlock) node;
                            System.out.println(block.isRead());
                            if (block.timeProperty().get() - 5 < presentLine.timeProperty().get()
                                    && presentLine.timeProperty().get() < block.timeProperty().get() + 10
                                    && !block.isRead()) {
                                System.out.println(block.getMessageIvy());
                                try {
                                    bus.sendMsg(block.getMessageIvy());
                                    block.setIsRead(true);
                                } catch (IvyException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
//                                block.setIsRead(false);
                            }
                        }
                    }
                    for (Node node : secondaryLine.getChildren()) {
                        if (node instanceof CompactBlock) {
                            CompactBlock block = (CompactBlock) node;
                            if (block.timeProperty().get() - 5 < presentLine.timeProperty().get()
                                    && presentLine.timeProperty().get() < block.timeProperty().get() + 10) {
                            	System.out.println(block.getMessageIvy());
                                try {
                                    bus.sendMsg(block.getMessageIvy());
                                    block.setIsRead(true);
                                } catch (IvyException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
        });

        state.addListener((observable) -> {
            switch (state.get()) {
                case STATE_PRESENT_OUT:
                    if (presentLine.timeProperty().get() <= mainLine.viewStartProperty().get()) {
                        mainLine.getRightGoBackButton().setVisible(false);
                        rightPresentLineSkin.setVisible(false);
                        mainLine.getLeftGoBackButton().setVisible(true);
                        leftPresentLineSkin.setVisible(true);
                    } else if (presentLine.timeProperty().get() >= mainLine.viewEndProperty().get()) {
                        mainLine.getRightGoBackButton().setVisible(true);
                        rightPresentLineSkin.setVisible(true);
                        mainLine.getLeftGoBackButton().setVisible(false);
                        leftPresentLineSkin.setVisible(false);
                    }
                    break;
                case STATE_IDLE:
                    rightPresentLineSkin.setVisible(false);
                    leftPresentLineSkin.setVisible(false);
                    break;
                case STATE_DRAG:
                    rightPresentLineSkin.setVisible(false);
                    leftPresentLineSkin.setVisible(false);
                    break;
            }

        });

        totalStartTime.addListener((observable) -> {
            rangeSlider.setLowValue(1);
            rangeSlider.setLowValue(0);
            presentLine.timeProperty().set(totalStartTime.get() + 100);
        });

        totalEndTime.addListener((observable) -> {
            rangeSlider.setHighValue(26);
            rangeSlider.setHighValue(25);
        });

        rangeSlider.lowValueProperty().addListener((observable) -> {
            int range = (totalEndTime.get() - totalStartTime.get()) / 100;
            mainLine.viewStartProperty().set((int) (rangeSlider.lowValueProperty().get() * range) + totalStartTime.get());
            secondaryLine.viewStartProperty().set((int) (rangeSlider.lowValueProperty().get() * range) + totalStartTime.get());
            updatePresentLine();
        });

        rangeSlider.highValueProperty().addListener((observable) -> {
            int range = (totalEndTime.get() - totalStartTime.get()) / 100;
            mainLine.viewEndProperty().set((int) (rangeSlider.highValueProperty().get() * range) + totalStartTime.get());
            secondaryLine.viewEndProperty().set((int) (rangeSlider.highValueProperty().get() * range) + totalStartTime.get());
            updatePresentLine();
        });

        bindTime();
        totalEndTime.set(10000);
        totalStartTime.set(0);
        presentLine.timeProperty().set(100);
        rangeSlider.setHighValue(25);

    }

    public SingleLine getMainLine() {
        return mainLine;
    }

    public SingleLine getSecondaryLine() {
        return secondaryLine;
    }

    public void bindTime() {
        mainLine.currentTimeProperty().bind(presentLine.timeProperty());
        mainLine.totalStartTimeProperty().bind(totalStartTime);
        mainLine.totalEndTimeProperty().bind(totalEndTime);
        secondaryLine.currentTimeProperty().bind(presentLine.timeProperty());
        secondaryLine.totalStartTimeProperty().bind(totalStartTime);
        secondaryLine.totalEndTimeProperty().bind(totalEndTime);
    }

    public void setMainLine(SingleLine line) {
        mainLine = (MainLine) line;
        lines.getChildren().add(mainLine);
    }

    public void setSecondaryLine(SingleLine line) {
        secondaryLine = line;
        lines.getChildren().add(secondaryLine);
    }

    public void setRangeSlider(RangeSlider rSlider) {
        rangeSlider = rSlider;
        lines.getChildren().add(rangeSlider);
    }

    public void setCurrentTime(int time) {
        presentLine.timeProperty().set(time);
    }

    public void setClockTime(int time, int rate) {
        double range = (totalEndTime.get() - totalStartTime.get()) / 100.0;
        double step = rate / range;
        presentLine.timeProperty().set(time);
        rangeSlider.setLowValue(rangeSlider.getLowValue() + step);
        rangeSlider.setHighValue(rangeSlider.getHighValue() + step);
    }

    public IntegerProperty totalStartTimeProperty() {
        return totalStartTime;
    }

    public IntegerProperty totalEndTimeProperty() {
        return totalEndTime;
    }

    public StringProperty stateProperty() {
        return state;
    }

    public RangeSlider getRangeSlider() {
        return rangeSlider;
    }

    public PresentLine getPresentLine() {
        return presentLine;
    }

    public void centerOnPresent() {
        int totalRrange = (totalEndTime.get() - totalStartTime.get()) / 100;
        int viewhalfRange = (mainLine.viewEndTime.get() - mainLine.viewStartTime.get()) / 2;
        if (presentLine.timeProperty().get() - viewhalfRange < totalStartTime.get()) {
            rangeSlider.setLowValue(0);
        } else {
            rangeSlider.setLowValue(((presentLine.timeProperty().get() - viewhalfRange) - totalStartTime.get()) / (totalRrange));
        }

        if (presentLine.timeProperty().get() + viewhalfRange > totalEndTime.get()) {
            rangeSlider.setHighValue(100);
        } else {
            rangeSlider.setHighValue(((presentLine.timeProperty().get() + viewhalfRange) - totalStartTime.get()) / (totalRrange));
        }
    }

    public void updatePresentLine() {
        int pos = mainLine.getXPos(presentLine.timeProperty().get());
        presentLine.setTranslateX(pos);
        if (presentLine.timeProperty().get() < mainLine.viewStartProperty().get()
                || presentLine.timeProperty().get() > mainLine.viewEndProperty().get()) {
            state.set(STATE_PRESENT_OUT);
        } else {
            state.set(STATE_IDLE);
        }
    }

}
