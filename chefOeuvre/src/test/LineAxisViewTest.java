package test;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;
import view.LineAxisView;

/**
 * Test class, not used in final run
 * 
 * @author Evergiste
 */
public class LineAxisViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        LineAxisView axes = new LineAxisView("label",10,300, 10,300,200);
        
        axes.getTransforms().add(new Rotate(-90,0,0));

        StackPane layout = new StackPane(
                axes
        );
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");

        stage.setTitle("Axis");
        stage.setScene(new Scene(layout, Color.GREEN));
        stage.show();
    }


}