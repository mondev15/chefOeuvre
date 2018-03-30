package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Model of a plane
 * 
 * @author Evergiste
 */

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
    private Point2D.Double twinklePosition; // twinklePosition dans la vue twinkle
    private Point2D.Double ndPosition; // twinklePosition dans la vue navigation display
    private double heading; //cap
    private double distance; // distance par rapport à l'avion central
    private int afl; // niveau
    private double vx;
    private double vy;
    private int speed;
    private int tendency;
    private int rate; //taux de montée en pieds/minutes
    //---route
    private Route route;
    //---
    private int angle; // l'angle que l'avion fait avec l'avion central

    //---forme de l'avion
    private Rectangle shape;

    private final int MAX_DISTANCE = 30; // NM 
    //---
    public Plane() {
        opacity = 1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
        flight = "default_Flight";
        sector = "default_Sector";
        callSign = "default_CallSign";
        route = new Route("default_Dep", "default_Arr", "default_List");
        heading = 0.0;
        rate =0;
        afl = 0;
        //angle = 0;
    }

    public Plane(String flt, String cs, Point2D.Double p, double xSpeed, double ySpeed) {
        twinklePosition = p;
        ndPosition = new Point2D.Double();
        vx = xSpeed;
        vy = ySpeed;
        sector = "";
        flight = flt;
        callSign = cs;
        opacity = 1.f;
        planeColor = new Color(0.0f, GREEN, 0, opacity);
        shape = new Rectangle(10, 10);
        route = new Route("default", "default", "default");
        //angle = 0;
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

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
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

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public Point2D.Double getTwinklePosition() {
        return twinklePosition;
    }

    public void setTwinklePosition(Point2D.Double twinklePosition) {
        this.twinklePosition = new Point2D.Double(
                (double)((int)(twinklePosition.x*100))/100,
                (double)((int)(twinklePosition.y*100))/100
        );
    }

    public Point2D.Double getNdPosition() {
        return ndPosition;
    }

    public void setNdPosition(Point2D.Double ndPosition) {
        this.ndPosition = new Point2D.Double(
                (double)((int)(ndPosition.x*100))/100,
                (double)((int)(ndPosition.y*100))/100
        );
    }

    public int getTendency() {
        return tendency;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    
    public void setTendency(int tendency) {
        this.tendency = tendency;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
          
    public int getMAX_DISTANCE() {
        return MAX_DISTANCE;
    }

    @Override
    public String toString() {
        return "\nPlane{" + "flight=" + flight + ", callSign=" + callSign + ", time=" + time + ", sector=" + sector + ", twinkle=" + twinklePosition + ", nd=" + ndPosition + ", heading=" + heading + ", distance=" + distance + ", afl=" + afl + ", vx=" + vx + ", vy=" + vy + ", speed=" + speed + ", tendency=" + tendency + ", rate=" + rate + ", route=" + route + ", angle=" + angle + "°"+'}';
    }  
}
