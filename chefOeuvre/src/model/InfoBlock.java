/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import view.SingleLine;

/**
 *
 * @author Charlelie
 */
public class InfoBlock extends Group implements IBlock{
    
    private IntegerProperty time;
    private Label titleLabel;
    private Label hdgLabel;
    private Label flLabel;
    private Label speedLabel;
    private Label infoLabel;
    private List<sun.font.Decoration.Label> listInfosLabels;
    private VBox content;
    private SimpleStringProperty state = new SimpleStringProperty();
    private InfoBlockSkin forwardBlock;
    private Color BACKGROUND_COLOR = Color.rgb(229, 229, 229);
    private Color DRAGGED_COLOR = Color.rgb(166, 165, 165);
    private Color SHADOW_COLOR = Color.rgb(48, 50, 51);
    private DropShadow shadow;

    
    private final int SIZE = 180;
    
    public InfoBlock(int t, String title, String hdg, String fl, String speed, String info, List<String> listInfos){
        content = new VBox();
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        content.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        content.setCursor(Cursor.HAND);
        
        shadow = new DropShadow(5.0, 3.0, 3.0, SHADOW_COLOR);
        content.setEffect(shadow);
        
        time = new SimpleIntegerProperty(t);
        titleLabel = new Label(title);
        hdgLabel = new Label(hdg);
        flLabel = new Label(fl);
        speedLabel = new Label(speed);
        infoLabel = new Label(info);
        
        listInfosLabels = new ArrayList<>();
        for (int i = 0; i < listInfos.size(); i++) {
        	listInfosLabels.add(new Label(listInfos.get(i)));
        }
        
        content.getChildren().add(titleLabel);
        content.getChildren().add(hdgLabel);
        content.getChildren().add(flLabel);
        content.getChildren().add(speedLabel);
        content.getChildren().add(infoLabel);
        
        for (int j = 0; j < listInfosLabels.size(); j++) {
        	content.getChildren().add(listInfosLabels.get(j));
        }
        
        time.addListener((observable) -> {
            setTranslateX(((SingleLine)getParent()).getXPos(time.get()));
        });

        state.addListener((Observable observable) -> {
            if("IDLE".equals(state.get())){
                content.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if("DRAG".equals(state.get())){
                content.setBackground(new Background(new BackgroundFill(DRAGGED_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        forwardBlock = new InfoBlockSkin(this);

        this.getChildren().addAll(content, forwardBlock);
    }
    
    public InfoBlock(){
        this(0, "", "", "", "", "", null);
    }
    
    public InfoBlock(int t){
        this(t, "", "", "", "", "", null);
    }
    
    public InfoBlock(int t, String title){
        this(t, title, "", "", "", "", null);
    }
    
    public InfoBlock(int t, String title, String hdg){
        this(t, title, hdg, "", "", "", null);
    }

    public InfoBlock(int t, String title, String hdg, String fl){
        this(t, title, hdg, fl, "", "", null);
    }
    
    public InfoBlock(int t, String title, String hdg, String fl, String speed){
        this(t, title, hdg, fl, speed, "", null);
    }
    
    public InfoBlock(int t, String title, String hdg, String fl, String speed, List<String> listInfos){
        this(t, title, hdg, fl, speed, "", listInfos);
    }

    public SimpleStringProperty stateProperty(){
        return state;
    }
    
    public void setTime(int s){
        time.set(s);
    }
    
    public void setTitle(String s){
        titleLabel.setText(s);
    }
    
    public void setHDG(String s){
        hdgLabel.setText(s);
    }
    
    public void setFL(String s){
        flLabel.setText(s);
    }
    
    public void setSpeed(String s){
        speedLabel.setText(s);
    }
    
    public void setInfo(String s){
        infoLabel.setText(s);
    }
    
    public void setListInfos(int index, String s) {
    	listInfosLabels.set(index, new Label(s));
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    public Label getTitle(){
        return titleLabel;
    }
    
    public Label getHDG(){
        return hdgLabel;
    }
    
    public Label getFL(){
        return flLabel;
    }
    
    public Label getSpeed(){
        return speedLabel;
    }
    
    public Label getInfo(){
        return infoLabel;
    }
    
    public List<Label> getListInfos() {
    	return listInfosLabels;
    }
    
    public Background getBackground(){
        return content.getBackground();
    }
}
