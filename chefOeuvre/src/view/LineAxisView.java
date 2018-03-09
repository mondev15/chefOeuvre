package view;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class LineAxisView extends Pane {

    private NumberAxis axis;
    private double min; //valeur min
    private double max; //valeur max
    private double step;
    private Label label;
    private int currentValue;
    private Point mousePosition;
    private Polygon polygon;
    private Label labelValue;
    private  int scale;

    public LineAxisView(String text, double lower, double upper, double stp,int width, int s) {
        //---
        scale = s;
        min = lower;
        max= upper;        
        step = stp;
        label = new Label();
        label.setTextFill(Color.WHITE);
        label.setText(text);
        label.setTextFill(Color.GREEN);
        label.getTransforms().add(new Rotate(90, 0, 0));
        labelValue = new Label("value");
        labelValue.setText("value");
        labelValue.setTextFill(Color.WHITE);
        labelValue.getTransforms().add(new Rotate(90, 0, 0));
        labelValue.relocate(220,35);
        
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        //setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        axis = new NumberAxis(min, max, step);
        axis.setSide(Side.BOTTOM);
        axis.setMinorTickVisible(false);
        axis.setPrefWidth(width);
        
        //--central value
        polygon = createPolygon();

         //line
        //--events
        axis.setOnMousePressed(mouseHandler);
        //axis.setOnMouseClicked(mouseHandler);
        //axis.setOnMouseDragged(mouseHandler);
        //axis.setOnMouseEntered(mouseHandler);
        //axis.setOnMouseExited(mouseHandler);
        //axis.setOnMouseMoved(mouseHandler);
        //axis.setOnMousePressed(mouseHandler);
        axis.setOnMouseReleased(mouseHandler);
        
        
        //axis.setLayoutX(width/2);
        //axis.setLayoutY(height/2);

        getChildren().setAll(labelValue,axis,label,polygon);

    }
    
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                currentValue =  (int) ((double)axis.getValueForDisplay(mouseEvent.getX()));
                mousePosition = new Point((int) mouseEvent.getX(),(int) mouseEvent.getY());
                System.out.println("cursor Value "+mousePosition);
                System.out.println("current Value "+currentValue);
                
            } /*else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED 
                    || mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {

                double newMin = axis.getLowerBound(); 
                double newMax = axis.getUpperBound();
                double Delta = 10; 

                if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    if (mousePosition.x < mouseEvent.getX()) {
                        newMin = axis.getLowerBound() - Delta;
                        newMax = axis.getUpperBound() - Delta;
                    } else if (mousePosition.x > mouseEvent.getX()) {
                        newMin = axis.getLowerBound() + Delta;
                        newMax = axis.getUpperBound() + Delta;
                    }
                    axis.setLowerBound(newMin);
                    axis.setUpperBound(newMax);
                }
                 mousePosition = new Point((int)mouseEvent.getX(),(int)mouseEvent.getY());
                 currentValue = (double) axis.getValueForDisplay(mouseEvent.getX());
            }*/
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
                 currentValue = (int)((double) axis.getValueForDisplay(mouseEvent.getX()));
                 axis.setLowerBound(currentValue-scale);
                 labelValue.setText(""+ (int)currentValue);
                 axis.setUpperBound(currentValue+scale);
                 System.out.println("lower : "+axis.getLowerBound());
                 System.out.println("upper : "+axis.getUpperBound());
            }
        }
    };

        //à modifier par rapport à la position du milieu
    public Polygon createPolygon(){
        Polygon p = new Polygon();
       
        p.getPoints().addAll(new Double[]{
            212.0, 22.0,
            222.0, 32.0,
            202.0, 32.0 });        
        p.setFill(Color.YELLOW);
        
        return p;
    }

    public Label getLabel() {
        return label;
    }

    public Label getLabelValue() {
        return labelValue;
    }
    
    
    
    public NumberAxis getAxis() {
        return axis;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}