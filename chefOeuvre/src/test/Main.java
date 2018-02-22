package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.RadarView;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Centered Plane View");
        Group root = new Group();
        RadarView radar = new RadarView();
        root.getChildren().add(radar);
        Scene scene = new Scene(root, 650, 450, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}