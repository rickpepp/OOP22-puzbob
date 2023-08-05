package it.unibo.puzbob.model;

/**
 * This interface calculate the physics about the shoted ball. The getBallPosition method return the position of the ball
 * after the time specified and the angle of the cannon. The isAttached method return the index of the matrix where 
 * need to be attached or null if not need to be attached.
 */

public interface Physics {
    
    Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle, double time);

    Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball);

}
