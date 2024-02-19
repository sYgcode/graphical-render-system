package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

/**
 * interface inherited by all geometries
 */
public abstract class Intersectable {
    /**
     * method to receive a ray and find all the points it intersects with the geometry.
     * @param ray ray to check for intersections
     * @return list of the points that intersect with the geometry and ray
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * finds GeoPoint intersections
     * @param ray ray to find intersections on
     * @return list of points ray intersects with
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * finds GeoPoint intersections with a maximum distance
     * @param ray ray to find intersections on
     * @param maxDistance maxdistance to check on
     * @return list of points ray intersects with
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * helps find GeoPoint intersections
     * @param ray ray to look for intersections on
     * @param maxDistance maximum distance to check for
     * @return list of points ray intersects with
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * class to hold Geometry and Point
     */
    public static class GeoPoint {
        /**
         * Geometry point is on
         */
        public Geometry geometry;
        /** Point */
        public Point point;

        /** constructor, inits geometry and point to new values*/
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint other)
                    && this.point.equals(other.point)
                    && this.geometry == other.geometry;
        }
        @Override
        public String toString(){
            return "Point: " + point.toString() + " Geometry: "  + geometry.toString();
        }
    }
}
