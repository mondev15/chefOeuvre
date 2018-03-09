package view;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class CircleAxisView extends Pane {

    private Arc arc;
    //private int startAngle;
    //private int angleValue;
    //private double step;
    private Label label;
    private int currentValue;
    private Point mousePosition;
    private Polygon polygon;
    private Label labelValue;
    private int currentLabel;
    private List<Label> linesLabel = new ArrayList<Label>();
    
    private Label label0,label1,label2,label3,label4,label5,label6;
    
    private static final int MAX_LINES = 7;
    private static final int STEP = 15;
    //private  int scale;

    public CircleAxisView(String text, Point center, int radius, int startAngle, int angleValue) {
        //min = lower;
        //max = upper;
        //step = stp;
        //scale = s;
        polygon = createPolygon();

        //---arc
        arc = createArc(center, radius, radius, startAngle, angleValue);
        int endAngle = startAngle + angleValue;
        createLines(new Point2D.Double(center.x, center.y), startAngle, endAngle, radius);

        //--- label
        label = new Label();
        label.setTextFill(Color.WHITE);
        label.setText(text);
        label.setTextFill(Color.GREEN);
        Point2D.Double p = RadarView.getNdPosition(new Point2D.Double(center.x,center.y), Math.toRadians(135), radius+40);
        label.relocate(p.x,p.y);
        //---labelValue
        labelValue = new Label("value");
        labelValue.setText("value");
        labelValue.setTextFill(Color.WHITE);
        //labelValue.getTransforms().add(new Rotate(90, 0, 0));
        labelValue.relocate(340, 0);
        //---events
        //arc.setOnMousePressed(mouseHandler);
        //arc.setOnMouseReleased(mouseHandler);
        
        for(Label l : linesLabel){
          l.setOnMousePressed(mouseHandler);
          l.setOnMouseReleased(mouseHandler);
        }

        getChildren().setAll(labelValue, arc,label0,label1,label2,label3,label4,label5,label6,label, polygon);

    }

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                
                //switch(currentLabel){
                    //case 10 : //i=6
                        labelValue.setText(linesLabel.get(6).getText());
                      /*  break;
                        case 20 : //i=5*/
                            labelValue.setText(linesLabel.get(5).getText());
                        /*break;
                        case 30 : //i=4
                            labelValue.setText(linesLabel.get(4).getText());
                        break;
                        case 40 : //i=3
                            labelValue.setText(linesLabel.get(3).getText());
                        break;
                        case 50 : //i=2
                            labelValue.setText(linesLabel.get(2).getText());
                        break;
                        case 60 : //i=1
                            labelValue.setText(linesLabel.get(1).getText());
                        break;
                        case 70 : //i=0
                            labelValue.setText(linesLabel.get(0).getText());
                        break;
                */
                //}       
                
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {

            }
        }
    };

    /*
    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
     */
    public Arc createArc(Point center, int radiusX, int radiusY, int start, int angle) {

        Arc arc = new Arc();
        //---
        arc.setCenterX(center.x);
        arc.setCenterY(center.y);
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

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public Point getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Point mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Label getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(Label labelValue) {
        this.labelValue = labelValue;
    }

    /*
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
     */
    public void createLines(Point2D.Double center, int startAngle, int endAngle, int radius) {
        for (int i = 0; i <MAX_LINES; i++) {
                Point2D.Double p = RadarView.getNdPosition(center, Math.toRadians(startAngle+(STEP*i)), radius);
                Line line = new Line(p.x, p.y-10, p.x, p.y);
                line.setStroke(Color.WHITE);
                Label label = new Label();
                label.setGraphic(line);
                label.setText(""+(10*(MAX_LINES-i)));
                label.setFont(new Font(10));                
                label.setTextFill(Color.GREY);
                label.relocate(p.x,p.y-12);
                switch(i){
                    case 0:
                        label0 = label;
                        linesLabel.add(label0);
                        break;
                    case 1:
                        label1 = label;
                        linesLabel.add(label1);
                        break;
                    case 2:
                        label2 = label;
                        linesLabel.add(label2);
                        break;
                    case 3:
                        label3 = label;
                        linesLabel.add(label3);
                        break;
                    case 4:
                        label4 = label;
                        linesLabel.add(label4);
                        break;
                    case 5:
                        label5 = label;
                        linesLabel.add(label5);
                        break;
                    case 6:
                        label6 = label;
                        linesLabel.add(label6);
                        break;
                    
                }

                //label.getTransforms().add(new Rotate());
                //linesLabel.add(label);
        }

    }

    public Polygon createPolygon() {
        Polygon p = new Polygon();

        p.getPoints().addAll(new Double[]{
            360.0, 15.0,
            350.0, 25.0,
            340.0, 15.0,});
        p.setFill(Color.YELLOW);
        return p;
    }

    public List<Label> getLinesLabel() {
        return linesLabel;
    }
    
    
}
