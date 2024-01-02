package geometries;

import primitives.Point;

/**
 * Triangle class inherits from Polygon
 */
public class Triangle extends Polygon{
    /**
     * constructor to initialize Triangle
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1, p2, p3);
    }

}
