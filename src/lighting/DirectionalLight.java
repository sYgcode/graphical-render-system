package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Directional Light class.
 */
public class DirectionalLight extends Light implements LightSource{
    /** vector representing direction */
    private Vector direction;

    /**
     * constructor inits Directional Light's fields
     * @param intensity init intensity
     * @param direction init direction
     */
    public DirectionalLight(Color intensity, Vector direction){
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        // in directional light it's equal everywhere
        return getIntensity();
    }

    @Override
    public Vector getL(Point p){
        //with directional light it is the same as the directionS
        return direction.normalize();
    }

    @Override
    public double getDistance(Point p){
        return Double.POSITIVE_INFINITY;
    }
}
