package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class of PDD Scene
 */
public class Scene {
    /** name of scene*/
    public String name;
    /** bg of scene*/
    public Color background = Color.BLACK;
    /** ambient light of scene*/
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** geometries in scene*/
    public Geometries geometries = new Geometries();
    /** constructor that assigns scene name*/
    public Scene(String name){this.name = name;}
    /**list of all lights in the scene */
    public List<LightSource> lights = new LinkedList<>();

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

    /**
     * setters sets lights
     * @param lights new lights to set to
     * @return this
     */
    public Scene setLights(List<LightSource> lights){
        this.lights = lights;
        return this;
    }


}
