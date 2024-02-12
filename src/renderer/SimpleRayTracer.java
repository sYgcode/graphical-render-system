package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;


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
     * @param ray to help with phong
     * @return Color at point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color= gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.direction;
        double nv= alignZero(n.dotProduct(v));
        if (nv== 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource: scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl= alignZero(n.dotProduct(l));
            if (nl* nv> 0) {
                // sign(nl) == sing(nv)
                Color iL= lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl, lightSource.getIntensity(gp.point))
                        .add(calcSpecular(material, n, l, nl, v, lightSource.getIntensity(gp.point))));
            }
        }
        return color;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray);
        if(list == null){return scene.background;}
        return calcColor(ray.findClosestGeoPoint(list), ray);
    }
}
