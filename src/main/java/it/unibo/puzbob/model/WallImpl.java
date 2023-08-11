package it.unibo.puzbob.model;

/** This is the implementation of the Wall interface */
public class WallImpl implements Wall{

    private double height; 

    /** Constructor basic: the height of the wall is at 0 */
    public WallImpl(){
        this.height = 0.0;
    }

    /** Constructor with wall height as input
     * @param size is the height from which the wall is to start
     */
    public WallImpl(double size){
        this.height = size;
    }

    /** Method modify the height of wall 
     * @param size indicates how much the wall should be lowered
    */
    public void goDown(double size) {
        this.height += size;
    }

    /** Getter method return the height of wall */
    public double getPosition() {
        return this.height;
    }

    public String toString(){
        return "Wall height is: " + this.height;
    }
    
}
