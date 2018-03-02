
package view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Plane;

public class PlaneView{

private Plane p;
private Label label;
    
    public PlaneView(Plane p, float x, float y) {
     label = new Label("plane");
     label.setText(p.getCallSign() +"\n"+x+"\n"+y);
    }

    public Plane getP() {
        return p;
    }

    public void setP(Plane p) {
        this.p = p;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
    


}
