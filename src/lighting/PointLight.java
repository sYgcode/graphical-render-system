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

    /** allows us to narrow beam*/
    protected double narrowBeam = 1;

    /**
     * constructor inits Point Light's fields
     * @param intensity init intensity
     * @param position init position
     */
    public PointLight(Color intensity, Point position){
        super(intensity);
        this.position = position;
    }

    /**
     * set attenuation attribute kC
     * @param kC new value
     * @return this
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    /**
     * set attenuation attribute kL
     * @param kL new value
     * @return this
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    /**
     * set attenuation attribute kQ
     * @param kQ new value
     * @return this
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * sets narrow beam number
     * @param narrowBeam new narrow beam
     * @return this
     */
    public PointLight setNarrowBeam(double narrowBeam){
        this.narrowBeam = narrowBeam;
        return this;
    }

    @Override
    public Color getIntensity(Point p){
        // I0/(kC + kL) * d + kQ * d^2
        return intensity.scale(1/(kC+kL*position.distance(p)+kQ*position.distanceSquared(p)));
    }

    @Override
    public Vector getL(Point p){
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p){
        return position.distance(p);
    }
}
