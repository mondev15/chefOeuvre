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
    private double currentValue;
    private Point mousePosition;
    private Polygon polygon;
    private Label labelValue;
    private static final int SCALE =100;

    public LineAxisView(String text, double low, double upper, double step,int width) {
        //---
        min = low;
        max= upper;        
        step = step;
        label = new Label();
        label.setTextFill(Color.WHITE);
        label.setText(text);
        label.setTextFill(Color.GREEN);
        label.getTransforms().add(new Rotate(90, 0, 0));
        labelValue = new Label("value");
        labelValue.setText("value");
        labelValue.setTextFill(Color.WHITE);
        labelValue.getTransforms().add(new Rotate(90, 0, 0));
        labelValue.relocate(210,35);
        
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
        axis.setOnMouseClicked(mouseHandler);
        axis.setOnMouseDragged(mouseHandler);
        axis.setOnMouseEntered(mouseHandler);
        axis.setOnMouseExited(mouseHandler);
        axis.setOnMouseMoved(mouseHandler);
        axis.setOnMousePressed(mouseHandler);
        axis.setOnMouseReleased(mouseHandler);
        
        
        //axis.setLayoutX(width/2);
        //axis.setLayoutY(height/2);

        getChildren().setAll(labelValue,axis,label,polygon);

    }
    
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                currentValue = (double) axis.getValueForDisplay(mouseEvent.getX());
                mousePosition = new Point((int) mouseEvent.getX(),(int) mouseEvent.getY());
                System.out.println("cursor Value "+mousePosition);
                System.out.println("current Value "+currentValue);
                
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED 
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
            }
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
                 currentValue = (double) axis.getValueForDisplay(mouseEvent.getX());
                 axis.setLowerBound(currentValue-SCALE);
                 labelValue.setText(""+ (int)currentValue);
                 axis.setUpperBound(currentValue+SCALE);
            }
        }
    };

        //à modifier par rapport à la position du milieu
    public Polygon createPolygon(){
        Polygon p = new Polygon();
        /*p.getPoints().addAll(new Double[]{
            150.0, 20.0,
            160.0, 30.0,
            140.0, 30.0 });        
        p.setFill(Color.YELLOW);
        */
       
        p.getPoints().addAll(new Double[]{
            200.0, 22.0,
            210.0, 32.0,
            190.0, 32.0 });        
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
