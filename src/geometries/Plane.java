package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * flat plane at a certain point
 */
public class Plane extends Geometry{
    /**
     * point for reference
     */
    private final Point q;

    /**
     * normal to the plane
     */
    private final Vector normal;

    /**
     * constructor to find normal based on 3 points and save a reference point
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    protected Plane(Point p1,Point p2,Point p3){
        q = p1;
        normal = (p2.subtract(p1)).crossProduct(p3.subtract(p1)).normalize();
    }

    /**
     * constructor to initialize the point and normal.
     * @param q point of reference
     * @param normal normal vector (doesn't need to be normalized)
     */
    public Plane(Point q, Vector normal){
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * function to find the normal vector of the plane
     * @return the normal vector of the plane
     */
    public Vector getNormal(){
        // from my understanding don't implement the function yet
        return normal;
    }

    @Override
    public Vector getNormal(Point point){
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        Point p0 = ray.head;
        Vector v = ray.direction;
        Vector n = normal;
        double nv = alignZero(n.dotProduct(v));

        if(isZero(nv)){
            return null;
        }
        // ray can't start from plane
        if(p0.equals(q)){
            return null;
        }
        Vector p0_q = q.subtract((p0));
        double np0_q = alignZero(n.dotProduct(p0_q));

        // ray parallel to plane
        if(isZero(np0_q)) {
            return null;
        }

        double t = alignZero((np0_q/nv));

        if (t<0){
            return null;
        }

        // after checking BVA's return
        return List.of(ray.getPoint(t));
    }

}
