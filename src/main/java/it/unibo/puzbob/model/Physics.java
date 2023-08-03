package it.unibo.puzbob.model;

public interface Physics {
    
    Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle, double time);

}
