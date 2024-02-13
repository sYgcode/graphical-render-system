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
     * calculate color at given point using phong (kA*iA(iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi)))
     * @param intersection point to calculate at
     * @param ray to help with phong
     * @return Color at point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * calculate the local affect using phong (iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi))
     * @param gp geo point at which to calculate light
     * @param ray ray from camera
     * @return Local Effect
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        // +iE
        Color color= gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.direction;
        double nv= alignZero(n.dotProduct(v));
        if (nv== 0)
            return color;
        Material material = gp.geometry.getMaterial();
        //sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi)
        for (LightSource lightSource: scene.lights) {
            Vector l = lightSource.getL(gp.point);
            //lI*n
            double nl= alignZero(n.dotProduct(l));
            if (nl* nv> 0) {
                // sign(nl) == sign(nv)
                Color iL= lightSource.getIntensity(gp.point);
                // +iLi * (kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)
                color = color.add(iL.scale(calcDiffusive(material, nl, lightSource.getIntensity(gp.point))
                        .add(calcSpecular(material, n, l, nl, v, lightSource.getIntensity(gp.point)))));
            }
        }
        return color;
    }

    /**
     * calculates the diffusive. ((kD*abs(lI*n))
     * @param material material of geometry
     * @param nl dot product between normal and L
     * @param intensity intensity at the point we are calculating
     * @return Diffusive
     */
    private Double3 calcDiffusive(Material material, double nl, Color intensity){
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calculates the specular. (kS*(max(0, -v*r))^nSh)
     * @param material material of geometry
     * @param n normal from point
     * @param l our L from point to light
     * @param nl dot product between normal and L
     * @param v direction of ray
     * @param intensity intensity of light at the point we are calculating for
     * @return Specular
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v, Color intensity){
        // kS*(max(0, -v*r))
        Double3 specular = material.kS.scale(Math.max(0, v.scale(-1).dotProduct(calcReflection(l, n, nl))));
        // ^nShininess
        for(int i = 0; i < material.nShininess; i++){
            specular = specular.product(specular);
        }
        return specular;
    }

    /**
     * calculates Reflection vector (r = l-2*(n*l) * n)
     * @param l our L from point to light
     * @param n normal from point
     * @param nl dot product between normal and L
     * @return Relection Vector
     */
    private Vector calcReflection(Vector l, Vector n, double nl){
        return l.subtract(n.scale(nl*2));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray);
        if(list == null){return scene.background;}
        return calcColor(ray.findClosestGeoPoint(list), ray);
    }
}
