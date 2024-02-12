package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Spot Light class
 */
public class SpotLight extends PointLight{
    /** represents direction of light. */
    private Vector direction;

    /**
     * constructor to init Point Light's fields
     * @param intensity init intensity
     * @param direction init direction
     * @param position init position
     */
    public SpotLight(Color intensity, Point position, Vector direction){
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public PointLight setKc(double kC){
        super.setKc(kC);
        return this;
    }
    @Override
    public PointLight setKl(double kL){
        super.setKl(kL);
        return this;
    }
    @Override
    public PointLight setKq(double kQ){
        super.setKq(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p){
        // I0*max(0, dir*l/(kC + kL) * d + kQ * d^2
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
    }
}
