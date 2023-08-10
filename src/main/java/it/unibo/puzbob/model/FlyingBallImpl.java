package it.unibo.puzbob.model;

/**
 * This is the extended version of BallImpl. In this version there is a new property "position"
 * in form of a "Pair" class, so this is a ball in a two dimensional space.
 */

public class FlyingBallImpl extends BallImpl {

    private Pair<Double, Double> position;

    /**
     * This is the constructo of a ball with position
     * @param color the name of the color you want
     * @param score the score of the ball
     * @param size the diameter of the ball
     * @param position the starting position of the ball
     */
    public FlyingBallImpl(String color, int score, double size, Pair<Double, Double> position) {
        super(color, score, size);
        this.position = position;
    }

    /**
     * This is a getter for the absolute position of the ball in the board
     * @return a pair with the X and Y coordinates
     */
    public Pair<Double, Double> getPosition() {
        return this.position;
    }

    /**
     * This is a setter for the absolute position of the world
     * @param newPosition the new position of the ball(X and Y)
     */
    public void setPosition(Pair<Double, Double> newPosition) {
        this.position = newPosition;
    }

    public String toString() {
        return super.toString() + " Position X: " + this.getPosition().getX() + " Position Y: " + this.getPosition().getY();
    }
    
}
