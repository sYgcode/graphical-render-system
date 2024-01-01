package primitives;

/**
 * the class represents a Vector in the 3d Cartesian coordinate system and inherits from the {@link class}.
 * the Vector always originates from the (0, 0, 0) point.
 */
public class Vector extends Point {
    /**
     * constructor for Vector, takes in x y and z coordinates.
     * @param x coordinate value for X
     * @param y coordinate value for Y
     * @param z coordinate value for Z
     */
    public Vector(double x, double y, double z){
        super(x, y, z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException ("Vector ZERO is not allowed");
        }
    }

    /**
     * constructor for Vector, takes in a Double3
     * @param xyz Double3 to assign to Vector
     */
    public Vector(Double3 xyz){
        super(xyz);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException ("Vector ZERO is not allowed");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && this.xyz.equals(other.xyz);
    }

//    @Override
//    public String toString(){
//        return xyz.toString();
//    }

    /**
     * performs addition with this vector and another one
     * @param vector other vector
     * @return new vector after completing addition between our and the other vector.
     */
    public Vector add(Vector vector){
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * multiplies a Vector by a scalar
     * @param scalar scalar to multiply by
     * @return new Vector of our Vector multiplied by scalar
     */
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }

    /**
     * performs dot product between our vector on the left and other vector on the right.
     * @param vector vector on the right
     * @return double value of the dot product
     */
    public double dotProduct(Vector vector){
        return xyz.d1 * vector.xyz.d1
                + xyz.d2 * vector.xyz.d2
                + xyz.d3 * vector.xyz.d3;
    }

    /**
     * cross product our vector and another vector on the right.
     * @param vector other vector on the right for the cross product.
     * @return new Vector that results from the cross product
     */
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;

        return new Vector(x, y, z);
    }

    /**
     * calculates the length of the vector squared
     * @return the length of the vector squared
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1
                + xyz.d2 * xyz.d2
                + xyz.d3 * xyz.d3;
    }

    /**
     * length of the vector
     * @return returns the length of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector so the length will be one
     * @return new Vector with the same direction but the length will be one
     */
    public Vector normalize(){
        return scale((1/length()));
    }
}
