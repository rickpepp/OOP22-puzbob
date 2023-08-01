package it.unibo.puzbob.model;

/**
 * This is the extended version of BallImpl. In this version there is a new property "position"
 * in form of a "Pair" class, so this is a ball in a two dimensional space.
 */

public class FlyingBallImpl extends BallImpl {

    private Pair<Double, Double> position;

    public FlyingBallImpl(String color, int score, double size, Pair<Double, Double> position) {
        super(color, score, size);
        this.position = position;
    }

    public Pair<Double, Double> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Double, Double> newPosition) {
        this.position = newPosition;
    }

    public String toString() {
        return super.toString() + " Position X: " + this.getPosition().getX() + " Position Y: " + this.getPosition().getY();
    }
    
}
