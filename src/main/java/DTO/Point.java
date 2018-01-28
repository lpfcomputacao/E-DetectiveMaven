package DTO;
import java.io.Serializable;
public class Point implements Serializable{/**
 *
 */
    private static final long serialVersionUID = 1L;
    private double latitude;
    private double longitude;

    public Point () {}

    public Point(double x, double y) {
        this.latitude = x;
        this.longitude = y;
    }

    public double getX() {
        return latitude;
    }

    public void setX(double x) {
        this.latitude = x;
    }

    public double getY() {
        return longitude;
    }

    public void setY(double y) {
        this.longitude = y;
    }
}
