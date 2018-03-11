package test;

import controller.Parser;
import ivy.IvyManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.InfoBlock;
import model.CompactBlock;


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
        
        Parser parser = new Parser();
        
//        root.getChildren().add(ivyManager.getRadarView());
        
//        InfoBlock blockTest1 = new InfoBlock(4950,
//           " APPROCHE                    ",
//           " 180 kts",
//           " Left 260°",
//           " ILS 14D",
//           " CALL BACK");
//        
//        InfoBlock blockTest2 = new InfoBlock(3500,
//           " APPROCHE                    ",
//           " 180 kts",
//           " Left 260°",
//           " ILS 14D",
//           " CALL BACK");
        
        CompactBlock blockTest3 = new CompactBlock(6000, 100);
        CompactBlock blockTest4 = new CompactBlock(50, 100);
        ivyManager.getTimeline().getSecondaryLine().addBlock(blockTest3);
        ivyManager.getTimeline().getSecondaryLine().addBlock(blockTest4);
//        ivyManager.getTimeline().getMainLine().addBlock(blockTest1);
//        ivyManager.getTimeline().getMainLine().addBlock(blockTest2);
        root.getChildren().add(ivyManager.getTimeline());

        
        //try {
            //Runtime rt = Runtime.getRuntime();
            //rt.exec("ivymon");
            //rt.exec("rejeu /usr/lib/rejeu/1_heure_Bordeaux.txt");
            //rt.exec("twinkle2013");
        //} catch (IOException ex) {
        //    Logger.getLogger(RadarViewTest.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int w = (int)primaryScreenBounds.getWidth();
        Scene scene = new Scene(root, w, 470, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}