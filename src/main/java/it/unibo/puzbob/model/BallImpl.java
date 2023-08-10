package it.unibo.puzbob.model;

/**
 * This is the implementation of the class ball. This can be used like a static ball.
 * If needed, can use FlyingBallImpl that is an extended version of this class for a moving ball in a two dimension space.
 */

public class BallImpl implements Ball {

    private String color;
    private int score;
    private double size;

    /**
     * This is the constructor of BallImpl
     * @param color the name of the color you want
     * @param score the score of the ball
     * @param size the diameter of the ball
     */
    public BallImpl(String color, int score, double size) {
        this.color = color;
        this.score = score;
        this.size = size;
    }

    /**
     * This is a getter for the color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * This is a getter for the score of the ball
     */
    public int getScore() {
        return this.score;
    }

    /**
     * This is a getter for the ball diameter
     */
    public double getBallSize() {
        return this.size;
    }

    public String toString() {
        return "Color: " + this.color + " Score: " + this.score + " Size: " + this.size;
    }
    
}
