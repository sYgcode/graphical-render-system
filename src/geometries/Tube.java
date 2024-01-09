package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

public class Tube extends RadialGeometry{
    /**
     * Ray to represent the axis of the tube
     */
    protected final Ray axis;

    /**
     * constructor to initialize Tube
     * @param axis axis to initialize to
     * @param radius radius to initialize to
     */
    protected Tube(Ray axis, double radius){
        super(radius);
        this.axis = axis;
    }


    @Override
    public Vector getNormal(Point point){
        double t = axis.direction.dotProduct(point.subtract(axis.head));
        Point o = axis.head.add(axis.direction.scale(t));
        return point.subtract(o).normalize();
    }
}
