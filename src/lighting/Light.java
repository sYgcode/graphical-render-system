package lighting;

import primitives.Color;

/**
 * represents light
 */
abstract class Light {
    /**intensity of light source */
    protected Color intensity;

    /**
     * constructor that inits intensity
     * @param intensity intensity to init to
     */
    protected Light(Color intensity){
        this.intensity = intensity;
    }
    /** returns intensity*/
    public Color getIntensity() {
        return intensity;
    }
}
