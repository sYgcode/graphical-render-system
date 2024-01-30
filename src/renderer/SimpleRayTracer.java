package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Point;
import scene.Scene;

import java.util.List;

/**
 * class inheriting class
 */
public class SimpleRayTracer extends RayTracerBase{
    /**
     * sets passed scene to scene
     * @param scene scene passed
     */
    public SimpleRayTracer(Scene scene){
        super(scene);
    }

    /**
     * calculate color at given point
     * @param point point to calcculate at
     * @return Color at point
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
    @Override
    public Color traceRay(Ray ray) {
        List<Point> list = scene.geometries.findIntersections(ray);
        if(list == null){return scene.background;}
        return calcColor(ray.findClosestPoint(list));
    }
}
