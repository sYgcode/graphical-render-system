package primitives;

/**
 * class representing a Material
 */
public class Material {
    /**Double3's to represent attenuation attributes */
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    //hw page says init at 0, presentation says 1
    /** represents how concentrated the shininess is*/
    public int nShininess = 1;

    /**
     * set kD to a new value
     * @param kD new Double3 value
     * @return this
     */
    public Material setKd(Double3 kD){
        this.kD = kD;
        return this;
    }
    /**
     * set kD to a new value
     * @param kD new double value
     * @return this
     */
    public Material setKd(double kD){
        this.kD = new Double3(kD);
        return this;
    }
    /**
     * set kS to a new value
     * @param kS new Double3 value
     * @return this
     */
    public Material setKs(Double3 kS){
        this.kS = kS;
        return this;
    }
    /**
     * set kS to a new value
     * @param kS new double value
     * @return this
     */
    public Material setKs(double kS){
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * sets the shininess variable to a new value
     * @param nShininess new value
     * @return this
     */
    public Material setShininess(int nShininess){
        this.nShininess = nShininess;
        return this;
    }
}
