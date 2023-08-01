package it.unibo.puzbob.model;

/**
 * This is an interface for a ball factory for both a static ball and a flying ball.
 */

public interface BallFactory {

    public Ball createStaticBall(String color);

    public Ball createFlyingBall(String color);

}
