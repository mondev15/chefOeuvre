package view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import com.sun.javafx.geom.Point2D;
import model.Plane;
import model.Position;

public class RadarView extends Parent {

    private static final int X_START = 50;
    private final int X_SCENE = 650;
    private final int Y_SCENE = 450;
    private float x_middle;
    private float y_middle;

    private Plane plane; //l'avion sur lequel la vue est centrée
    private Map<Integer, Plane> planes;  // la collection (trafic) des avions dans la vue radar

    private Label planeLabel;
    private Label text10, text20, text30;

    public RadarView() {
        //---
        plane = new Plane();
        planes = new HashMap<Integer, Plane>();
        planeLabel = new Label("planeLabel");
        //---
        x_middle = (X_START + (X_SCENE - X_START)) / 2.f;
        y_middle = (Y_SCENE - X_START + Y_SCENE - X_START) / 2.f;

        //--- la ligne verticale
        Line line = createLine(x_middle, X_START, x_middle, y_middle);
        //--- les arcs                
        Arc arc1 = createArc(x_middle, y_middle, 100, 100);
        Arc arc2 = createArc(x_middle, y_middle, 200, 200);
        Arc arc3 = createArc(x_middle, y_middle, 300, 300);

        // les textes 10, 20  et 30
        text10 = createLabel("10", x_middle - 110, y_middle - 50);
        text20 = createLabel("20", x_middle - 210, y_middle - 70);
        text30 = createLabel("30", x_middle - 310, y_middle - 90);

        //---ajout des lignes et arc au groupe
        //Arc arc4 = new Arc();
        //animateArc(arc4);
        //this.getChildren().add(arc4);
        //Circle circle = createPlane(x_middle, y_middle,Color.YELLOW);
        //Text text4 = createText(plane.getCallSign(), x_middle-10,y_middle+20);
        this.getChildren().addAll(line, arc1, arc2, arc3, text10, text20, text30);

    }

    public void addCentralPlane() {
        Circle circle = createPlane(x_middle, y_middle, Color.YELLOW);
        String str = plane.getCallSign() + "\t" + plane.getPosition().getPos().x + " , " + plane.getPosition().getPos().y;
        str += "\ncap :" + plane.getHeading() + " , niveau :" + plane.getAfl();
        planeLabel.setText(str);
        planeLabel.setTextFill(Color.WHITE);
        planeLabel.relocate(x_middle - 70, y_middle + 20);
        if (!this.getChildren().contains(planeLabel)) {
            this.getChildren().addAll(circle, planeLabel);
        }
    }

    public void addPlane(Plane p) {
        Label label = new Label();
        p.calculateNewPosition();
        Point2D pos = p.getNewPosition().getPos();
        Circle circle = createPlane(pos.x-20, pos.y+20, Color.GREEN);
        String str = p.getCallSign();
        label.setText(str);
        label.setTextFill(Color.WHITE);
        label.relocate(pos.x - 20, pos.y + 20);
        if (getDistance(p) < 200) {
            this.getChildren().addAll(circle, label);
        }
    }

    public Circle createPlane(float centerX, float centerY, Color color) {
        Circle circle = new Circle();
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(7.0f);
        circle.setFill(color);
        return circle;
    }

    public Label createLabel(String txt, float x, float y) {

        Label label = new Label(txt);
        label.setText(txt);
        label.setTextFill(Color.WHITE);
        label.relocate(x, y);
        return label;
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

    //--- calcule la distance d'un avion par rapport à l'avion central
    public float getDistance(Plane p) {

        Position p1 = plane.getPosition();
        Position p2 = p.getPosition();

            return (float) Math.sqrt(Math.pow(p1.getPos().x - p2.getPos().x, 2.0)
                + Math.pow(p1.getPos().y - p2.getPos().y, 2.0)
        );
    }

    //---TO DO
    //Label avec une position et le rendre  cliquable
    //applique une rotation pour centrer sur la valeur selectionnée
    public void animateArc(Arc arc) {

    }

}
