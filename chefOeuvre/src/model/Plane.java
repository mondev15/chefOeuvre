package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

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
    private int heading; //cap
    private int afl; // niveau
    private double vx;
    private double vy;
    private int speed;
    private int tendency;
    //---route
    private Route route;

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
        heading = 0;
        afl = 0;
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
                (double)((int)(ndPosition.x*100))/100
        );
    }

    public int getTendency() {
        return tendency;
    }

    public void setTendency(int tendency) {
        this.tendency = tendency;
    }
    //--- ATTENTION LES COORDONNEES SONT EN CAUTRA (Coordonnées AUtomatisées du TRafic Aerien)
    //--- calcule la nouvelle twinklePosition du point dans la vue navigation display
    /*
    public void calculateNewPosition() {
        //---le temps ne change pas, c'est le même dans les deux vues (repères)
        //---la twinklePosition change en fonction de la vitesse et du cap
        double x = Math.abs( twinklePosition.x - speed * Math.cos(heading));
        double y = Math.abs(twinklePosition.y + speed * Math.sin(heading));
        ndPosition = new Point2D.Double((double)((int)(x*100))/100,(double)((int)(y*100))/100);
    }
     */       
    public int getMAX_DISTANCE() {
        return MAX_DISTANCE;
    }

    @Override
    public String toString() {
        return "\nPlane{" + "flight=" + flight + ", callSign=" + callSign + ", time=" + time + ", sector=" + sector + ", twinkle=" + twinklePosition + ", nd=" + ndPosition + ", heading=" + heading + ", afl=" + afl + ", vx=" + vx + ", vy=" + vy + ", speed=" + speed + ", tendency=" + tendency + ", route=" + route + '}';
    }
    
}
