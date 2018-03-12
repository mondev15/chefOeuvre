package test;

import ivy.IvyManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RadarViewTest extends Application {

    private  static final int W = 800;
    private static final int H = 520;
    
    public static void main(String[] args) {
        Application.launch(RadarViewTest.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Centered Plane View");
        //primaryStage.setResizable(false);
        Group root = new Group();
        IvyManager ivyManager = new IvyManager();
        //NumberAxis axis = new NumberAxis(10, 1000, 10);
        //axis.relocate(150, 150);
        //axis.getTransforms().add(new Rotate(90,0,0)); //        
        root.getChildren().addAll(ivyManager.getRadarView());
        try {
            Runtime rt = Runtime.getRuntime();
            //kill process
            rt.exec(" pkill ivymon");
            rt.exec(" pkill rejeu");
            rt.exec(" pkill TWINKLE2013");
            //run process
            rt.exec(" ivymon");
            rt.exec(" rejeu");
            rt.exec(" twinkle2013");
        } catch (IOException ex) {
            Logger.getLogger(RadarViewTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        Scene scene = new Scene(root, W,H,Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}