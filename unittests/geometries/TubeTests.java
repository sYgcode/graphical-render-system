package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube
 * @author Shua Golubtchik & Yair Yahav
 */
class TubeTests {

    private final double DELTA = 0.000001;
    /** Test method for {@link Tube#getNormal(Point)}. */
    @Test
    void getNormal() {
        Point p1 = new Point(1,2,0);
        Point p2 = new Point(1,0,0);
        Point head = new Point(0,0,0);
        Vector dir = new Vector(0, 1, 0);
        Ray ray = new Ray (head, dir);
        Tube tube = new Tube(ray, 1);
        Vector n = new Vector(1,0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if normal works
        assertDoesNotThrow(()->tube.getNormal(p1), "");
        Vector result = tube.getNormal(p1);
        assertEquals(1, result.length(), DELTA, "ERROR: Tube normal is not the right length");
        assertEquals(n, result, "ERROR: get Normal for tube failed");

        // =============== Boundary Values Tests ==================
        //TC11 checks if (p-p0) is orthogonal to dir
        assertThrows( IllegalArgumentException.class, () -> tube.getNormal(p2), "ERROR: no error thrown when (P-P0) is orthogonal to dir or wrong error thrown");
    }
}