package it.unibo.puzbob.model;

/**
 * This is the interface for the Wall. 
 */
public interface Wall {
    
    /** Method brings down the wall
     * @param size is the value of how much tolower the wall
     */
    void goDown(double size);

    /** Getter method return the position of the wall
     * @return the value of how low  the wall is
    */
    double getPosition();
}
