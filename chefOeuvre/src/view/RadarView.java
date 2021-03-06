package view;

import ivy.IvyManager;
import java.awt.Point;
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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import model.Plane;

/**
 * Creates the ND-like view, with a central ND view, two lists : Speed and
 * FL (more PFD-like) and heading.
 * 
 * @author Evergiste
 */

// Could be improved :
// Add a vertical speed info
// Fix the display of nearby planes

public class RadarView extends Parent {

    private static final int X_START = 50;

    static void sendNewSpeedOrLevelToRadar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private final int W = 650;
    private final int H = 450;

    private static final int RADIUS_1 = 90;
    private static final int RADIUS_2 = 190;
    private static final int RADIUS_3 = 290;
    private static final int RADIUS_4 = 390;

    private static final int ANGLE_180 = 180;
    private static final int ANGLE_90 = 90;

    private static final int SPEED_SCALE = 100;
    private static final int LEVEL_SCALE = 200;

    private static final int SPEED_STEP = 20;
    private static final int LEVEL_STEP = 20;

    private static final int MIN_SPEED = 180;
    private static final int MAX_SPEED = 380;

    private static final int MIN_LEVEL = 110;
    private static final int MAX_LEVEL = 400;

    //private static final int HEADING_SCALE= 45;
    private final double MIDDLE_X = (X_START + (W - X_START + 50)) / 2;
    private final double MIDDLE_Y = (H - X_START + H - X_START + 50) / 2;

    private static Plane centralPlane; //l'avion sur lequel la vue est centrée
    private List<Label> addedPlanes ;// liste des avions ajoutés au radar view
    private Map<Integer, Plane> planes;  // la collection (trafic) des avions dans la vue radar

    private Label text10, text20, text30;
    private Label centralPlaneLabel;

    private VBox vboxLabels = new VBox();
    private VBox vboxValues = new VBox();

    private Map<String, Label> labelsMap;
    //---axes
    private CircleAxisView topAxis;
    private static LineAxisView leftAxis;
    private static LineAxisView rightAxis;
    
    private boolean centralPlaneExists = false;

    public RadarView() {
        //---
        centralPlane = new Plane();
        planes = new HashMap<Integer, Plane>();
        addedPlanes = new ArrayList<Label>();
        centralPlaneLabel = new Label("centralPlaneLabel");
        labelsMap = new HashMap();

        //--- la ligne verticale
        Line line = createLine(MIDDLE_X, 30, MIDDLE_X, MIDDLE_Y);
        //--- les arcs                
        Arc arc1 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_1, RADIUS_1, 0, ANGLE_180);
        Arc arc2 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_2, RADIUS_2, 0, ANGLE_180);
        Arc arc3 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_3, RADIUS_3, 0, ANGLE_180);

        // les textes 10, 20  et 30
        text10 = createLabel("10", MIDDLE_X - (RADIUS_1 + 10), MIDDLE_Y - X_START);
        text20 = createLabel("20", MIDDLE_X - (RADIUS_2 + 10), MIDDLE_Y - (X_START +20));
        text30 = createLabel("30", MIDDLE_X - (RADIUS_3 + 10), MIDDLE_Y - (X_START +40));

        createCentralPlane();
        topAxis = new CircleAxisView("Heading", new Point((int) MIDDLE_X, (int) MIDDLE_Y), RADIUS_4, 45, ANGLE_90);

        leftAxis = new LineAxisView("\nSpeed(Kts)", MIN_SPEED, MAX_SPEED, SPEED_STEP, (int) MIDDLE_Y, SPEED_SCALE);
        leftAxis.relocate(0, MIDDLE_Y);
        leftAxis.getTransforms().add(new Rotate(270, 0, 0)); //

        rightAxis = new LineAxisView("\nLevel", MIN_LEVEL, MAX_LEVEL, LEVEL_STEP, (int) MIDDLE_Y, LEVEL_SCALE);
        rightAxis.relocate(MIDDLE_X + RADIUS_3 + 30, MIDDLE_Y);
        rightAxis.getTransforms().add(new Rotate(270, 0, 0)); //

        this.getChildren().addAll(topAxis, leftAxis, line, arc1, arc2, arc3, text10, text20, text30, rightAxis);

    }

    public void createCentralPlane() {
        Line l1 = new Line(MIDDLE_X - 20, MIDDLE_Y, MIDDLE_X + 20, MIDDLE_Y); //ligne  horizontale haute
        l1.setStroke(Color.YELLOW);
        l1.setStrokeWidth(2);
                   l1.setStrokeWidth(3.0);
        Line l2 = new Line(MIDDLE_X - 10, MIDDLE_Y + 25, MIDDLE_X + 10, MIDDLE_Y + 25); //ligne horizontale basse
        l2.setStroke(Color.YELLOW);
        l2.setStrokeWidth(2);
         l2.setStrokeWidth(3.0);
        Line l3 = new Line(MIDDLE_X, MIDDLE_Y - 5, MIDDLE_X, MIDDLE_Y + 30); //ligne verticale milieu
        l3.setStroke(Color.YELLOW);
        l3.setStrokeWidth(2);
         l3.setStrokeWidth(3.0);
        this.getChildren().addAll(l1, l2, l3);
    }

    public void addCentralPlane() {
        Point2D.Double pos = getNdPosition(new Point2D.Double(MIDDLE_X, MIDDLE_Y), centralPlane.getHeading(), 0.0);
        String str = "\n" + centralPlane.getCallSign()+", "+centralPlane.getFlight();
        //str += "\n" + centralPlane.getHeading();
        //str += "\n" + centralPlane.getSpeed() + ", " + centralPlane.getAfl();
        centralPlaneLabel.setText(str);
        centralPlaneLabel.setTextFill(Color.WHITE);
        centralPlaneLabel.relocate(pos.x - 20, pos.y + 30);
        if (!this.getChildren().contains(centralPlaneLabel)) {
            this.getChildren().add(centralPlaneLabel);
            centralPlaneExists = true;
            updateSpeedHeadingAFl();
        } else {
            updateSpeedHeadingAFl();
        }
    }

    public void addPlane(Plane p) {
        if (centralPlane.getTwinklePosition() != null) {

            //on vérifie s'il est différent ce central plane
            if ((!p.getCallSign().equals(centralPlane.getCallSign())) && isInRange(p)) {
                Label l = labelsMap.get(p.getCallSign());
                double angle = normalize(getAngleRad(p) + Math.toRadians(centralPlane.getHeading()));
                double dist = getDistance(p) * 10;
                Point2D.Double pos = getNdPosition(new Point2D.Double(MIDDLE_X, MIDDLE_Y), angle, dist);
                p.setNdPosition(pos);
                //---
                String str = p.getCallSign();
                //str += "\n" + Math.toDegrees(angle);

                //--- si le label n'existe pas déjà,on le crée                
                if (l == null) {
                    Label label = new Label(p.getCallSign());
                    Circle circle = createPlane(pos.x, pos.y, Color.GREEN);
                    label.setGraphic(circle);
                    label.setText(str);
                    label.setTextFill(Color.WHITE);
                    label.relocate(pos.x, pos.y);
                    labelsMap.put(p.getCallSign(), label);
                    if (getDistance(p) <= centralPlane.getMAX_DISTANCE() && isInNavigationDisplay(pos)) {
                        this.getChildren().addAll(label);
                        addedPlanes.add(label);
                    }
                } else {//--- si le label existe , on le met à jour
                    if (getDistance(p) <= centralPlane.getMAX_DISTANCE() && isInNavigationDisplay(pos)) {
                        l.setText(str);
                        l.relocate(pos.x, pos.y);
                    } else {
                        this.getChildren().remove(l);
                        addedPlanes.remove(l);
                    }
                }
            }
        } // != null
    }

    public void removeAllPlanes(){
      for (Label l : addedPlanes){
       this.getChildren().remove(l);
      }
    
    }
    
    public Circle createPlane(double centerX, double centerY, Color color) {
        Circle circle = new Circle();
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(5.0f);
        circle.setFill(color);
        return circle;
    }

    public Label createLabel(String txt, double x, double y) {

        Label label = new Label(txt);
        label.setText(txt);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(11));
        label.relocate(x, y);
        return label;
    }

    public Arc createArc(double centerX, double centerY, int radiusX, int radiusY, int start, int angle) {

        Arc arc = new Arc();
        //---
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radiusX);
        arc.setRadiusY(radiusY);
        arc.setStartAngle(start);
        arc.setLength(angle);
        arc.setFill(Color.TRANSPARENT);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.DARKGRAY);
        arc.setStrokeWidth(0.6);
        arc.setSmooth(true); //anti-aliasing

        return arc;
    }

    public Text createText(String t, double xPos, double yPos) {
        Text text = new Text(t);
        text.setFont(new Font(10));
        text.setStroke(Color.DARKGRAY);
        text.relocate(xPos, yPos);
        return text;
    }

    public Line createLine(double startX, double startY, double endX, double endY) {
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

    public VBox createLabels() {
        Label hd = new Label();
        hd.setText("Heading : ");
        hd.setTextFill(Color.WHITE);
        Label afl = new Label();
        afl.setText("afl     : ");
        afl.setTextFill(Color.WHITE);
        Label speed = new Label();
        speed.setText("speed    : ");
        speed.setTextFill(Color.WHITE);
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hd, afl, speed);
        vb1.setSpacing(15);
        return vb1;
    }

    public Plane getCentralPlane() {
        return centralPlane;
    }

    public void setCentralPlane(Plane plane) {
        this.centralPlane = plane;
    }

    public Map<Integer, Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(Map<Integer, Plane> planes) {
        this.planes = planes;
    }

    public boolean isInRange(Plane p) {
        double angle = normalize(Math.toRadians(centralPlane.getHeading()) + getAngleRad(p));
        return (0 < angle && angle < Math.PI);
    }

    public double normalize(double angle) {
        return Math.atan2(Math.sin(angle), Math.cos(angle));
    }

    public double getDistance(Plane p) {

        Point2D.Double p1 = centralPlane.getTwinklePosition();
        Point2D.Double p2 = p.getTwinklePosition();

        double res = (Math.sqrt(
                Math.pow(p1.x - p2.x, 2.0)
                + Math.pow(p1.y - p2.y, 2.0)
        ));
        p.setDistance(res);
        return res;
    }

    public static Point2D.Double getNdPosition(Point2D.Double center, double angleRad, double radius) {
        double x;
        double y;
        if (angleRad > 0) {
            x = (center.getX() + radius * Math.cos(angleRad));
            y = (center.getY() - radius * Math.sin(angleRad));
        } else {
            x = (center.getX() - radius * Math.sin(angleRad));
            y = (center.getY() + radius * Math.cos(angleRad));
        }

        return new Point2D.Double(x, y);
    }
    
    public static Point2D.Double getTopAxisNdPosition(Point2D.Double center, double angle, double radius) {
        double x =(center.getX() + radius * Math.cos(angle));
        double y =(center.getY() - radius * Math.sin(angle));
        return new Point2D.Double(x, y);
    }

    public boolean isInNavigationDisplay(Point2D.Double p) {
        double minY = MIDDLE_Y - RADIUS_3;
        double maxY = MIDDLE_Y;
        return minY < p.y && p.y < maxY;
    }

    public double getAngleRad(Plane p) {
        
        double angle = Math.atan((p.getTwinklePosition().y - centralPlane.getTwinklePosition().y)
                / (p.getTwinklePosition().x - centralPlane.getTwinklePosition().x)
        );
            return angle;
    }

    public void updateSpeedHeadingAFl() {
        //---
        Point2D.Double pos = getNdPosition(new Point2D.Double(MIDDLE_X, MIDDLE_Y), centralPlane.getHeading(), 0.0);
        String str="";
        str = "\n" + centralPlane.getCallSign()+", "+centralPlane.getFlight();
        //str += "\n" + centralPlane.getHeading();
        //str += "\n" + centralPlane.getSpeed() + ", " + centralPlane.getAfl();
        centralPlaneLabel.setText(str);

        
        leftAxis.getLabelValue().setText("" + centralPlane.getSpeed());
        leftAxis.getAxis().setLowerBound(centralPlane.getSpeed() - SPEED_SCALE);
        leftAxis.getAxis().setUpperBound(centralPlane.getSpeed() + SPEED_SCALE);
        //---
        rightAxis.getLabelValue().setText("" + centralPlane.getAfl());
        rightAxis.getAxis().setLowerBound(centralPlane.getAfl() - LEVEL_SCALE);
        rightAxis.getAxis().setUpperBound(centralPlane.getAfl() + LEVEL_SCALE);
        //
        topAxis.getLabelValue().setText("" + centralPlane.getHeading());
        topAxis.getLinesLabel().get(0).setText("" + (centralPlane.getHeading() + 30));
        topAxis.getLinesLabel().get(1).setText("" + (centralPlane.getHeading() + 20));
        topAxis.getLinesLabel().get(2).setText("" + (centralPlane.getHeading() + 10));
        topAxis.getLinesLabel().get(3).setText("" + (centralPlane.getHeading()));
        topAxis.getLinesLabel().get(4).setText("" + (centralPlane.getHeading() - 10));
        topAxis.getLinesLabel().get(5).setText("" + (centralPlane.getHeading() - 20));
        topAxis.getLinesLabel().get(6).setText("" + (centralPlane.getHeading() - 30));
    }

    public boolean isCentralPlaneExists() {
        return centralPlaneExists;
    }

    public void setCentralPlaneExists(boolean centralPlaneExists) {
        this.centralPlaneExists = centralPlaneExists;
    }  

    public CircleAxisView getTopAxis() {
        return topAxis;
    }

    public LineAxisView getLeftAxis() {
        return leftAxis;
    }

    public LineAxisView getRightAxis() {
        return rightAxis;
    }
    
    public static void sendNewHeadingToIvy(double newHeading) {
        IvyManager.setNewHeading(newHeading);
    }
    
  public static void sendNewSpeedToIvy(double newSpeed) {
        IvyManager.setNewSpeed(newSpeed);
    }
        
    public static void sendNewLevelToIvy(double newLevel) {
        IvyManager.setNewLevel(newLevel);
    }
}
