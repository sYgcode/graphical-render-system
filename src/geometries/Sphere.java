package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class, inherits from radial geometry. contains radius and center point
 */
public class Sphere extends RadialGeometry{
    /**
     * Point to represent center of Sphere
     */
    protected Point center;

    /**
     * function to calculate normal (vertical) vector from the given point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return returns the normal vector
     */
    @Override
    Vector getNormal(Point point) {
        return null;
    }
}
