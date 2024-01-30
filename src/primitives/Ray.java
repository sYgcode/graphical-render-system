package primitives;
import java.util.List;

import static primitives.Util.*;

/**
 * Ray made up out of a Point representing the head and a Vector representing the direction.
 * @author Yishua Golubtchik & Yair Yahav
 */
public class Ray {
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
    public Point findClosestPoint(List<Point> points){
        if(points == null){
            return null;
        }
        double shortestDist;
        double dist;
        //get first distance
        shortestDist = head.distance(points.getFirst());
        Point closestPoint = points.getFirst();
        for(int i = 0; i < points.size(); i++){
            dist = head.distance(points.get(i));
            if (shortestDist>dist){
                shortestDist = dist;
                closestPoint = points.get(i);
            }
        }
        return closestPoint;
    }

}
