package test;

import controller.Parser;
import ivy.IvyManager;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.InfoBlock;
import model.CompactBlock;
import view.InformationView;

public class RadarViewTest extends Application {

    //private  static final int W = 800;
    //private static final int H = 520;
           
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int w = (int)primaryScreenBounds.getWidth();
        int h = (int)primaryScreenBounds.getHeight() - 20;
    
    public static void main(String[] args) {
        Application.launch(RadarViewTest.class, args);
    }
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Centered Plane View");
        //primaryStage.setResizable(false);
        Group root = new Group();
        IvyManager ivyManager = new IvyManager(); 
        
        Parser parser = new Parser();
        parser.parsing();
        parser.parsingMessageATC();
        
        CompactBlock blockTest3 = new CompactBlock(6000, 100);
        CompactBlock blockTest4 = new CompactBlock(50, 100);
        ivyManager.getTimeline().getSecondaryLine().addBlock(blockTest3);
        ivyManager.getTimeline().getSecondaryLine().addBlock(blockTest4);
        
        List<InfoBlock> blocks = parser.getBlocks();
        List<CompactBlock> atcBlocks = parser.getAtcBlocks();
        
        for (InfoBlock block : blocks) {
            ivyManager.getTimeline().getMainLine().addBlock(block);
        }
        
        for (CompactBlock block : atcBlocks) {
            ivyManager.getTimeline().getSecondaryLine().addBlock(block);
        }
        
//        ivyManager.getTimeline().getMainLine().addBlock(blockTest1);
//        ivyManager.getTimeline().getMainLine().addBlock(blockTest2);

        VBox vb = new VBox();
        InformationView infoView = new InformationView();
        
        HBox hb = new HBox(20);
        hb.getChildren().addAll(ivyManager.getRadarView(),infoView);
        
        vb.getChildren().addAll(hb,ivyManager.getTimeline());
        //root.getChildren().addAll(ivyManager.getRadarView());
       

        root.getChildren().add(vb);


        //Scene scene = new Scene(root, w, 470, Color.BLACK);
        Scene scene = new Scene(root, w,h,Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

