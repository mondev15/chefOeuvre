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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Plane;

public class RadarView extends Parent {

    private static final int X_START = 50;
    private final int W = 650;
    private final int H = 450;

    private final  double MIDDLE_X= (X_START + (W - X_START)) / 2;
    private final double MIDDLE_Y= (H - X_START + H - X_START) / 2;

    private Plane centralPlane; //l'avion sur lequel la vue est centrée
    private Map<Integer, Plane> planes;  // la collection (trafic) des avions dans la vue radar

    private Label planeLabel;
    private Label text10, text20, text30;

    private HBox hb = new HBox();
    private VBox vboxLabels = new VBox();
    private VBox vboxValues = new VBox();

    private Map<String, Label> labelsMap;

    public RadarView() {
        //---
        centralPlane = new Plane();
        planes = new HashMap<Integer, Plane>();
        planeLabel = new Label("planeLabel");
        labelsMap = new HashMap();


        //--- la ligne verticale
        Line line = createLine(MIDDLE_X, X_START, MIDDLE_X, MIDDLE_Y);
        //--- les arcs                
        Arc arc1 = createArc(MIDDLE_X, MIDDLE_Y, 100, 100);
        Arc arc2 = createArc(MIDDLE_X, MIDDLE_Y, 200, 200);
        Arc arc3 = createArc(MIDDLE_X, MIDDLE_Y, 300, 300);

        // les textes 10, 20  et 30
        text10 = createLabel("10", MIDDLE_X - 110, MIDDLE_Y - 50);
        text20 = createLabel("20", MIDDLE_X - 210, MIDDLE_Y - 70);
        text30 = createLabel("30", MIDDLE_X - 310, MIDDLE_Y - 90);

        // label pour afficher cap , niveau et vitesse
        vboxLabels = createLabels();
        vboxValues = createTextFields();
        hb.getChildren().add(vboxLabels);
        hb.getChildren().add(vboxValues);

        //---AJOUT DU COMPOSANT POUR LA MODIFICATION DU CAP
        //Arc arc4 = new Arc();
        //animateArc(arc4);
        //this.getChildren().add(arc4);
        //Circle circle = createPlane(x_middle, y_middle,Color.YELLOW);
        //Text text4 = createText(centralPlane.getCallSign(), x_middle-10,y_middle+20);
        //--- AJOUT DU COMPOSANT POUR LA MODIFICATION DU NIVEAU
        //---//AJOUT DU COMPOSANT POUR LA MODIFICATION DE LA VITESSE
        this.getChildren().addAll(line, arc1, arc2, arc3, text10, text20, text30, hb);

    }

    public void addCentralPlane() {
        Circle circle = createPlane(MIDDLE_X, MIDDLE_Y, Color.YELLOW);
        String str = centralPlane.getCallSign() + "\n" + centralPlane.getTwinklePosition().x + " , " + centralPlane.getTwinklePosition().y;
        centralPlane.setNdPosition(new Point2D.Double(MIDDLE_X, MIDDLE_Y));
        VBox vb2 = createTextFields();
        hb.getChildren().remove(1);
        hb.getChildren().add(vb2);
        planeLabel.setText(str);
        planeLabel.setTextFill(Color.WHITE);
        planeLabel.relocate(MIDDLE_X, MIDDLE_Y);
        planeLabel.setGraphic(circle);
        if (!this.getChildren().contains(planeLabel)) {
            this.getChildren().add(planeLabel);
        }
    }

    public void addPlane(Plane p) {
        if (centralPlane.getTwinklePosition() != null) {
            if (!p.getCallSign().equals(centralPlane.getCallSign())) {

                Label l  = labelsMap.get(p.getCallSign());
                calculateNdPosition(p);
                Point2D.Double pos = p.getNdPosition();
                Tooltip tp  = new Tooltip();
                //--- si le label n'existe pas déjà                
                if (l == null) {
                    Label label = new Label(p.getCallSign());
                    Circle circle = createPlane(pos.x - 20, pos.y + 20, Color.GREEN);
                    label.setGraphic(circle);
                    String str = p.getCallSign();
                    tp.setText(" "+pos.x+" , "+pos.y+"\n heading: "+p.getHeading()+"\n afl: "+p.getAfl()+"\n afl: "+p.getAfl());
                    label.setText(str);
                    label.setTooltip(tp);
                    label.setTextFill(Color.WHITE);
                    label.relocate(pos.x, pos.y);
                    labelsMap.put(p.getCallSign(), label);
                    if (getDistance(p) <= centralPlane.getMAX_DISTANCE()) {
                            this.getChildren().add(label);
                    }
                } 
                else {//--- si le label existe
                      String str = p.getCallSign();
                      tp.setText(" "+pos.x+" , "+pos.y+"\nafl: "+p.getAfl());
                      l.setText(str);
                      l.setTooltip(tp);
                      l.relocate(pos.x, pos.y);
                }

            }
        }
    }

    public Circle createPlane(double centerX, double centerY, Color color) {
        Circle circle = new Circle();
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(7.0f);
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

    //----calcule la distance d'un avion par rapport à l'avion central
    //----les position X et Y sont en NM , la distance est donc en NM
    public double getDistance(Plane p) {

        Point2D.Double p1 = centralPlane.getTwinklePosition();
        Point2D.Double p2 = p.getTwinklePosition();

        return (Math.sqrt(
                Math.pow(p1.x - p2.x, 2.0)
                + Math.pow(p1.y - p2.y, 2.0)
        ));
    }

    //--- conversion lon, lat to xy
    public void calculateNdPosition(Plane p) {
        if (centralPlane.getNdPosition() != null) {
            double posX = (p.getTwinklePosition().x * centralPlane.getNdPosition().x) / centralPlane.getTwinklePosition().x;
            double posY = (p.getTwinklePosition().y * centralPlane.getNdPosition().y) / centralPlane.getTwinklePosition().y;
            p.setNdPosition(new Point2D.Double(posX, posY));
        }

    }

    //---TO DO
    //Label avec une position et le rendre  cliquable
    //applique une rotation pour centrer sur la valeur selectionnée
    public void animateArc(Arc arc) {

    }

}
