package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

public class Tube extends RadialGeometry{
    /**
     * Ray to represent the axis of the tube
     */
    protected Ray axis;

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
