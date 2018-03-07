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
import java.awt.geom.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import model.Plane;

public class RadarView extends Parent {

    private static final int X_START = 50;
    private final int W = 650;
    private final int H = 450;
    private final int SCALE =100;

    private static final int RADIUS_1 = 90;
    private static final int RADIUS_2 = 190;
    private static final int RADIUS_3 = 290;
    
    private static final int MIN_SPEED =180;
    private static final int MAX_SPEED =380;
    private static final double SPEED_STEP =10;
    
    private static final int MIN_LEVEL =110;
    private static final int MAX_LEVEL =400;
    private static final double LEVEL_STEP =10;

    private final double MIDDLE_X = (X_START + (W - X_START)) / 2;
    private final double MIDDLE_Y = (H - X_START + H - X_START) / 2;

    private Plane centralPlane; //l'avion sur lequel la vue est centrée
    private Map<Integer, Plane> planes;  // la collection (trafic) des avions dans la vue radar

    private Label text10, text20, text30;
    private Label centralPlaneLabel;

    private HBox hb = new HBox();
    private VBox vboxLabels = new VBox();
    private VBox vboxValues = new VBox();

    private Map<String, Label> labelsMap;
    //---axes
    private LineAxisView topAxis;
    private LineAxisView leftAxis;
    private LineAxisView rightAxis;

    public RadarView() {
        //---
        centralPlane = new Plane();
        planes = new HashMap<Integer, Plane>();
        centralPlaneLabel = new Label("centralPlaneLabel");
        labelsMap = new HashMap();

        //--- la ligne verticale
        Line line = createLine(MIDDLE_X, X_START, MIDDLE_X, MIDDLE_Y);
        //--- les arcs                
        Arc arc1 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_1, RADIUS_1);
        Arc arc2 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_2, RADIUS_2);
        Arc arc3 = createArc(MIDDLE_X, MIDDLE_Y, RADIUS_3, RADIUS_3);

        // les textes 10, 20  et 30
        text10 = createLabel("10", MIDDLE_X - (RADIUS_1 + 10), MIDDLE_Y - X_START);
        text20 = createLabel("20", MIDDLE_X - (RADIUS_2 + 10), MIDDLE_Y - (X_START + 20));
        text30 = createLabel("30", MIDDLE_X - (RADIUS_3 + 10), MIDDLE_Y - (X_START + 40));

        // label pour afficher cap , niveau et vitesse
        vboxLabels = createLabels();
        vboxValues = createTextFields();
        hb.getChildren().add(vboxLabels);
        hb.getChildren().add(vboxValues);
        
        
        //axis
        topAxis =  new LineAxisView("Cap",110, 400, 10, (int)(RADIUS_3*2-X_START));
        topAxis.relocate(X_START, MIDDLE_Y-RADIUS_3-RADIUS_1);
        //topAxis.setPrefWidth(600);
        //topAxis.getTransforms().add(new Rotate(270,0,0)); //
        
        leftAxis =  new LineAxisView("\nVitesse(Kts)",MIN_SPEED, MAX_SPEED, SPEED_STEP,(int)MIDDLE_Y);
        leftAxis.relocate(0, MIDDLE_Y);
        leftAxis.getTransforms().add(new Rotate(270,0,0)); //
        
        rightAxis =  new LineAxisView("\nNiveau",MIN_LEVEL,MAX_LEVEL, LEVEL_STEP,(int)MIDDLE_Y);
        rightAxis.relocate(MIDDLE_X+RADIUS_3+30, MIDDLE_Y);
        rightAxis.getTransforms().add(new Rotate(270,0,0)); //
        
        //leftAxis.autosize();

        //---AJOUT DU COMPOSANT POUR LA MODIFICATION DU CAP
        //Arc arc4 = new Arc();
        //animateArc(arc4);
        //this.getChildren().add(arc4);
        //Circle circle = createPlane(x_middle, y_middle,Color.YELLOW);
        //Text text4 = createText(centralPlane.getCallSign(), x_middle-10,y_middle+20);

        this.getChildren().addAll(/*topAxis,*/leftAxis,line, arc1, arc2, arc3, text10, text20, text30, hb,rightAxis);


    }

    public void addCentralPlane() {
        Circle circle = createPlane(MIDDLE_X, MIDDLE_Y, Color.YELLOW);
        Point2D.Double pos = getNdPosition(new Point2D.Double(MIDDLE_X, MIDDLE_Y), centralPlane.getHeading(), 0.0);
        VBox vb2 = createTextFields();
        hb.getChildren().remove(1);
        hb.getChildren().add(vb2);
        String str = centralPlane.getCallSign();
        centralPlaneLabel.setText(str);
        centralPlaneLabel.setTextFill(Color.WHITE);
        centralPlaneLabel.relocate(pos.x, pos.y);
        centralPlaneLabel.setGraphic(circle);
        if (!this.getChildren().contains(centralPlaneLabel)) {
            this.getChildren().add(centralPlaneLabel);
            //update labels
            updateSpeedHeadingAFl();
        }
        else{
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
                //---
                String str = p.getCallSign();
                str += "\n" + Math.toDegrees(angle);

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
                    }
                } else {//--- si le label existe , on le met à jour
                    if (getDistance(p) <= centralPlane.getMAX_DISTANCE() && isInNavigationDisplay(pos)) {
                        l.setText(str);
                        l.relocate(pos.x, pos.y);
                    } else {
                        this.getChildren().remove(l);
                    }
                }
            }
        } // != null
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
        label.relocate(x, y);
        return label;
    }

    public Arc createArc(double centerX, double centerY, int radiusX, int radiusY) {

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

    public VBox createTextFields() {
        //---
        TextField tfcap = new TextField();
        tfcap.setText("" + centralPlane.getHeading());
        tfcap.setEditable(false);
        tfcap.setPrefWidth(45);
        tfcap.setPrefHeight(10);
        //---
        TextField tfni = new TextField();
        tfni.setText("" + centralPlane.getAfl());
        tfni.setEditable(false);
        tfni.setPrefWidth(45);
        tfni.setPrefHeight(10);
        //---
        TextField tfvi = new TextField();
        tfvi.setText("" + centralPlane.getSpeed());
        tfvi.setEditable(false);
        tfvi.setPrefWidth(45);
        tfvi.setPrefHeight(10);
        //---
        VBox vb = new VBox();
        vb.getChildren().addAll(tfcap, tfni, tfvi);
        vb.setSpacing(3);
        return vb;
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
        return (-Math.PI / 2 < angle && angle < Math.PI / 2);
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

        return res;
    }

    public Point2D.Double getNdPosition(Point2D.Double center, double angleRad, double radius) {
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

    public boolean isInNavigationDisplay(Point2D.Double p) {
        double minY = MIDDLE_Y - RADIUS_3 + 10;
        double maxY = MIDDLE_Y;
        return minY < p.y && p.y < maxY;
    }

    public double getAngleRad(Plane p) {
        return Math.atan((p.getTwinklePosition().y - centralPlane.getTwinklePosition().y)
                / (p.getTwinklePosition().x - centralPlane.getTwinklePosition().x)
        );
    }

    public void updateSpeedHeadingAFl(){
        //---
        leftAxis.getLabelValue().setText(""+centralPlane.getSpeed());
        leftAxis.getAxis().setLowerBound(centralPlane.getSpeed()-SCALE);
        leftAxis.getAxis().setUpperBound(centralPlane.getSpeed()+SCALE);
        //---
        rightAxis.getLabelValue().setText(""+centralPlane.getAfl());
        rightAxis.getAxis().setLowerBound(centralPlane.getAfl()-SCALE);
        rightAxis.getAxis().setUpperBound(centralPlane.getAfl()+SCALE);
    
    }
    //---TO DO
    //Label avec une position et le rendre  cliquable
    //applique une rotation pour centrer sur la valeur selectionnée
    public void animateArc(Arc arc) {

    }

}
