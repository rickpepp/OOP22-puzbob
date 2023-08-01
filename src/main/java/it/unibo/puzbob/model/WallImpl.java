package it.unibo.puzbob.model;

/** This is the implementation of the wall.*/
public class WallImpl implements Wall{

    private double height; 

    public WallImpl(){
        this.height = 0.0;
    }

    public WallImpl(double size){
        this.height = size;
    }

    /** This method modify the height of wall */
    public void goDown(double size) {
        this.height += size;
    }

    /** This method return the height of wall */
    public double getPosition() {
        return this.height;
    }

    public String toString(){
        return "Wall height is: " + this.height;
    }
    
}
