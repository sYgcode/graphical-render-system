package premitives;

/**
 * This class will represent a point in the 3d space.
 * @author Yishua GOlubtchik & Yair Yaha
 */
public class Point {
    protected final Double3 xyz;

    public Point(Double3 init) {
        xyz = init;
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

}
