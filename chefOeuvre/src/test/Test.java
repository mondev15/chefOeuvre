package test;

import javax.swing.JFrame;

import model.Plane;
import view.Radar;

public final class Test extends JFrame {

    private final Radar radar;

    public Test() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 400);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Plane Centered View");

        radar = new Radar();
        
        //---
        Plane p0 = new Plane("DAL71",332,332,0.f,0.f);
        radar.addPlaneToRadar(p0);

        //---
        Plane p1 = new Plane("SAB5458",190, 75, .1f, 0.0f);        
        radar.addPlaneToRadar(p1);

        //---
        Plane p2 = new Plane("AF360UD",407, 262, 0.1f, -0.1f);

        radar.addPlaneToRadar(p2);
        //---
        Plane p3 = new Plane("RAM5712",250, 150, 0.1f, 0.1f);
        radar.addPlaneToRadar(p3);
        //---
        Plane p4 = new Plane("AF118AB",90, 160, 0.0f, 0.2f);
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
                        sleep(100);
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
