package primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;


/**
 * Unit tests for primitives.Point class
 * @author Shua Golubtchik & Yair Yahav
 */
class PointTests {

    /** Test method for {@link Point#subtract(Point)}. */
    @Test
    void subtract() {
        Point  p1 = new Point(1, 2, 3);
        Point  p2 = new Point(2, 4, 6);
        Vector v1  = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks subtraction
        assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11 checks exceptions for subtraction at center
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception or throws the wrong exception");
    }

    /** Test method for {@link Point#add(Vector)}. */
    @Test
    void add() {
        Point  p1 = new Point(1, 2, 3);
        Point  p2 = new Point(2, 4, 6);
        Vector v1  = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        // ============ Equivalence Partitions Tests ==============
        //TC01 tests if addition is working correctly
        assertEquals(p1.add(v1), p2, "ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11 checks center of coordinates using addition
        assertEquals(p1.add(v1Opposite), Point.ZERO, "ERROR: (point + vector) = center of coordinates does not work correctly");

    }
    /** Test method for {@link Point#distanceSquared(Point)}. */
    @Test
    void distanceSquared() {
        Point  p1 = new Point(1, 2, 3);
        Point  p3 = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============
        //TC02 point to second point
        assertTrue(isZero(p1.distanceSquared(p3) - 9), "ERROR: distanceSquared between points to itself is wrong");
        //TC03 second point to point
        assertTrue(isZero(p3.distanceSquared(p1) - 9), "ERROR: distanceSquared between points to itself is wrong");
        // =============== Boundary Values Tests ==================
        //TC11 distance squared to self check
        assertTrue(isZero(p1.distanceSquared(p1)), "ERROR: point distanceSquared to itself is not zero");

    }

    /** Test method for {@link Point#distance(Point)}. */
    @Test
    void distance() {
        Point  p1 = new Point(1, 2, 3);
        Point  p3 = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============
        //TC02 point to second point
        assertTrue(isZero(p1.distance(p3) - 3), "ERROR: distance between points to itself is wrong");
        //TC03 second point to point
        assertTrue(isZero(p3.distance(p1) - 3), "ERROR: distance between points to itself is wrong");
        // =============== Boundary Values Tests ==================
        //TC11 distance to self check
        assertTrue(isZero(p1.distance(p1)), "ERROR: point distance to itself is not zero");

    }
}