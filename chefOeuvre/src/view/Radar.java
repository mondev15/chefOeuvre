package view;

import model.Plane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public final class Radar extends JPanel {

    private final List<Plane> planeList;
    private final int RADAR_SIZE = 400;
    private final Point POINT = new Point(10,10);

    public Radar() {
        planeList = new ArrayList<>();
        this.setSize(new Dimension(RADAR_SIZE,RADAR_SIZE));
        this.setBounds(0, 0, 430, 430);
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
        //g2d.drawArc(20, 20, 360,360,0, 180);
        g2d.drawOval(POINT.x+40, POINT.y+40,RADAR_SIZE-60, RADAR_SIZE-60);
        g2d.drawOval(POINT.x+70, POINT.y+70, RADAR_SIZE-120, RADAR_SIZE-120);
        g2d.drawOval(POINT.x+100, POINT.y+100, RADAR_SIZE-180, RADAR_SIZE-180);
        //---
        g2d.drawLine(220, 40, 220,400);
        g2d.drawLine(40, 220, 400,220);

        for (Plane p : planeList) {
            p.drawPlane(g2d);
        }

    }

    public List<Plane> getPlaneList() {
        return planeList;
    }

}
