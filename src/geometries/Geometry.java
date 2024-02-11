package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface to be inherited from by sons
 */
public abstract class Geometry implements Intersectable {
    /** emission of Geometry, init at Black*/
    protected Color emission = Color.BLACK;

    /**
     * gets the emission field for the Geometry
     * @return the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * set the emission to something new
     * @param emission the new emission
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * function to calculate normal (vertical) vector from the given point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return returns the normal vector
     */
    public abstract Vector getNormal(Point point);
}
