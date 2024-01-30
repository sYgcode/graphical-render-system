package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class for Ambient later
 */
public class AmbientLight {
    /** intensity of color*/
    private final Color intensity;
    /** static Ambient Light representing None*/
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);
    /** constructor, initializes intensity using Double3*/
    public AmbientLight(Color Ia, Double3 Ka){
        intensity = new Color(Ia.scale(Ka).getColor());
    }
    /** constructor, initializes intensity using Double*/
    AmbientLight(Color Ia, double Ka){
         intensity = new Color(Ia.scale(Ka).getColor());
    }
    /** returns intensity of Ambient Light*/
    public Color getIntensity() {
        return intensity;
    }
}
