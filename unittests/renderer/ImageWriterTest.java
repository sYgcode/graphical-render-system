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
                .setVpSize(800, 500);
        Color color1 = new Color(255, 0, 0);
        Color color2 = new Color(java.awt.Color.YELLOW);
        scene.setBackground(color1);
        ImageWriter imgWriter = new ImageWriter("first test", 800, 500);
        camera
                .setImageWriter(imgWriter);
        camera.build().renderImage();
        int i, j;
        for(i=0; i<800; i+=50){
            for(j=0; j<500; j++){
                imgWriter.writePixel(i, j, color2);
            }
        }
        for(j=0; j<500; j+=60){
            for(i=0; i<800; i++){
                imgWriter.writePixel(i, j, color2);
            }
        }
        camera.setImageWriter(imgWriter);
        camera.build();
        camera.build().writeToImage();
    }
}
