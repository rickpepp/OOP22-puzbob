package it.unibo.puzbob.model;

/**
 * This interface calculate the physics about the shoted ball. The getBallPosition method return the position of the ball
 * after the time specified and the angle of the cannon. The isAttached method return the index of the matrix where 
 * need to be attached or null if not need to be attached.
 */

public interface Physics {
    
    /**
     * This method return the actual position of the ball after some time and some condition
     * @param flyingBall the ball in question
     * @param cannonAngle starting angle of the cannon
     * @param time the time elapsed from the start of the ball from the cannon
     * @return a pair with the absolute coordinates X and Y 
     */
    Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle, double time);

    /**
     * This method return if at the actual position of the ball there is a contact with other balls, and need to be attached somewhere.
     * @param wallHeight the actual position of the upper wall
     * @param matrixBall the actual matrix of the ball
     * @param ball the ball in question
     * @return a pair with X: column and Y: row of the matrix ball to attach
     */
    Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball);

}
