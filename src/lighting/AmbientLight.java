package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private final Color intensity;
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

     AmbientLight(Color Ia, Double3 Ka){
        intensity = new Color(Ia.scale(Ka).getColor());
    }
    AmbientLight(Color Ia, double Ka){
         intensity = new Color(Ia.scale(Ka).getColor());
    }

    public Color getIntensity() {
        return intensity;
    }
}
