package test;

import java.awt.Dimension;

import javax.swing.JFrame;

import model.Plane;
import view.Radar;

public final class Test extends JFrame {

    private final Radar radar;

    public Test() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 430);
        this.setLayout(null);
        this.setTitle("Plane Centered View");

        radar = new Radar();
        radar.setBounds(0, 0, 400, 400);
        radar.setSize(new Dimension(400, 400));
        
        
        Plane p0 = new Plane("p0",193, 195,0,0);
        radar.addPlaneToRadar(p0);

        Plane p1 = new Plane("p1",75, 75, .1f, 0.0f);        
        radar.addPlaneToRadar(p1);

        //---
        Plane p2 = new Plane("p2",200, 300, 0.1f, -.1f);

        radar.addPlaneToRadar(p2);
        //---
        Plane p3 = new Plane("p3",250, 150, 0.1f, .2f);
        radar.addPlaneToRadar(p3);
        //---
        Plane p4 = new Plane("p4",90, 105, -.1f, .2f);
        radar.addPlaneToRadar(p4);

        this.getContentPane().add(radar);
        this.setLocationRelativeTo(null);

        runRadar();
    }

    private void runRadar() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        radar.radarScan();
                        sleep(30);
                    } 
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        Test testFrame = new Test();
        testFrame.setVisible(true);
    }
}
