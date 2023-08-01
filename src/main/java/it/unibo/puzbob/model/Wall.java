package it.unibo.puzbob.model;

/**
 * This is the interface for the Wall. 
 */
public interface Wall {
    
    /** This method brings down the wall */
    void goDown(double size);

    //* This method return the position of the wall*/
    double getPosition();
}
