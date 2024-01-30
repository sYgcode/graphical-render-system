package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

/**
 * test Image Writer
 */
public class ImageWriterTest {
    /**
     * test to test Image Writer. test builds a render of the image and prints a grid over it
     */
    @Test
    void ImageWriterTests(){
        final Scene scene = new Scene("First Test scene");
        final Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
                .setVpDistance(100)
                .setVpSize(500, 800);
        Color color1 = new Color(255, 0, 0);
        Color color2 = new Color(java.awt.Color.YELLOW);
        scene.setBackground(color1);
        camera
                .setImageWriter(new ImageWriter("first test", 500, 800));
        camera.build().renderImage();
        camera.build().printGrid(50, color2);
        camera.build().writeToImage();
    }
}
