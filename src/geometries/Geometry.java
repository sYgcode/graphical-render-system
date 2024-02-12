package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * interface to be inherited from by sons
 */
public abstract class Geometry extends Intersectable {
    /** emission of Geometry, init at Black*/
    protected Color emission = Color.BLACK;
    /** represents material on geometry */
    private Material material = new Material();

    /**
     * gets the emission field for the Geometry
     * @return the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * gets the geometry's material
     * @return the material
     */
    public Material getMaterial(){return material;}
    /**
     * set the emission to something new
     * @param emission the new emission
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material){
        this.material = material;
        return this;
    }
    /**
     * function to calculate normal (vertical) vector from the given point
     * @param point certain point on the surface of the object to calculate the normal from
     * @return returns the normal vector
     */
    public abstract Vector getNormal(Point point);
}
