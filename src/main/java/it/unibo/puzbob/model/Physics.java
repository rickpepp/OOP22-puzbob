package it.unibo.puzbob.model;

public interface Physics {
    
    Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle, double time);

    Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball);

}
