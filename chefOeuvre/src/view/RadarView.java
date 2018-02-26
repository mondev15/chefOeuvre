package view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Plane;
import model.Position;

public class RadarView extends Parent{

    private static final int X_START = 50;
    private final int X_SCENE = 650;
    private final int Y_SCENE = 450;

    private Plane plane; //l'avion sur lequel la vue est centrée
    private Map<Integer,Plane> planes;  // la collection (trafic) des avions dans la vue radar
    
    public RadarView() {
        //---
        plane = new Plane();
        planes = new HashMap<Integer,Plane>();
        //---
        float x_middle = (X_START + (X_SCENE - X_START)) / 2.f;
        float y_middle = (Y_SCENE - X_START + Y_SCENE - X_START) / 2.f;

        //--- la ligne verticale
        Line line = createLine(x_middle, X_START, x_middle, y_middle);
        //--- les arcs                
        Arc arc1 = createArc(x_middle, y_middle, 100, 100);
        Arc arc2 = createArc(x_middle, y_middle, 200, 200);
        Arc arc3 = createArc(x_middle, y_middle, 300, 300);

        // les textes 10, 20  et 30
        Text text1 = createText("10", x_middle - 110, y_middle - 50);
        Text text2 = createText("20", x_middle - 210, y_middle - 70);
        Text text3 = createText("30", x_middle - 310, y_middle - 90);

        //---ajout des lignes et arc au groupe
        //Arc arc4 = new Arc();
        //animateArc(arc4);
        //this.getChildren().add(arc4);
        Circle circle = createPlane(x_middle, y_middle,Color.YELLOW);
        Text text4 = createText(plane.getCallSign(), x_middle-10,y_middle+20);
     
        this.getChildren().addAll(line, arc1, arc2, arc3, text1, text2, text3, circle,text4);
        /*for (Map.Entry<Integer,Plane> e : planesList.getPlanes().entrySet()){
            Plane p = e.getValue();
            Circle c = createPlane(Math.abs((p.getPosition().getX())/2), Math.abs((p.getPosition().getX())/2),Color.GREEN);
            //Text text = createText(e.getValue().getCallSign(), x_middle-10,y_middle+20);
            //System.out.println(e.getKey() + " : " + e.getValue());
            this.getChildren().add(c);
        }*/
        updateView();
    }
    
    //méthode pour la mise à jour de la vue
    //à appeller dans track moved, mettre à jour la position du plane s'il existe pas sinon le créer
    public void updateView(){
        Circle circle = createPlane(200, 200,Color.YELLOW);
        this.getChildren().add(circle);
    }

    public Circle createPlane(float centerX, float centerY,Color color) {
        Circle circle = new Circle();
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(7.0f);
        circle.setFill(color);
        //circle.setStroke(javafx.scene.paint.Color.GREEN);
        return circle;
    }

    public Arc createArc(float centerX, float centerY, int radiusX, int radiusY) {

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

    public Text createText(String t, float xPos, float yPos) {
        Text text = new Text(t);
        text.setFont(new Font(10));
        text.setStroke(Color.DARKGRAY);
        text.relocate(xPos, yPos);
        return text;
    }

    public Line createLine(float startX, float startY, float endX, float endY) {
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

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Map<Integer, Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(Map<Integer, Plane> planes) {
        this.planes = planes;
    }


    public float distance(Plane p){
     
       Position p1 = plane.getPosition();
       Position p2 = p.getPosition();
       
       return (float) Math.sqrt(Math.pow(p1.getX()-p2.getX(),2.0) +
                    Math.pow(p1.getY()-p2.getY(),2.0)
       );
    }
    
    
    //---TO DO
    //Text avec une position et le rendre  cliquable
    //applique une rotation pour centrer sur la valeur selectionnée
    public void animateArc(Arc arc) {

    }  
    

}
