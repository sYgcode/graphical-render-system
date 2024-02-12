package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Point;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;


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
     * @param point point to calculate at
     * @return Color at point
     */
    private Color calcColor(GeoPoint point){
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray);
        if(list == null){return scene.background;}
        return calcColor(ray.findClosestGeoPoint(list));
    }
}
