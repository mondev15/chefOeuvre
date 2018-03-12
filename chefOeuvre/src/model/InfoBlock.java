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
    private List<Label> listInfosLabels;
    private String messageIvy;
    
    private VBox content;
    private SimpleStringProperty state = new SimpleStringProperty();
    private InfoBlockSkin forwardBlock;
    private Color BACKGROUND_COLOR = Color.rgb(229, 229, 229);
    private Color DRAGGED_COLOR = Color.rgb(166, 165, 165);
    private Color SHADOW_COLOR = Color.rgb(48, 50, 51);
    private DropShadow shadow;
    private boolean isread = false;

    
    private final int SIZE = 180;
    
    public InfoBlock(int t, String title, List<String> listInfos, String messageIvy){
        content = new VBox();
        this.prefWidth(SIZE);
        this.prefHeight(SIZE);
        content.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        content.setCursor(Cursor.HAND);
        
        shadow = new DropShadow(5.0, 3.0, 3.0, SHADOW_COLOR);
        content.setEffect(shadow);
        
        time = new SimpleIntegerProperty(t);
        titleLabel = new Label(title);
        
        listInfosLabels = new ArrayList<>();
        for (int i = 0; i < listInfos.size(); i++) {
        	listInfosLabels.add(new Label(listInfos.get(i)));
        }
        
        messageIvy = "";
        
        content.getChildren().add(titleLabel);
        
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
        this(0, "", null, "");
    }
    
    public InfoBlock(int t){
        this(t, "", null, "");
    }
    
    public InfoBlock(int t, String title){
        this(t, title, null, "");
    }
    
    public InfoBlock(int t, String title, List<String> listInfo){
        this(t, title, listInfo, "");
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
    
    public void setListInfos(int index, String s) {
    	listInfosLabels.set(index, new Label(s));
    }
    
    public void setMessageIvy(String s) {
    	messageIvy = s;
    }
    
    public IntegerProperty timeProperty(){
        return time;
    }
    
    public Label getTitle(){
        return titleLabel;
    }
    
    public List<Label> getListInfos() {
    	return listInfosLabels;
    }
    
    public String getMessageIvy() {
    	return messageIvy;
    }
    
    public Background getBackground(){
        return content.getBackground();
    }
    
    public void setIsRead(boolean b){
        isread = b;
    }
    
    public boolean isRead(){
        return isread;
    }
}
