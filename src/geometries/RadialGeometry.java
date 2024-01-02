package geometries;

/**
 * parent class to certain Radial shapes
 */
public abstract class RadialGeometry extends Geometry{
    /**
     * represents the radius
     */
    protected final double radius;

    /**
     * constructor to initialize the radius
     * @param radius the value to initialize the radius to
     */
    public RadialGeometry(double radius){
        this.radius = radius;
    }
}
