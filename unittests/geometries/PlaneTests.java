package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 * @author Shua Golubtchik & Yair Yahav
 */
class PlaneTests {

    private final double DELTA = 0.000001;
    /** Test method for {@link Plane#Plane(Point, Point, Point)}. */
    @Test
    void testConstructor() {
        Point p1 = new Point (2, 6, 2);
        Point p2 = new  Point (0, 0, 3);
        Point p3 = new Point (0, 0, 7);
        Point p4 = new Point(0, 0, 0);
        // =============== Boundary Values Tests ==================
        //TC11 test case for 2 of the same point
        String error1 = "ERROR: no error thrown for 2 similar points or wrong exception thrown";
        assertThrows(IllegalArgumentException.class, () -> new Plane(p3, p3, p1), error1);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p3, p1, p3), error1);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p3, p3), error1);
        //TC12 test case if all on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p4, p2, p3), "ERROR: no error thrown for all points on the same line or wrong exception thrown");

    }

    /** Test method for {@link Plane#getNormal(Point)}. */
    @Test
    void testGetNormal() {
        Point p1 = new Point(0,0,0);
        Point p2 = new Point(1,0,0);
        Point p3 = new Point(0,2,0);
        Plane plane = new Plane(p1, p2, p3);
        // ============ Equivalence Partitions Tests ==============
        //TC01 check if normal works
        //check if there are errors
        assertDoesNotThrow(() -> plane.getNormal(), "");
        //get result
        Vector result = plane.getNormal(new Point(1,0,0));
        //ensure normal is the right length
        assertEquals(1, result.length(), DELTA, "ERROR: Plane normal is not the right length");
        //check the normal is orthogonal to all
        String error = "ERROR: Plane's normal is not orthogonal";
        assertEquals(0, result.dotProduct(p1.subtract(p3)),error);
        assertEquals(0, result.dotProduct(p2.subtract(p3)),error);
        assertEquals(0, result.dotProduct(p1.subtract(p2)),error);


    }
}