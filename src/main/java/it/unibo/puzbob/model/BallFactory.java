package it.unibo.puzbob.model;

/**
 * This is an interface for a ball factory for both a static ball and a flying ball.
 */

public interface BallFactory {

    /**
     * This return a Ball without the position information
     * @param color the color you want the ball
     * @return the ball
     */
    public Ball createStaticBall(String color);

    /**
     * This return a Ball with the position information
     * @param color the color you want the ball
     * @param position Pair with the coordinates of the FlyingBall
     * @return the Ball
     */
    public Ball createFlyingBall(String color, Pair<Double, Double> position);

    /**
     * This is a getter to have the ball diameter
     * @return the ball diameter
     */
    public Double getBallDimension();

}
