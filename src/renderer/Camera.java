package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class for camera
 */
public class Camera implements Cloneable {

    /** Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * <ul>     */
    private PixelManager pixelManager;

    /** how many threads to create */
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads
    /** spare threads in case of all cores */
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    /** print interval for percentage */
    private double printInterval = 0;    // printing progress percentage interval

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
    private Point viewPlaneCenter;

    /** imageWriter*/
    private ImageWriter imageWriter;
    /** raytracer */
    private RayTracerBase rayTracer;
    /** density of rays, represented by square root of actual density */
    private int density = 1;
    /** if at 0, supersampling is disabled. if higher then it represents recursion depth*/
    private int superSampling = 0;
    /** blackboard to use for each pixel*/
    private Blackboard blackboard;

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
    public static Builder getBuilder (){return new Builder();}

    /**
     * constructs a ray through a pixel
     * @param nX row length
     * @param nY column height
     * @param j pixel index - column
     * @param i pixel index - row
     * @return null
     */
    public List<Ray> constructRay(int nX, int nY, int j, int i){
        List<Ray> rays = new LinkedList<>();
        //Image Center

        //Ratio width and height
        double Ry = height/nY;
        double Rx = width/nX;

        //Pixel[i,j] center
        double yI = -(i-(nY-1)/2.0)*Ry;
        double xJ = (j-(nX-1)/2.0)*Rx;
        Point Pij = viewPlaneCenter;
        //make sure to account for Vector ZERO
        if(xJ != 0) { Pij = Pij.add(Vright.scale(xJ));}
        if(yI != 0) { Pij = Pij.add(Vup.scale(yI));}

        if(blackboard.getDensity() != 1){
            //set center point to center of pixel and set width and height
            blackboard = blackboard.setCenterPoint(Pij).setWidth(Rx).setHeight(Ry);
            // generate list of points on grid
            List<Point> points = blackboard.generateJitterGrid();
            for(Point point : points){
                //rays to point on grid
                rays.add(new Ray(loc, point.subtract(loc)));
            }
        }
        else {
            rays.add(new Ray(loc, Pij.subtract(loc)));
        }
        return rays;
    }

    /**
     * renders image
     */
    public void renderImage() {
        //get nx and ny from image writer
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        pixelManager = new PixelManager(Ny, Nx, printInterval);

        if (threadsCount == 0) {
            for (int i = 0; i < Nx; i++) {
                for (int j = 0; j < Ny; j++) {
                    this.castRay(Nx, Ny, i, j);
                }
            }
        } else if (threadsCount == -1) {
            IntStream.range(0, Ny).parallel()   //
                    .forEach(i -> IntStream.range(0, Nx).parallel() //
                            .forEach(j -> castRay(Nx, Ny, j, i)));
        } else {
            var threads = new LinkedList<Thread>();
            while (threadsCount-- > 0) threads.add(new Thread(() -> {
                PixelManager.Pixel pixel;
                while ((pixel = pixelManager.nextPixel()) != null) castRay(Nx, Ny, pixel.col(), pixel.row());
            }));
            for (var thread : threads) thread.start();
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
    }

    /**
     * calculates the average color of a list of points
     * @param rays list of rays to calculate color for
     * @return averaged color
     */
    private Color calcAvgColor(List<Ray> rays){
        Color color = Color.BLACK;
        for(Ray ray : rays){
            color = color.add(rayTracer.traceRay(ray));
        }

        return color.reduce(rays.size());
    }

    /**
     * casts a ray from said pixel
     * @param Nx amount of columns
     * @param Ny amount of rows
     * @param i index of column
     * @param j index of row
     */
    private void castRay(int Nx, int Ny, int i, int j){
        if (superSampling > 0){
            imageWriter.writePixel(i, j, constructRayAccelerator(Nx, Ny, j, i));
        }
        else{
            imageWriter.writePixel(i, j, calcAvgColor(constructRay(Nx, Ny, j, i)));
        }
        pixelManager.pixelDone();


    }

    /**
     * calculates Color of pixel using adaptive supersampling
     * @param Nx number of columns
     * @param Ny number of rows
     * @param i pixel index we are on
     * @param j pixel index we are on
     */
    private Color constructRayAccelerator(int Nx, int Ny, int j, int i){
        //Image Center

        //Ratio width and height
        double Ry = height/Ny;
        double Rx = width/Nx;

        //Pixel[i,j] center
        double yI = -(i-(Ny-1)/2.0)*Ry;
        double xJ = (j-(Nx-1)/2.0)*Rx;
        Point Pij = viewPlaneCenter;
        //make sure to account for Vector ZERO
        if(xJ != 0) { Pij = Pij.add(Vright.scale(xJ));}
        if(yI != 0) { Pij = Pij.add(Vup.scale(yI));}

        Blackboard blackboard1 = new Blackboard(Vup, Vright, 1);
        return constructRayAcceleratorHelper(Pij, Rx, Ry, superSampling, blackboard1, Color.BLACK);
    }

    /**
     * assisting function for supersampling, calculates color
     * @param Pij center point of target area
     * @param Rx width of target area
     * @param Ry height of target area
     * @param depth recursive max depth
     * @param blackboard1 blackboard item, used to optimize creation of objects
     * @param color color to average with
     * @return final pixel color
     */
    private Color constructRayAcceleratorHelper(Point Pij, double Rx, double Ry, int depth, Blackboard blackboard1, Color color){
        blackboard1 = blackboard1.setCenterPoint(Pij).setWidth(Rx).setHeight(Ry);
        List<Point> points = blackboard1.generateGrid();
        List<Ray> rays = new LinkedList<>();
        for(Point point : points){
            //rays to point on grid
            rays.add(new Ray(loc, point.subtract(loc)));
        }
        Color avgColor = calcAvgColor(rays);

        Color color0 = rayTracer.traceRay(rays.getFirst());
        if(color0.equalsMarg(avgColor)|| depth < 1){
            if(color.equals(Color.BLACK))
                color = color.add(avgColor);
            else
                color = color.add(avgColor).reduce(2);

            return color;
        }
        color = constructRayAcceleratorHelper(Pij.add(Vright.scale(-0.25*Rx)).add(Vup.scale(0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(Vright.scale(0.25*Rx)).add(Vup.scale(0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(Vright.scale(0.25*Rx)).add(Vup.scale(-0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(Vright.scale(-0.25*Rx)).add(Vup.scale(-0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        return color;
    }


    /**
     * creates a grid for the image writer
     * @param interval interval for size of each square
     * @param color color for grid to be in
     */
    public void printGrid(int interval, Color color){
        int i, j;
        for(i=0; i<width; i+=interval){
            for(j=0; j<height; j++){
                imageWriter.writePixel(i, j, color);
            }
        }
        for(i=0; i<height; i+=interval){
            for(j=0; j<width; j++){
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * writes to image
     */
    public void writeToImage(){imageWriter.writeToImage();}
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
         * sets image writer object
         * @param imageWriter image writer to set to
         * @return this
         */
        public Builder setImageWriter(ImageWriter imageWriter){
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * sets camera's ray tracer
         * @param rayTracer rayTracer to set to
         * @return this
         */
        public Builder setRayTracer(RayTracerBase rayTracer){
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * sets density of anti aliasing
         * @param density density represented in square root (if var is 2 then we will have 4 beams)
         * @return this
         */
        public Builder setDensity(int density){
            if(camera.density < 1){throw new IllegalArgumentException("Density is invalid, must be bigger then 1");}
            camera.density = density;
            return this;
        }

        /**
         * enables supersampling on the condition that density is already defined, otherwise remains disabled
         * @return this
         */
        public Builder setSuperSampling(){
            // calculates recursion depth by rounding as if we would scan the whole pixel as opposed to just parts of it
            camera.superSampling = (int)(Math.log(camera.density)/Math.log(2));
            return this;
        }

        /**
         * sets multithreading amount
         * @param threads amount of threads
         * @return this
         */
        public Builder setMultithreading(int threads) {
            if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if (threads >= -1) camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }

        /**
         * progress bar, only works in Eclipse
         * @param interval how often should it update
         * @return this
         */
        public Builder setDebugPrint(double interval) {
            camera.printInterval = interval;
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
            if (camera.imageWriter == null){throw new MissingResourceException(errorPrefix, className, "imageWriter" + errorSuffix);}
            if (camera.rayTracer == null){throw new MissingResourceException(errorPrefix, className, "height" + errorSuffix);}

            // calculate Vright
            camera.Vright = camera.Vto.crossProduct(camera.Vup).normalize();
            camera.viewPlaneCenter = camera.loc.add(camera.Vto.scale(camera.distance));

            camera.blackboard = new Blackboard(camera.Vup, camera.Vright, camera.density);

            return (Camera) camera.clone();
        }

    }

}
