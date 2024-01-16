package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Triangle class inherits from Polygon
 */
public class Triangle extends Polygon{
    /**
     * constructor to initialize Triangle
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        // check with plane first
        List<Point> planeIntersections = plane.findIntersections(ray);
        if (planeIntersections == null)
            return null;

        Point p0 = ray.head;
        Vector v = ray.direction;

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        //crossproducts
        double a1 = alignZero(v.dotProduct(v1.crossProduct(v2)));
        double a2 = alignZero(v.dotProduct(v2.crossProduct(v3)));
        double a3 = alignZero(v.dotProduct(v3.crossProduct(v1)));

        if(isZero(a1) || isZero(a2) || isZero(a3)){
            return null;
        }

        if((a1 > 0 && a2 > 0 && a3 > 0) || (a1 < 0 && a2 < 0 && a3 < 0)){
            return List.of(planeIntersections.getFirst());
        }

        return null;
    }

}
