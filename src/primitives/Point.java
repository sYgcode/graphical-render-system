package primitives;

/**
 * This class will represent a point in the 3d space.
 * @author Yishua Golubtchik & Yair Yahav
 */
public class Point {
    /**
     * ZERO Point
     */
    public static final Point ZERO = new Point(Double3.ZERO);
    /**
     * Double3 to hold Point variables
     */
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

    /**
     * subtract a new point from our current point
     * @param point passed point to subtract from our point
     * @return returns a vector from the second point to our point
     */
    public Vector subtract (Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * adds a vector to our point
     * @param vector vector to add to our point
     * @return returns the new point
     */
    public Point add (Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * calculates the squared distance between our point to another point
     * @param point the point to calculate the distance from our point from
     * @return returns the squared distance between the 2 points as a double
     */
    public double distanceSquared(Point point){
        // (x1-x2)^2 + (y1 - y2)^2 + (z1 - z2)^2
        return ((xyz.d1 - point.xyz.d1)* (xyz.d1 - point.xyz.d1)
                + (xyz.d2 - point.xyz.d2)* (xyz.d2 - point.xyz.d2)
                + (xyz.d3 - point.xyz.d3)* (xyz.d3 - point.xyz.d3));
    }

    /**
     * calculates the distance between our point and another point
     * @param point the point to calculate the distance from our point from
     * @return returns the distance between the 2 points as a double
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }

    public double getX()
    {
        return xyz.d1;
    }
    public double getY()
    {
        return xyz.d2;
    }

}
