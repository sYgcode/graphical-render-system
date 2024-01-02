package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

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
        return null;
    }
}
