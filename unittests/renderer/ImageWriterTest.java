package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * test Image Writer
 */
public class ImageWriterTest {
    @Test
    void ImageWriterTest(){
        Color color = new Color(255, 0, 0);
        Camera.Builder builder;
        ImageWriter img = new ImageWriter("test 1", 500, 800);
        for(int i=0; i < 500; i++){
            for(int j=0; j<800; j++){
                img.writePixel(i, j, color);
            }
        }

        img.writeToImage();
    }
}
