package primitives;

/**
 * This class will represent a point in the 3d space.
 * @author Yishua GOlubtchik & Yair Yaha
 */
public class Point {

    public static final Point ZERO = new Point(Double3.ZERO);
    protected final Double3 xyz;

    /**
     * this constructor assigns the inner Double3 the value of 'init'
     * @param init passed Double3 value
     */
    protected Point(Double3 init) {
        xyz = init;
    }

    /**
     * constructor takes in 3 doubles and assigns them to our Point.
     * @param d1 x axis
     * @param d2 y axis
     * @param d3 z axis
     */
    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    public Vector subtract (Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    public Point add (Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    public double distanceSquared(Point point){
        // (x1-x2)^2 + (y1 - y2)^2 + (z1 - z2)^2
        return ((xyz.d1 - point.xyz.d1)* (xyz.d1 - point.xyz.d1)
                + (xyz.d2 - point.xyz.d2)* (xyz.d2 - point.xyz.d2)
                + (xyz.d3 - point.xyz.d3)* (xyz.d3 - point.xyz.d3));
    }

    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }

}
