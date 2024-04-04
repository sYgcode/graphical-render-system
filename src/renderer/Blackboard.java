package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * blackboard to represent target area for each pixel
 */
public class Blackboard {
    /** point to represent pixel center */
    private Point p0;
    /** Vector upwards */
    private Vector Vup;
    /** Vector rightwards */
    private Vector Vright;
    /** width of target area*/
    private double width;
    /** height of target area */
    private double height;
    /** square root of the amount of rays we'll want to have*/
    private int density;
    /** distance between each node on graph on X axis */
    private double dX;
    /** distance between each node on graph on Y axis */
    private double dY;

    /**
     * constructor inits blackboard
     * @param Vup toward vector of camera
     * @param Vright rightwards vector of camera
     * @param density density
     */
    public Blackboard(Vector Vup, Vector Vright, int density){
        this.Vup = Vup.normalize();
        this.Vright = Vright.normalize();
        this.density = density;
    }

    /**
     * set width of target area to 0.1 width of pixel
     * @param width width of pixel
     * @return this
     */
    public Blackboard setWidth(double width){
        this.width = width;
        this.dX = this.width/density;
        return this;
    }

    /**
     * set height of target area to 0.1 height of pixel
     * @param height height of pixel
     * @return this
     */
    public Blackboard setHeight(double height){
        this.height = height;
        this.dY = this.height/density;
        return this;
    }

    /**
     * gets density
     * @return density
     */
    public int getDensity(){
        return density;
    }

    /**
     * set center point of blackboard
     * @param p0 center point
     * @return this
     */
    public Blackboard setCenterPoint(Point p0){
        this.p0 = p0;
        return this;
    }

    /**
     * generate a grid densityXdensity with random jitter
     * @return list of points on the grid
     */
    public List<Point> generateJitterGrid(){
        List<Point> points = new LinkedList<>();
        //bring our point up to the corner
        Point topleft = p0.add(Vright.scale(-0.5*width)).add(Vup.scale(0.5*height));
        Point p;
        for(int j = 0; j < density; j++){
            p = topleft;
            //deal with vector zero
            if(!isZero(j)) {
                p = p.add(Vright.scale(dX*j));
            }
            for(int i = 0; i < density; i++){
                if(!isZero(i)) {
                    p = p.add(Vup.scale((-dY)+random(-0.1*dX, 0.1*dX)).add(Vright.scale(random(-0.1*dY, 0.1*dY))));
                }
                points.add(p);
            }
        }
        return points;
    }
}
