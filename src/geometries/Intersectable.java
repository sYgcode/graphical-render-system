package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

/**
 * interface inherited by all geometries
 */
public interface Intersectable {
    /**
     * method to recieve a ray and find all the points it intersects with the geometry.
     * @param ray ray to check for intersections
     * @return list of the points that intersect with the geometry and ray
     */
    List<Point> findIntersections(Ray ray);
}
