package it.unibo.puzbob.model;

/**
 * This class calculate the physics about the shoted ball
 */

public class PhysicsImpl implements Physics {

    private Pair<Double, Double> boardDimension;
    private double velocity;

    public PhysicsImpl(Pair<Double, Double> boardDimension, double velocity) {
        this.boardDimension = boardDimension;
        this.velocity = velocity;
    }

    // This method return the ball position after some time elapsed
    public Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle,
            double time) {

        return this.calcPosition(flyingBall.getPosition(), cannonAngle, time, true, flyingBall.getBallSize() / 2);
    }

    private Pair<Double, Double> calcPosition(Pair<Double, Double> startingPosition, int cannonAngle,
            double time, boolean positive, double halfBallDimension) {

        // This resolve some calc problem that accour when the ball is too close to the wall
        if (time < 0) {
            return startingPosition;
        }
        
        // The angle need to be in radians
        double angle = Math.toRadians(cannonAngle - 90);
        double x = startingPosition.getX();
        double y;

        // Positive change the direction after a bouncing
        if (positive) {
            x += (this.velocity * time * Math.sin(angle));
        } else {
            x -= (this.velocity * time * Math.sin(angle));
        }

        // If the ball after this time bounce
        if (x < 0 + halfBallDimension || x > this.boardDimension.getX() - halfBallDimension) {

            // Put the x in one of the extreme point
            if (x < 0 + halfBallDimension)
                 x = 0 + halfBallDimension;
            else if (x > this.boardDimension.getX() - halfBallDimension)
                 x = this.boardDimension.getX() - halfBallDimension;

            // getYBouncing calc the y coordinate
            y = this.getYBouncing(startingPosition, angle, x);

            // newtime is the time left to the movement
            double newTime = this.calcNewTime(Math.abs(x - startingPosition.getX()),
                y - startingPosition.getY(), time);

            // Call this function recursively
            return this.calcPosition(new Pair<Double,Double>(x, y), 
                cannonAngle, newTime, !positive, halfBallDimension);
        }

        // If is not recursive, calc the y coordinate
        y = startingPosition.getY() + 
            (this.velocity * time * Math.cos(angle));

        // Return the result
        return new Pair<Double,Double>(x, y);
    }

    // Calc the y when the ball bounces
    private double getYBouncing(Pair<Double, Double> startingPosition, double angle, double x) {

        return startingPosition.getY() + (Math.abs(x - startingPosition.getX()) * Math.cos(angle));

    }

    // Calc the time left to the movement after the bounce
    private double calcNewTime(double x, double y, double time) {

        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double timeToSubtract = distance / this.velocity;
        return time - timeToSubtract;

    }
    
}
