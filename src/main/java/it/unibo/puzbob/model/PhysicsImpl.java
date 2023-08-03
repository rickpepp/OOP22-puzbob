package it.unibo.puzbob.model;

public class PhysicsImpl implements Physics {

    private Pair<Double, Double> boardDimension;
    private double velocity;

    public PhysicsImpl(Pair<Double, Double> boardDimension, double velocity) {
        this.boardDimension = boardDimension;
        this.velocity = velocity;
    }

    public Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle,
            double time) {

        return this.calcPosition(flyingBall.getPosition(), cannonAngle, time, true, flyingBall.getBallSize() / 2);
    }

    private Pair<Double, Double> calcPosition(Pair<Double, Double> startingPosition, int cannonAngle,
            double time, boolean positive, double halfBallDimension) {
        
        double x;
        double y;
        double angle = Math.toRadians(cannonAngle) - (Math.PI / 2.0);

        if (positive) {
            x = startingPosition.getX() + 
                (this.velocity * time * Math.sin(angle));
        } else {
            x = startingPosition.getX() -
                (this.velocity * time * Math.sin(angle));
        }
        
        if (x < 0 + halfBallDimension) {
            x = 0 + halfBallDimension;
            y = this.getYBouncing(startingPosition, angle);
            double newTime = this.calcNewTime(x, y - startingPosition.getY(), time);

            // Next Row check bouncing
            // System.out.println("x " + x + " y " + y + " time " + newTime + " positive " + positive);

            return this.calcPosition(new Pair<Double,Double>(x, y), cannonAngle, newTime, !positive, halfBallDimension);
        } else if (x > this.boardDimension.getX() - halfBallDimension) {
            x = this.boardDimension.getX() - halfBallDimension;
            y = this.getYBouncing(startingPosition, angle);
            double newTime = this.calcNewTime(x, y - startingPosition.getY(), time);

            // Next Row check bouncing
            // System.out.println("x " + x + " y " + y + " time " + newTime + " positive " + positive);

            return this.calcPosition(new Pair<Double,Double>(x, y), cannonAngle, newTime, !positive, halfBallDimension);
        } 

        y = startingPosition.getY() + 
            (this.velocity * time * Math.cos(angle));

        return new Pair<Double,Double>(x, y);
    }

    private double getYBouncing(Pair<Double, Double> startingPosition, double angle) {
        if (startingPosition.getX() == (this.boardDimension.getX() / 2)) {
            return startingPosition.getY() + Math.abs((0.5 * this.boardDimension.getX() * (Math.cos(angle) / Math.sin(angle))));
        } else {
            return startingPosition.getY() + Math.abs(this.boardDimension.getX() * (Math.cos(angle) / Math.sin(angle)));
        }
    }

    private double calcNewTime(double x, double y, double time) {
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double timeToSubtract = distance / this.velocity;
        return time - timeToSubtract;
    }
    
}
