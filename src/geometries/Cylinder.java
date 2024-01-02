package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * cylinder class, represents non infinite tube, inherits from tube
 */
public class Cylinder extends Tube{
    /**
     * double to represent height of cylinder
     */
    protected double height;


    /**
     * function to calculate normal (vertical) vector from the given point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return returns the normal vector
     */
    @Override
    Vector getNormal(Point point){
        return null;
    }
}
