package premitives;

/**
 * This class will represent a point in the 3d space.
 * @author Yishua GOlubtchik & Yair Yaha
 */
public class Point {
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

    /**
     * this function overrides the equals function and allows us to use the equals function for Point.
     * @param obj  passed object to assign to our instance.
     * @return returns the new object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    /**
     * this function overrides the toString function
     * @return returns the double3 in String format (d1, d2, d3)
     */
    @Override
    public String toString() {
        return xyz.toString();
    }

}
