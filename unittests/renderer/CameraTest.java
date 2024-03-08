package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.*;
import scene.Scene;
//import scene.Scene;

/**
 * Testing Camera Class
 * @author Dan
 */
class CameraTest {
   /** Camera builder for the tests */
   private final Camera.Builder cameraBuilder = new Camera.Builder().setLocation(Point.ZERO)
           .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
           .setVpDistance(10).setImageWriter(new ImageWriter("test construct Ray", 500, 800)).setRayTracer(new SimpleRayTracer(new Scene("hi")));


   /**
    * Test method for
    * {@link renderer.Camera#constructRay(int, int, int, int)}.
    */
   @Test
   void testConstructRay() {
      final String badRay  = "Bad ray";

      // ============ Equivalence Partitions Tests ==============
      // EP01: 4X4 Inside (1,1)
      Camera.Builder builder = cameraBuilder;
      Camera camera1 = (builder.setVpSize(8, 8).build());
      assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                   camera1.constructRay(4, 4, 1, 1).getFirst(), badRay);

      // =============== Boundary Values Tests ==================
      // BV01: 4X4 Corner (0,0)
      assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                   camera1.constructRay(4, 4, 0, 0).getFirst(), badRay);

      // BV02: 4X4 Side (0,1)
      assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                   camera1.constructRay(4, 4, 1, 0).getFirst(), badRay);

      // BV03: 3X3 Center (1,1)
      Camera camera2 = builder.setVpSize(6, 6).build();
      assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                   camera2.constructRay(3, 3, 1, 1).getFirst(), badRay);

      // BV04: 3X3 Center of Upper Side (0,1)
      assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                   camera2.constructRay(3, 3, 1, 0).getFirst(), badRay);

      // BV05: 3X3 Center of Left Side (1,0)
      assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                   camera2.constructRay(3, 3, 0, 1).getFirst(), badRay);

      // BV06: 3X3 Corner (0,0)
      assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                   camera2.constructRay(3, 3, 0, 0).getFirst(), badRay);

   }

}
