package model;

import java.awt.Color;
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
    private Position position;
    private int heading; //cap
    private int afl; // niveau
    private float vx; 
    private float vy;    
    private int speed;
     //---route
    private Route route;

    //---forme de l'avion
    private Rectangle shape;

    //---
    public Plane() {
        opacity = 1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
        flight ="default";
        sector="";
        route = new Route("default","default","default");
        heading=0;
        afl =0;
    }

    public Plane(String flt, String cs, Position p, float xSpeed, float ySpeed){
        position = p;
        vx = xSpeed;
        vy = ySpeed;
        sector = "";
        flight = flt;
        callSign = cs;
        opacity = 1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
        route = new Route("default","default","default");
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void movePlane() {
        position.setX(position.getX()+vx);
        position.setY(position.getY()+vy);
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public int getAfl() {
        return afl;
    }

    public void setAfl(int afl) {
        this.afl = afl;
    }

    @Override
    public String toString() {
        return "\nPlane{" + "flight=" + flight + ", callSign=" + callSign + ", time=" + time + ", sector=" + sector + ", position=" + position + ", heading=" + heading + ", afl=" + afl + ", vx=" + vx + ", vy=" + vy + ", speed=" + speed + ", route=" + route + '}';
    }

    
    
}
