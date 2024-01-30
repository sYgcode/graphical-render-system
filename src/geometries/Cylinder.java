package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * cylinder class, represents non infinite tube, inherits from tube
 */
public class Cylinder extends Tube{
    /**
     * double to represent height of cylinder
     */
    private final double height;

    /**
     * constructor to initialize Cylinder
     * @param height height to initialize to
     * @param axis axis to initialize to
     * @param radius radius to initialize to
     */
    public Cylinder(double height, Ray axis, double radius){
        super(axis, radius);
        this.height = height;
    }


    @Override
    public Vector getNormal(Point point){
        Point p0 = axis.head;
        Vector v = axis.direction;
        double t = 0;
        //check tube BVA P-p0
        try {
            t = alignZero(point.subtract(p0).dotProduct(v));
        }
        catch (IllegalArgumentException e) {
            return v;
        }

        //check if the point is at the base
        if(isZero(t) || isZero(height-t)){
            return v;
        }

        // reassign p0 so dont need to create new obj
        p0 = p0.add(v.scale(t));
        return point.subtract(p0).normalize();
    }
}
