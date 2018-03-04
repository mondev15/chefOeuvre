package model;

public class Vector {

    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double xA, double yA, double xB, double yB) {
        this(xB - xA, yB - yA);
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public double scalarProduct(Vector v) {
        //(x1-x3) (x2-x3) + (y1-y3) (y2-y3)
        return x * v.x + y * v.y;
    }

    //retourne l'angle en degre
    public int getAngle(Vector v) {
        return (int) Math.toDegrees(
                Math.acos(
                        scalarProduct(v) / (this.norm() * v.norm())
                )
        );

    }

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + '}';
    }

}
