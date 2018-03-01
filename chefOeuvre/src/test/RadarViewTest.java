package test;

import ivy.IvyManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class RadarViewTest extends Application {

    public static void main(String[] args) {
        Application.launch(RadarViewTest.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Centered Plane View");
        primaryStage.setResizable(false);
        Group root = new Group();
        IvyManager ivyManager = new IvyManager(); 
        
        root.getChildren().add(ivyManager.getRadarView());
        //try {
            //Runtime rt = Runtime.getRuntime();
            //rt.exec("ivymon");
            //rt.exec("rejeu /usr/lib/rejeu/1_heure_Bordeaux.txt");
            //rt.exec("twinkle2013");
        //} catch (IOException ex) {
        //    Logger.getLogger(RadarViewTest.class.getName()).log(Level.SEVERE, null, ex);
        //}
       
        Scene scene = new Scene(root, 650, 470, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}