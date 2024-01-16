package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Util.*;
/**
 * Testing Sphere
 * @author Shua Golubtchik & Yair Yahav
 */
class SphereTests {

    private final double DELTA = 0.000001;

    /** Test method for {@link Sphere#getNormal(Point)}. */
    @Test
    void testGetNormal() {
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

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p02 = new Point(0.5, 0.5, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(p01, v310));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(exp, result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(p02, v310));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC04: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(3,1,0))), "Ray Crosses Sphere but isn't meant to");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point(1,-1,0)),
                sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(1,-1,0))), "ERROR: Ray from sphere inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(-1,-1,0))), "ERROR: Ray from sphere outside");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(-1,0,0),
                new Vector(1,0,0)));
        assertEquals(2, result.size(), "ERROR: through center wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0,0,0), new Point(2,0,0)), result, "ERROR: wrong points through center");
        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(1,0,0))), "ERROR: wrong point when start on sphere to center");
        // TC15: Ray starts inside (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(1.5,0,0), new Vector(1,0,0))), "ERROR: wrong point when start inside sphere to center");
        // TC16: Ray starts at the center (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0))), "ERROR: wrong point when start at center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(-1,0,0))), "ERROR: wrong point when start on sphere to outside");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(-1,0,0))), "ERROR: wrong point when start after sphere to outside center");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0), new Vector(0,1,0))), "ERROR: wrong point when tangent before sphere");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(0,1,0))), "ERROR: wrong point when tangent starts on sphere");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0), new Vector(0,1,0))), "ERROR: wrong point when tangent after sphere");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(0,1,0))), "ERROR: wrong point when orthogonal to center");
    }
}