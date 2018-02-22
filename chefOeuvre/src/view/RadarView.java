//JavaFX view

package view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import model.Plane;

public class RadarView extends Parent {

    private static final int X_START =50;
    private final int X_SCENE =650;
    private final int Y_SCENE =450;
    
    private Map<Integer,Plane> planeMap; // la collection des avions dans la vue radar
    private Plane plane; // l'avion sur lequel la vue est centrée
    
    public RadarView() {

        planeMap = new HashMap<Integer, Plane>();
        //---
        float x_middle = (X_START+(X_SCENE-X_START))/2.f;
        float y_middle = (Y_SCENE-X_START+Y_SCENE-X_START)/2.f;

        //--- vertical line 
        Line vLine = new Line();
        vLine.setStartX(x_middle);
        vLine.setStartY(X_START);
        vLine.setEndX(x_middle);
        vLine.setEndY(y_middle);
        vLine.setStroke(Color.DARKGRAY);
        vLine.setStrokeWidth(0.4);
        
        //--arc 1                
        Arc arc1 = new Arc();
        arc1.setCenterX(x_middle);
        arc1.setCenterY(y_middle);
        arc1.setRadiusX(100);
        arc1.setRadiusY(100);
        arc1.setStartAngle(0.0f);
        arc1.setLength(180.0f);
        arc1.setType(ArcType.OPEN); // ROUND  si on veut faire des arcs fermés
        arc1.setStroke(Color.DARKGRAY);
        arc1.setStrokeWidth(0.6);
        
        //--arc 2
        Arc arc2 = new Arc();
        arc2.setCenterX(x_middle);
        arc2.setCenterY(y_middle);
        arc2.setRadiusX(200);
        arc2.setRadiusY(200);
        arc2.setStartAngle(0.0f);
        arc2.setLength(180.0f);
        arc2.setType(ArcType.OPEN);
        arc2.setStroke(Color.DARKGRAY);
        arc2.setStrokeWidth(0.6);
        
        //--arc 3
        Arc arc3 = new Arc();
        arc3.setCenterX(x_middle);
        arc3.setCenterY(y_middle);
        arc3.setRadiusX(300);
        arc3.setRadiusY(300);
        arc3.setStartAngle(0.0f);
        arc3.setLength(180.0f);
        arc3.setType(ArcType.OPEN);
        arc3.setStroke(Color.DARKGRAY);
        arc3.setStrokeWidth(0.6);
        
        //---ajout des lignes et arc au groupe
        this.getChildren().addAll(arc3,arc2,arc1,vLine);
    }

    public Map<Integer, Plane> getPlaneMap() {
        return planeMap;
    }       
}
