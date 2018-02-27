
package model;

import com.sun.javafx.geom.Point2D;


public class Position {
    
    private  Point2D pos;

    public Position(){
      pos = new Point2D();

    }
    
    public Position(Point2D position) {
        this.pos = position;
    }

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }
    
    @Override
    public String toString() {
        return "\nPosition{" + "position=" + pos + '}';
    }
    
}
