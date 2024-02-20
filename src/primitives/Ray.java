package primitives;
import java.util.List;

import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;
/**
 * Ray made up out of a Point representing the head and a Vector representing the direction.
 * @author Yishua Golubtchik & Yair Yahav
 */
public class Ray {
    /** amount to move ray's head when calculating shadows */
    private static final double DELTA = 0.1;

    /**
     * Point representing the head of the ray
     */
    public final Point head;
    /**
     * Vector representing the direction of the Ray
     */
    public final Vector direction;

    /**
     * constructor that initializes the Ray with a Point and direction
     * @param head point representing the head of the ray
     * @param direction vector representing the direction of the ray
     */
    public Ray(Point head, Vector direction){
        this.head = head;
        // there is no reason to check if the vector is already normalized due to the low probability
        // of the vector already being normalized and each check costs the runtime to check the length.
        this.direction = direction.normalize();
    }

    /**
     * Constructor for ray deflected by DELTA
     * @param head origin
     * @param n normal vector
     * @param dir direction
     */
    public Ray(Point head, Vector n, Vector dir) {
        this.direction = dir.normalize();
        double nv = alignZero(n.dotProduct(this.direction));
        Vector delta  =n.scale(DELTA);
        if (nv<0)
            delta = delta.scale(-1);
        if(isZero(nv)){
            this.head = head;
        }
        else
            this.head = head.add(delta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString (){
        return ("head: " + head.toString()
                + " direction: " + direction.toString());
    }

    // returns P=p0 +tv
    public Point getPoint(double t) {
        if ((isZero(t))){
            return head;
        }
        return head.add(direction.scale(t));
    }

    /**
     * returns the closest point to the head of the ray
     * @param points list of points to check
     * @return closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * finds the closest GeoPoint to the head of the ray and returns it
     * @param points list to search
     * @return closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points){
        if(points == null){
            return null;
        }
        double shortestDist;
        double dist;
        //get first distance
        shortestDist = head.distance(points.getFirst().point);
        GeoPoint closestPoint = points.getFirst();
        for(int i = 0; i < points.size(); i++){
            dist = head.distance(points.get(i).point);
            if (shortestDist>dist){
                shortestDist = dist;
                closestPoint = points.get(i);
            }
        }
        return closestPoint;
    }

}
