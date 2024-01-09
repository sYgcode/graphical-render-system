package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Tube
 * @author Shua Golubtchik & Yair Yahav
 */
class TriangleTests {

    private final double DELTA = 0.000001;

    /** Test method for {@link Triangle#getNormal(Point)}. */
    @Test
    void getNormal() {
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        //check for errors
        assertDoesNotThrow(() -> triangle.getNormal(new Point(0, 0, 1)), "");
        Vector result = triangle.getNormal(new Point(0,0, 1));
        assertEquals(1, result.length(), DELTA, "ERROR: Triangle normal the wrong length");
        //check if it's orthogonal to all points
        String error = "ERROR: Triangle's normal is not orthogonal";
        assertEquals(0, result.dotProduct(new Point(0, 0, 1).subtract(new Point(0, 1, 0))),error);
        assertEquals(0, result.dotProduct(new Point(1, 0, 0).subtract(new Point(0, 1, 0))),error);
        assertEquals(0, result.dotProduct(new Point(0, 0, 1).subtract(new Point(1, 0, 0))),error);

    }

}