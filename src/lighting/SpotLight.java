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
    SpotLight(Color intensity, Vector direction, Point position){
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public PointLight setkC(double kC){
        super.setkC(kC);
        return this;
    }
    @Override
    public PointLight setkL(double kL){
        super.setkL(kL);
        return this;
    }
    @Override
    public PointLight setkQ(double kQ){
        super.setkQ(kQ);
        return this;
    }
}
