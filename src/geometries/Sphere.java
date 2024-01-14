package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class, inherits from radial geometry. contains radius and center point
 */
public class Sphere extends RadialGeometry{
    /**
     * Point to represent center of Sphere
     */
    private final Point center;

    /**
     * constructor to initialize Tube
     * @param center center point to initialize to
     * @param radius radius to initialize to
     */
    public Sphere(Point center, double radius){
        super(radius);
        this.center = center;
    }


    @Override
    public Vector getNormal(Point point) {

        return point.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }
}
