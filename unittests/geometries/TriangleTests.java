package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /** tests max distance */
    @Test
    public void testFindGeoIntersections(){
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        //TC01 out of bounds check
        assertNull(triangle.findGeoIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,-1)), 0.1), "ERROR: bad intersect for max distance");
    }

    /** Test method for {@link Triangle#findIntersections(primitives.Ray)}. */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        //TC01 inside triangle
        assertEquals(List.of(new Point(1d/3,1d/3,1d/3)), triangle.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,-1))), "ERROR: bad intersect");
        //TC02 in between triangle rays outside triangle
        Ray ray =new Ray(new Point(0, 0, 2), new Vector(-1, -1, 0));
        //first check if it is in Plane
        assertEquals(List.of(new Point(-0.5, -0.5, 2)), plane.findIntersections(ray), "ERROR: wrong intersect with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect when shouldn't be");
        //TC03 on other side of edge outside triangle
        //first check if it is in Plane
       ray = new Ray(new Point(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(1, 1, -1)), plane.findIntersections(ray),
                "ERROR: wrong intersect with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // =============== Boundary Values Tests ==================
        //TC11 intersects on edge
        ray = new Ray(new Point(-1, -1, 0), new Vector(1, 1, 0));
        //check plane first
        assertEquals(List.of(new Point(0.5, 0.5, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
        //TC12 intersect on vertex
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0));
        //check plane first
        assertEquals(List.of(new Point(0, 1, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
        //TC13 intersect on edge continuation
        ray = new Ray(new Point(-2, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(-0.5, 1.5, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
    }
}