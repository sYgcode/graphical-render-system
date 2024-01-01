package primitives;

/**
 * Ray made up out of a Point representing the head and a Vector representing the direction.
 */
public class Ray {
    /**
     * Point representing the head of the ray
     */
    public final Point head;
    /**
     * Vector representing the direction of the Ray
     */
    public final Vector direction;

    /**
     * constructor that initializes the Ray with a Point and direction
     * @param head point representing the head of the ray
     * @param direction vector representing the direction of the ray
     */
    public Ray(Point head, Vector direction){
        this.head = head;
        // there is no reason to check if the vector is already normalized due to the low probability
        // of the vector already being normalized and each check costs the runtime to check the length.
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString (){
        return ("head: " + head.toString()
                + " direction: " + direction.toString());
    }


}
