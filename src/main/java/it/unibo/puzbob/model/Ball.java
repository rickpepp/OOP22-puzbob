package it.unibo.puzbob.model;

/**
 * This is an interface for a simple ball. This is the base for both a static ball and a flying ball. 
 */

public interface Ball {

    /**
     * This is a getter for the color of the ball
     * @return a string that show the color of the ball
     */
    public String getColor();

    /**
     * This is a getter for the score of the ball
     * @return the score of the ball
     */
    public int getScore();

    /**
     * This return the diameter of the ball
     * @return the diameter of the ball
     */
    public double getBallSize();

}
