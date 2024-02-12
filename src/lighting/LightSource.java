package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Light source interface
 */
public interface LightSource {
    /**
     * retrieves intensity of a given point
     * @param p point to retrieve from
     * @return intensity
     */
    public Color getIntensity(Point p);

    /**
     * gets light vector at certain point
     * @param p point
     * @return light vector
     */
    public Vector getL(Point p);
}
