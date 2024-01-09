package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Sphere
 * @author Shua Golubtchik & Yair Yahav
 */
class SphereTests {

    private final double DELTA = 0.000001;

    /** Test method for {@link Sphere#getNormal(Point)}. */
    @Test
    void getNormal() {
        Point p1 = new Point(1,0,0);
        Sphere sphere = new Sphere((new Point(0,0,0)), 1);
        Vector n = new Vector(1,0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks normal
        assertDoesNotThrow(()->sphere.getNormal(p1), "");
        Vector result = sphere.getNormal(p1);
        assertEquals(1, result.length(), DELTA, "ERROR: Sphere normal is not the right length");
        assertEquals(sphere.getNormal(p1), n, "ERROR: get Normal for sphere failed");


    }
}