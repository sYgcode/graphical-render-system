package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface to be inherited from by sons
 */
public abstract class Geometry implements Intersectable {
    /**
     * function to calculate normal (vertical) vector from the given point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return returns the normal vector
     */
    public abstract Vector getNormal(Point point);
}
