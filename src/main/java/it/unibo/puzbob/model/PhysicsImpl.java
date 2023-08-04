package it.unibo.puzbob.model;

/**
 * This class calculate the physics about the shoted ball
 */

public class PhysicsImpl implements Physics {

    private Pair<Double, Double> boardDimension;
    private double velocity;
    private Pair<Double, Double> cannonPosition;

    public PhysicsImpl(Pair<Double, Double> boardDimension, double velocity, Pair<Double, Double> cannonPosition) {
        this.boardDimension = boardDimension;
        this.velocity = velocity;
        this.cannonPosition = cannonPosition;
    }

    // This method return the ball position after some time elapsed
    public Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle,
            double time) {

        return this.calcPosition(cannonPosition, cannonAngle, time, true, flyingBall.getBallSize() / 2);
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

    public Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball) {
        
        Pair<Double, Double> ballPosition = ball.getPosition();

        double ballDimension = ball.getBallSize();

        boolean result = false;

        // Calc the relative row index
        int rowIndex = (int) Math.floor((this.boardDimension.getY() - wallHeight - ballPosition.getY()) / ballDimension);
        int columnIndex = (int) Math.floor( ballPosition.getX() / ballDimension);

        // If it touch the upper wall and is empty add it
        if (rowIndex == 0 && matrixBall[rowIndex][columnIndex] == null) {
            return new Pair<Integer,Integer>(columnIndex, rowIndex);
        }

        result = result | isPresent(new Pair<Integer,Integer>(columnIndex - 1, rowIndex), matrixBall);
        result = result | isPresent(new Pair<Integer,Integer>(columnIndex + 1, rowIndex), matrixBall);
        result = result | isPresent(new Pair<Integer,Integer>(columnIndex, rowIndex + 1), matrixBall);
        result = result | isPresent(new Pair<Integer,Integer>(columnIndex, rowIndex - 1), matrixBall);

        if (rowIndex % 2 == 0) {
            result = result | isPresent(new Pair<Integer,Integer>(columnIndex - 1, rowIndex - 1), matrixBall);
            result = result | isPresent(new Pair<Integer,Integer>(columnIndex - 1, rowIndex + 1), matrixBall);
        } else {
            result = result | isPresent(new Pair<Integer,Integer>(columnIndex + 1, rowIndex - 1), matrixBall);
            result = result | isPresent(new Pair<Integer,Integer>(columnIndex + 1, rowIndex + 1), matrixBall);
        }
        
        if (result)
            return new Pair<Integer,Integer>(columnIndex, rowIndex);
        
        return null;

    }
    
    private boolean isPresent(Pair<Integer, Integer> indexes, Ball[][] matrixBall) {
        try {
            if (matrixBall[indexes.getY()][indexes.getX()] != null)
                return true;
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        
    }
}
