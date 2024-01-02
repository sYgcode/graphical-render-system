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
    protected Point q;
    /**
     * normal to the plane
     */
    protected Vector normal;

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
    protected Plane(Point q, Vector normal){
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * function to find the normal vector of the plane
     * @return the normal vector of the plane
     */
    protected Vector getNormal(){
        // from my understanding don't implement the function yet
        return null;
    }

    /**
     * function to find the normal vector of the plane using the passed point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return the normal vector of the plane
     */
    @Override
    protected Vector getNormal(Point point){
        // from my understanding don't implement the function yet
        return null;
    }

}
