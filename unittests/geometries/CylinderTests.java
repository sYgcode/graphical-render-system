package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder
 * @author Shua Golubtchik & Yair Yahav
 */
class CylinderTests {

    /** Test method for {@link Cylinder#getNormal(Point)}. */
    @Test
    void getNormal() {
        Point p1 = new Point(1,2,0);
        Vector dir = new Vector(0,1,0);
        Ray axis = new Ray((new Point(0,0,0)), dir);
        Cylinder cylinder = new Cylinder(10, axis, 1);
        // ============ Equivalence Partitions Tests ==============
        //TC01 normal on the round bit
        Vector n1 = new Vector(0,2,0);
        assertEquals(cylinder.getNormal(p1), n1);
        //TC02 normal on the top

        // =============== Boundary Values Tests ==================
    }

}