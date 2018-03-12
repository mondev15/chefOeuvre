package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InformationView extends Pane {

    private Label altitude;
    private Label heading;
    private Label currentWind;
    private Label IAS;
    private Label TAS;

    private TextField altitudeText;
      private TextField headingText;
        private TextField currentWindText;
          private TextField IASText;  
          private TextField TASText;
          
    
    public InformationView() {
    
    altitude = new Label("Altitude");
    altitude.setTextFill(Color.WHITE);
    heading = new Label("heading");
    heading .setTextFill(Color.WHITE);
    currentWind = new Label("currentWind");
    currentWind .setTextFill(Color.WHITE);
    IAS = new Label("IAS");
    IAS.setTextFill(Color.WHITE);
    TAS = new Label("TAS");
    TAS.setTextFill(Color.WHITE);
    
    altitudeText = new TextField();
    headingText = new TextField();
    currentWindText = new TextField();
    IASText = new TextField();
    TASText = new TextField();
    
    
     VBox vbLabel = new VBox(22);
     vbLabel.getChildren().addAll(altitude,heading,currentWind,IAS,TAS);
     
     VBox vbText = new VBox(10);
     vbText.getChildren().addAll(altitudeText,headingText,currentWindText,IASText,TASText);     
    
        HBox hb = new HBox(10);
        hb.getChildren().addAll(vbLabel,vbText);
        getChildren().setAll(hb);
    
    }

    public Label getAltitude() {
        return altitude;
    }

    public void setAltitude(Label altitude) {
        this.altitude = altitude;
    }

    public Label getHeading() {
        return heading;
    }

    public void setHeading(Label heading) {
        this.heading = heading;
    }

    public Label getCurrentWind() {
        return currentWind;
    }

    public void setCurrentWind(Label currentWind) {
        this.currentWind = currentWind;
    }

    public Label getIAS() {
        return IAS;
    }

    public void setIAS(Label IAS) {
        this.IAS = IAS;
    }

    public Label getTAS() {
        return TAS;
    }

    public void setTAS(Label TAS) {
        this.TAS = TAS;
    }

    public TextField getAltitudeText() {
        return altitudeText;
    }

    public void setAltitudeText(TextField altitudeText) {
        this.altitudeText = altitudeText;
    }

    public TextField getHeadingText() {
        return headingText;
    }

    public void setHeadingText(TextField headingText) {
        this.headingText = headingText;
    }

    public TextField getCurrentWindText() {
        return currentWindText;
    }

    public void setCurrentWindText(TextField currentWindText) {
        this.currentWindText = currentWindText;
    }

    public TextField getIASText() {
        return IASText;
    }

    public void setIASText(TextField IASText) {
        this.IASText = IASText;
    }

    public TextField getTASText() {
        return TASText;
    }

    public void setTASText(TextField TASText) {
        this.TASText = TASText;
    }
    
    
   

}
