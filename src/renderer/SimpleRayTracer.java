package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * class inheriting class
 */
public class SimpleRayTracer extends RayTracerBase{

    /** recursion stop constant */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /** recursion stop constant */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /** initial recursion k */
    private static final Double3 INITIAL_K = Double3.ONE;



    /**
     * sets passed scene to scene
     * @param scene scene passed
     */
    public SimpleRayTracer(Scene scene){
        super(scene);
    }

    /**
     * checks if a point is shaded or not
     * @param gp geo point to check
     * @param l direction vector of light to point
     * @param n normal of point
     * @param lightSource light source to check for
     * @return true if unshaded
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        if(gp.geometry.getMaterial().kT == Double3.ZERO){
            return true;
        }
        Vector lightDirection = l.scale(-1); // from point to light source


        Ray ray= new Ray(gp.point, n, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, lightSource.getDistance(ray.head));
        if (intersections == null){
            return true;
        }

        // check if the transparant level is too small to matter
        for (var item : intersections){
            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K)){
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates level of shadow for different levels of transparency
     * @param lightSource lightsource to calculate from
     * @param l L
     * @param n normal
     * @param gp point to calculate for
     * @return double3 representing transparency
     */
    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point point = gp.point;
        Ray lightRay = new Ray(point, n, lightDirection);

        double maxdistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxdistance);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
//        loop over intersections and for each intersection which is closer to the
//        point than the light source multiply ktr by 𝒌𝑻 of its geometry.
//        Performance:
//        if you get close to 0 –it’s time to get out( return 0)
        for (var geo : intersections) {
            ktr = ktr.product(geo.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * calculate color at given point using phong (kA*iA(iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi)))
     * @param gp point to calculate at
     * @param ray to help with phong
     * @return Color at point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }
    /**
     * calculate color at given point using phong (kA*iA(iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi)))
     * @param gp point to calculate at
     * @param ray to help with phong
     * @return Color at point
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calculate the global effect of an object
     * @param ray ray
     * @param kx kx test
     * @param level recursion level
     * @param k our k val
     * @return color effect
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) { Double3 kkx= kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp= findClosestIntersection(ray);
        return (gp == null ? scene.background: calcColor(gp, ray,level -1, kkx)) .scale(kx);
    }

    /**
     * calculate the global effects in total
     * @param gp point
     * @param ray ray
     * @param level level of recursion
     * @param k our k val
     * @return color of global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,int level, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Material material= gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp.point, ray.direction, n), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp.point, ray.direction, n), material.kR, level, k));
    }

    /**
     * find the closest intersection to the ray out of all the geometries
     * @param ray ray to calculate for
     * @return closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * construct the refracted ray but first make sure nv is zero
     * @param point point to caclulate for
     * @param v direction
     * @param n normal
     * @return secondary ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        double vn = v.dotProduct(n);

        if (isZero(alignZero(vn))) {
            return null;
        }
        return new Ray(point, n, v);
    }

    /**
     * construct reflected ray using equation in presentation
     * @param point point to calculate for
     * @param v direction
     * @param n normal
     * @return secondary ray
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        //r = v - 2.(v.n).n
        double vn = v.dotProduct(n);

        if (isZero(alignZero(vn))) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(point, n, r);
    }

    /**
     * calculate the local affect using phong (iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi))
     * @param gp geo point at which to calculate light
     * @param ray ray from camera
     * @return Local Effect
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        //less access time
        Geometry geometry = gp.geometry;
        Point point = gp.point;
        Color intensity;
        // +iE
        Color color = geometry.getEmission();
        Vector n = geometry.getNormal(gp.point);
        Vector v = ray.direction.normalize();
        double nv= alignZero(n.dotProduct(v));
        if (nv== 0)
            return color;
        Material material = geometry.getMaterial();
        //sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi)
        for (LightSource lightSource: scene.lights) {
            //less access time
            intensity = lightSource.getIntensity(point);
            Vector l = lightSource.getL(point);
            //lI*n
            double nl= alignZero(n.dotProduct(l));
            if (nl* nv> 0) {
                // sign(nl) == sign(nv)
                Double3 ktr = transparency(lightSource, l, n, gp);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    // +iLi * (kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)
                    color = color.add(iL.scale(calcDiffusive(material.kD, nl, intensity)
                            .add(calcSpecular(material.kS, material.nShininess, n, l, nl, v, intensity))));
                }
            }
        }
        return color;
    }

    /**
     * calculates the diffusive. ((kD*abs(lI*n))
     * @param kD kD of material of geometry so as to save time by not sending all of Material
     * @param nl dot product between normal and L
     * @param intensity intensity at the point we are calculating
     * @return Diffusive
     */
    private Double3 calcDiffusive(Double3 kD, double nl, Color intensity){
        return kD.scale(Math.abs(nl));
    }

    /**
     * calculates the specular. (kS*(max(0, -v*r))^nSh)
     * @param kS kD of material of geometry so as to save time by not sending all of Material
     * @param nShininess integer to represent shininess
     * @param n normal from point
     * @param l our L from point to light
     * @param nl dot product between normal and L
     * @param v direction of ray
     * @param intensity intensity of light at the point we are calculating for
     * @return Specular
     */
    private Double3 calcSpecular(Double3 kS, int nShininess, Vector n, Vector l, double nl, Vector v, Color intensity){
        // kS*(max(0, -v*r))
        return kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(calcReflection(l, n, nl))),nShininess));
    }

    /**
     * calculates Reflection vector (r = l-2*(n*l) * n)
     * @param l our L from point to light
     * @param n normal from point
     * @param nl dot product between normal and L
     * @return Relection Vector
     */
    private Vector calcReflection(Vector l, Vector n, double nl){
        return l.subtract(n.scale(nl*2)).normalize();
    }

    @Override
    public Color traceRay(Ray ray) {
//        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray);
//        if(list == null){return scene.background;}
        GeoPoint closestPoint = findClosestIntersection(ray);
        if(closestPoint == null){return scene.background;}
        return calcColor(closestPoint, ray);
    }
}
