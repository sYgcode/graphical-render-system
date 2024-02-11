package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class Ray Tracer Base
 */
public abstract class RayTracerBase {
    /** scene to trace ray in*/
    protected Scene scene;

    /**
     * takes a scene in
     * @param scene scene to take in
     */
    RayTracerBase(Scene scene){
        this.scene = scene;
    }

    /**
     * traces a ray and returns the color it hits
     * @param ray ray to trace
     * @return color to return
     */
    public abstract Color traceRay(Ray ray);
}
