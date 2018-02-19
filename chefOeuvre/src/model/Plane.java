package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public final class Plane {

    private final float GREEN = 1.0f;
    private Color planeColor = new Color(0, GREEN, 0, 0.0f);

    private float xPos;
    private float yPos;
    private String planeId;
    private Rectangle shape;
    private float opacity = 0.f;
    private float xSpeed;
    private float ySpeed;

    public Plane(String id,float x, float y, float xSpeed, float ySpeed) {
        this.xPos = x;
        this.yPos = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        planeId = id;
        opacity =1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
    }

    public void drawPlane(Graphics g) {
        shape.setLocation((int) xPos, (int) yPos);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(planeColor);
        g2d.fillOval(shape.x, shape.y, 15, 15);
        g2d.drawString("   |"+planeId,xPos+5,yPos);
        g2d.drawString("   |"+xPos,xPos+5,yPos+10);
        g2d.drawString("   |"+yPos,xPos+5,yPos+20);
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public void movePlane() {
        xPos += xSpeed;
        yPos += ySpeed;
    }
        public Color getPlaneColor() {
        return planeColor;
    }

    public void setPlaneColor(Color planeColor) {
        this.planeColor = planeColor;
    }

    public String getPlaneId() {
        return planeId;
    }

    public void setPlaneId(String planeId) {
        this.planeId = planeId;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

}
