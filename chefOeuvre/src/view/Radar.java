package view;

import model.Plane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public final class Radar extends JPanel {

    private final List<Plane> planeList;
    //utiliser pluto une mappour modifier les coordonées
    private final int RADAR_SIZE = 600;
    //private final int RADAR_SIZE_Y = 480;
    private final Point POINT = new Point(10,10);

    public Radar() {
        planeList = new ArrayList<>();
        this.setBounds(0, 0, RADAR_SIZE+100, RADAR_SIZE+100);
    }

    public void addPlaneToRadar(Plane p) {
        planeList.add(p);
    }

    public void radarScan() {
        for (Plane p : planeList) {
            p.movePlane();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        //---
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.DARK_GRAY);
        //---
        g2d.drawArc(POINT.x+30, POINT.y+30, RADAR_SIZE,RADAR_SIZE,0, 180);
        g2d.drawArc(POINT.x+130, POINT.y+130,RADAR_SIZE-200,RADAR_SIZE-200,0,180);
        g2d.drawArc(POINT.x+230, POINT.y+230,RADAR_SIZE-400,RADAR_SIZE-400,0,180);
        //---
       g2d.drawLine(340, 30, 340,350);
       g2d.drawLine(30,340,650,340);

        for (Plane p : planeList) {
            p.drawPlane(g2d);
        }

    }

    public List<Plane> getPlaneList() {
        return planeList;
    }

}
