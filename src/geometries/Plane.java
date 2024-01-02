package geometries;

import primitives.Point;
import primitives.Vector;

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
        normal = getNormal(p1);
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
        // from my understanding don't implement the function yet
        return normal;
    }

}
