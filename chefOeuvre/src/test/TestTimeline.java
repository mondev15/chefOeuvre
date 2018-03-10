///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import model.Block;
//import view.SingleLine;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.paint.Color;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import view.Timeline;
//
//public class TestTimeline extends Application {
//
//    public static void main(String[] args) {
//        Application.launch(TestTimeline.class, args);
//    }
//    
//    @Override
//    public void start(Stage primaryStage) {
//        
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        int w = (int)primaryScreenBounds.getWidth();
//
//        primaryStage.setTitle("Test Timeline");
//        Group root = new Group();
//        Scene scene = new Scene(root, w, 320, Color.LIGHTGREEN);
//        Timeline timeline = new Timeline();
//        timeline.setCurrentTime(4000);
//        Block blockTest1 = new Block(4950,
//                           "APPROCHE -> PILOTE",
//                           "180 kts",
//                           "Left 260°",
//                           "ILS 14D",
//                           "CALL BACK");
//        
//        timeline.getMainLine().addBlock(blockTest1);
//        root.getChildren().add(timeline);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        Block blockTest2 = new Block(3500,
//                   "APPROCHE -> PILOTE",
//                   "180 kts",
//                   "Left 260°",
//                   "ILS 14D",
//                   "CALL BACK");
//        timeline.getMainLine().addBlock(blockTest2);
//        blockTest2.setHDG("Right changed");
//    }
//}


//                   timeline = new Timeline();
//                   timeline.setVisible(true);
//                   SingleLine sLine1 = timeline.getSingleLine1();
//                   sLine1.addBlock(
//                           new Block(4000,
//                           "APPROCHE -> PILOTE",
//                           "180 kts",
//                           "Left 260°",
//                           "ILS 14D",
//                           "CALL BACK"));