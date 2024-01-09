package primitives;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


import org.junit.jupiter.api.Test;

/**
 * Testing Vector
 * @author Shua Golubtchik & Yair Yahav
 */
class VectorTests {

    /** Test method for {@link Vector#Vector(Double3)}. */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
        // TC11 test zero vector
        String error1 = "ERROR: zero vector does not throw an exception or throws the wrong exception";

        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), error1);
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), error1);



    }
    /** Test method for {@link Vector#add(Vector)}. */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v1Opposite = new Vector(-1, -2, -3);
        // ============ Equivalence Partitions Tests ==============
        //TC01
        assertEquals(v1.add(v2), v1Opposite, "ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11 tests creating a zero vector through addition
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself does not throw an exception, or throws the wrong exception");
    }

    /** Test method for {@link Vector#subtract(Point)}. */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks subtraction
        assertEquals(v1.subtract(v2), new Vector(3, 6, 9), "ERROR: Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11 tests creating a zero vector through subtraction
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception, or throws the wrong exception");
    }

    /** Test method for {@link Vector#dotProduct(Vector)}. */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
        //TC11 checks if dot product of orthogonal vector is zero
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        //TC12 checks if dot product works correctly
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
    }

    /** Test method for {@link Vector#crossProduct(Vector)}. */
    @Test
    void testCrossProduct() {
    Vector v1 = new Vector(1, 2, 3);
    // ============ Equivalence Partitions Tests ==============
    Vector v2 = new Vector(0, 3, -2);
    Vector vr = v1.crossProduct(v2);
    // TC01: Test that length of cross-product is proper (orthogonal vectors taken
    // for simplicity)
    assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
    // TC02: Test cross-product result orthogonality to its operands
    assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
    assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
    // =============== Boundary Values Tests ==================
    // TC11: test zero vector from cross-productof co-lined vectors
    Vector v3 = new Vector(-2, -4, -6);
    assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
            "crossProduct() for parallel vectors does not throw an exception");
    }

    /** Test method for {@link Vector#lengthSquared()}. */
    @Test
    void testLengthSquared() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
        //TC11 checks length squared
        assertTrue(isZero(v4.lengthSquared() - 9), "ERROR: lengthSquared() wrong value");
    }

    /** Test method for {@link Vector#length()}. */
    @Test
    void testLength() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks length
        assertTrue(isZero(v4.length() - 3), "ERROR: length() wrong value");
        // =============== Boundary Values Tests ==================

    }

    /** Test method for {@link Vector#normalize()}. */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if normalization works
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not parallel to the original one");

        // =============== Boundary Values Tests ==================
        //TC12 test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");
        assertTrue((v.dotProduct(u) >= 0), "ERROR: the normalized vector is opposite to the original one");

    }
}