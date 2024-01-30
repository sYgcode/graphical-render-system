package primitives;

import geometries.Geometries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Rays
 * @author Shua Golubtchik & Yair Yahav
 */
public class RayTests {
    /**
     * Test method for {@link Ray#getPoint(double)}}.
     */
    @Test
    public void testGetPoint(){
        Ray ray = new Ray(new Point(1,0,0), new Vector(-1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01 Positive
        assertEquals(new Point(-1,0,0), ray.getPoint(2), "ERROR: getpoint positive doesn't work");
        //TC02 Negative
        assertEquals(new Point(3,0,0), ray.getPoint(-2), "ERROR: getpoint negative doesn't work");
        // =============== Boundary Values Tests ==================
        //TC11 zero
        assertEquals(ray.head, ray.getPoint(0), "ERROR: getpoint zero doesn't work");


    }

    @Test
    public void testFindClosestPoint(){

    }
}
