package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        Point rayHead = ray.head;
        Vector v = ray.direction;
        if(rayHead.equals(center)) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }
        Vector u = center.subtract(rayHead);
        double tm = alignZero(v.dotProduct(u));
        double d = Math.sqrt(alignZero(u.lengthSquared()-tm*tm));
        if(d>=radius){
            // no intersections
            return null;
        }
        double th = Math.sqrt(alignZero(radius*radius-d*d));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if(t1 <= 0 && t2 <= 0){
            return null;
        }
        else if (t1 > 0 && t2 <= 0){
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }
        else if(t2 > 0 && t1 <= 0){
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }
        else if(t2>0 && t1 > 0){
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        }
        return null;
    }

//    @Override
//    public List<Point> findIntersections(Ray ray){
//        Point rayHead = ray.head;
//        Vector v = ray.direction;
//        if(rayHead.equals(center)) {
//            return List.of(ray.getPoint(radius));
//        }
//        Vector u = center.subtract(rayHead);
//        double tm = alignZero(v.dotProduct(u));
//        double d = Math.sqrt(alignZero(u.lengthSquared()-tm*tm));
//        if(d>=radius){
//            // no intersections
//            return null;
//        }
//        double th = Math.sqrt(alignZero(radius*radius-d*d));
//        double t1 = alignZero(tm + th);
//        double t2 = alignZero(tm - th);
//
//        if(t1 <= 0 && t2 <= 0){
//            return null;
//        }
//        else if (t1 > 0 && t2 <= 0){
//            return List.of(ray.getPoint(t1));
//        }
//        else if(t2 > 0 && t1 <= 0){
//            return List.of(ray.getPoint(t2));
//        }
//        else if(t2>0 && t1 > 0){
//            return List.of(ray.getPoint(t1), ray.getPoint(t2));
//        }
//        return null;
//    }
}
