//JavaFX view

package view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Plane;

public class RadarView extends Parent {

    private static final int X_START =50;
    private final int X_SCENE =650;
    private final int Y_SCENE =450;
    
    private Map<Integer,Plane> planesMap; // la collection des avions dans la vue radar
    private Plane plane; // l'avion sur lequel la vue est centrée
    
    public RadarView() {

        //---
        planesMap = new HashMap<Integer, Plane>();
        //---
        float x_middle = (X_START+(X_SCENE-X_START))/2.f;
        float y_middle = (Y_SCENE-X_START+Y_SCENE-X_START)/2.f;

        //--- la ligne verticale
        Line line = createLine(x_middle,X_START,x_middle,y_middle);
        
        //--- les arcs                
        Arc arc1 = createArc(x_middle, y_middle,100,100);
        Arc arc2 = createArc(x_middle, y_middle,200,200);
        Arc arc3 = createArc(x_middle, y_middle,300,300);
        
        /*Arc arc = new Arc();       
        arc.setCenterX(x_middle);
        arc.setCenterY(y_middle);
        arc.setRadiusX(350);
        arc.setRadiusY(350);
        arc.setStartAngle(45.0f);
        arc.setLength(100.0f);
        arc.setFill(Color.TRANSPARENT);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.DARKGRAY);
        arc.setStrokeWidth(0.6);
        arc.setSmooth(true); //anti-aliasing
        */
        // les textes 10, 20  et 30
        Text text1 = createText("10",x_middle-110,y_middle-50);
        Text text2 = createText("20",x_middle-210,y_middle-70);
        Text text3 = createText("30",x_middle-310,y_middle-90);
        
        //---ajout des lignes et arc au groupe
        //Arc arc4 = new Arc();
        //animateArc(arc4);
        //this.getChildren().add(arc4);
        this.getChildren().addAll(line, arc1, arc2, arc3,text1, text2, text3);
    }

    public Map<Integer, Plane> getPlanesMap() {
        return planesMap;
    }
    
    public Arc createArc(float centerX, float centerY, int radiusX, int radiusY){
     
        Arc arc = new Arc();
        //---
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radiusX);
        arc.setRadiusY(radiusY);
        arc.setStartAngle(0.0f);
        arc.setLength(180.0f);
        arc.setFill(Color.TRANSPARENT);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.DARKGRAY);
        arc.setStrokeWidth(0.6);
        arc.setSmooth(true); //anti-aliasing
        
        return arc;
    }

    public Text createText(String t, float xPos, float yPos){
     Text text = new Text(t);     
     text.setFont(new Font (10));
     text.setStroke(Color.DARKGRAY);
     text.relocate(xPos,yPos);
     return text;
    }
    
    public Line createLine(float startX, float startY, float endX, float endY){
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStroke(Color.DARKGRAY);
        line.setStrokeWidth(0.4);
        line.setSmooth(true);
        return line; 
    }
    
    //---TO DO
    //Text avec une position et le rendre  cliquable
    //applique une rotation pour centrer sur la valeur selectionnée
    public void animateArc(Arc arc){
    
    
    }

    //---TO DO
    public void drawPlane(){
    
    }
}
