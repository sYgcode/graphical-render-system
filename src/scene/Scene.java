package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * class of PDD Scene
 */
public class Scene {
    /** name of scene*/
    public String name;
    /** bg of scene*/
    public Color background;
    /** ambient light of scene*/
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** geometries in scene*/
    public Geometries geometries = new Geometries();
    /** constructor that assigns scene name*/
    public Scene(String name){this.name = name;}

    /** setter sets bg*/
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    /** setter sets ambient light*/
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    /** setter sets geometries*/
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
