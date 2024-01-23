package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class for camera
 */
public class Camera implements Cloneable {
    /**
     * Point representing Camera location.
     */
    private Point loc;
    /**
     * direction vectors for camera.
     */
    private Vector Vto, Vup, Vright;
    /** height of view plane*/
    private double height = 0;
    /** distance of camera from view plane*/
    private double distance = 0;
    /** width of view plane*/
    private double width = 0;

    /** @return Camera location*/
    public Point getLoc(){return loc;}
    /** @return Camera to direction vector*/
    public Vector getVto(){return Vto;}
    /** @return Camera up direction vector*/
    public Vector getVup(){return Vup;}
    /** @return Camera right direction vector*/
    public Vector getVright(){return Vright;}
    /** @return Camera Height*/
    public double getHeight(){return height;}
    /** @return Camera Distance */
    public double getDistance(){return distance;}
    /** @return Camera Width*/
    public double getWidth(){return width;}

    /** default constructor */
    private Camera(){}

    /**
     * returns new Builder object
     * @return new Builder object
     */
    public static Builder getBuilder (){return null;}

    /**
     * constructs a ray through a pixel
     * @param nX row length
     * @param nY column height
     * @param j pixel index - column
     * @param i pixel index - row
     * @return null
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        //Image Center
        Point Pc = loc.add(Vto.scale(distance));

        //Ratio width and height
        double Ry = height/nY;
        double Rx = width/nX;

        //Pixel[i,j] center
        double yI = -(i-(nY-1)/2.0)*Ry;
        double xJ = (j-(nX-1)/2.0)*Rx;
        Point Pij = Pc;
        //make sure to account for Vector ZERO
        if(xJ != 0) { Pij = Pij.add(Vright.scale(xJ));}
        if(yI != 0) { Pij = Pij.add(Vup.scale(yI));}

        Vector Vij = Pij.subtract(loc);
        return new Ray(loc, Vij);
    }

    @Override
    public Camera clone() {
        try {
            Camera clone = (Camera) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    /**
     * nested builder class to build the camera
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * sets the camera location to the passed location
         * @param newLoc new location to be set to
         * @return this
         */
        public Builder setLocation(Point newLoc){
            camera.loc = newLoc;
            return this;
        }

        /**
         * sets direction of forward and upward. both must be orthogonal and normalized
         * @param forward forward direction vector
         * @param up upward direction vector
         * @return this
         */
        public Builder setDirection(Vector forward, Vector up) {
            if(forward.length() != 1 || up.length() != 1){
                throw new IllegalArgumentException("direction vector isn't normalized");
            }
            if(!isZero(alignZero(forward.dotProduct(up)))){
                throw new IllegalArgumentException("forward and upward directional vectors are not perpendicular");
            }

            camera.Vto = forward;
            camera.Vup = up;
            return this;
        }

        /**
         * sets View Plane's size
         * @param width width to set view plane to
         * @param height height to set view plane to
         * @return this
         */
        public Builder setVpSize (double width, double height){
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * sets view plane's distance from camera
         * @param distance distance to set to
         * @return this
         */
        public Builder setVpDistance (double distance){
            camera.distance = distance;
            return this;
        }

        /**
         * builds the camera and makes sure it is all initialized.
         * @return a finished camera object
         */
        public Camera build () {
            final String errorPrefix = "render data is missing.";
            final String errorSuffix = " is not initialized.";
            final String className = "Camera";
            //check if all are initialized
            if (isZero(camera.distance)){throw new MissingResourceException(errorPrefix, className, "distance" + errorSuffix);}
            if (isZero(camera.width)){throw new MissingResourceException(errorPrefix, className, "width" + errorSuffix);}
            if (isZero(camera.height)){throw new MissingResourceException(errorPrefix, className, "height" + errorSuffix);}

            // calculate Vright
            camera.Vright = camera.Vto.crossProduct(camera.Vup).normalize();

            return (Camera) camera.clone();
        }

    }

}
