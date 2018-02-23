package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Plane {
    
    //--- affichage de l'avion
    private final float GREEN = 1.0f;
    private Color planeColor = new Color(0, GREEN, 0, 0.0f);
    private float opacity = 0.f;
    
    //--- info de l'avion   
    private String flight;
    private String callSign;
    private String time;
    private String sector;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private int speed;
    private String dep;
    private String arr;
    private String list;
    
    //---forme de l'avion
    private Rectangle shape;

    //---
    
    public Plane (){
        opacity =1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
    }
    
    public Plane(String flt,String cs,float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.vx = xSpeed;
        this.vy = ySpeed;
        this.sector="";
        this.flight =flight;
        callSign = cs;
        opacity =1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
    }

    public void drawPlane(Graphics g) {
        shape.setLocation((int) x, (int) y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(planeColor);
        g2d.fillOval(shape.x, shape.y, 15, 15);
        g2d.drawString("   |"+callSign,x+5,y);
        g2d.drawString("   |"+x,x+5,y+10);
        g2d.drawString("   |"+y,x+5,y+20);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float xPos) {
        this.x = xPos;
    }

    public void setY(float yPos) {
        this.y = yPos;
    }

    public void movePlane() {
        x += vx;
        y += vy;
    }
        public Color getPlaneColor() {
        return planeColor;
    }

    public void setPlaneColor(Color pc) {
        this.planeColor = pc;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String cs) {
        this.callSign = cs;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    
    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int groundSpeed) {
        this.speed = groundSpeed;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "\nPlane{" + "flight=" + flight + ", callSign=" + callSign + ", time=" + time + ", sector=" + sector + ", x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", speed=" + speed + ", dep=" + dep + ", arr=" + arr + ", list=" + list + '}';
    }
    

}
