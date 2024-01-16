package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Geometries implements Intersectable{
    private List<Intersectable> geometries = null;

    /**
     * default constructor to initialize linked list
     */
    public Geometries() {
        geometries = new LinkedList<>();
    }

    /**
     * constructor to add all the geometries
     * @param geometries geometries to init with
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

    /**
     * adds all passed geometries to the collection
     * @param geometries geometries to add
     */
    public void add(Intersectable... geometries){
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable item : geometries) {
            List<Point> itemList = item.findIntersections(ray);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }
}
