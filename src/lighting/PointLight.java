package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Point Light class
 */
public class PointLight extends Light implements LightSource{
    /** position of light */
    private Point position;
    /** representing attenuation attributes*/
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * constructor inits Point Light's fields
     * @param intensity init intensity
     * @param position init position
     */
    PointLight(Color intensity, Point position){
        super(intensity);
        this.position = position;
    }

    /**
     * set attenuation attribute kC
     * @param kC new value
     * @return this
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }
    /**
     * set attenuation attribute kL
     * @param kL new value
     * @return this
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }
    /**
     * set attenuation attribute kQ
     * @param kQ new value
     * @return this
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p){
        // I0/(kC + kL) * d + kQ * d^2
        return intensity.scale(1/((kC+kL)*position.distance(p)+kQ*position.distanceSquared(p)));
    }

    @Override
    public Vector getL(Point p){

    }
}
