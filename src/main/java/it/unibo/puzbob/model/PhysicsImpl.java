package it.unibo.puzbob.model;

/**
 * This class calculate the physics about the shoted ball. The calc position method return the position of the ball
 * after the time specified and the angle of the cannon. The isAttached method return the index of the matrix where 
 * need to be attached or null if not need to be attached.
 */

public class PhysicsImpl implements Physics {

    private Pair<Double, Double> boardDimension;
    private double velocity;
    private double ballDimension;
    private Pair<Double, Double> cannonPosition;

    public PhysicsImpl(Pair<Double, Double> boardDimension, double velocity, Pair<Double, Double> cannonPosition, double ballDimension) {
        this.boardDimension = boardDimension;
        this.velocity = velocity;
        this.cannonPosition = cannonPosition;
        this.ballDimension = ballDimension;
    }

    // This method return the ball position after some time elapsed
    public Pair<Double, Double> getBallPosition(FlyingBallImpl flyingBall, int cannonAngle,
            double time) {

        return this.calcPosition(cannonPosition, cannonAngle, time, true, this.ballDimension / 2);
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

    // Check if the flying ball need to be attached at the matrix balls
    public Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball) {
        
        Pair<Double, Double> ballPosition = ball.getPosition();

        boolean result = false;

        // Calc the relative row index
        int rowIndex = (int) Math.floor((this.boardDimension.getY() - wallHeight - ballPosition.getY()) / this.ballDimension);
        int columnIndex;

        if (rowIndex % 2 == 0) {
            columnIndex = (int) Math.floor( ballPosition.getX() / this.ballDimension);
        } else {
            if (ballPosition.getX() < this.ballDimension / 2) {
                columnIndex = 0;
            } else {
                columnIndex = (int) Math.floor( (ballPosition.getX() - (this.ballDimension / 2)) / this.ballDimension);
            }
        }
        
        Pair<Integer, Integer> possibleIndexes = new Pair<Integer,Integer>(columnIndex, rowIndex);

        // If it touch the upper wall and is empty add it. It need to touch the wall
        if (rowIndex == 0 && matrixBall[rowIndex][columnIndex] == null && ballPosition.getY() >= (this.boardDimension.getY() - (this.ballDimension / 2))) {
            return possibleIndexes;
        }

        // Maximum column value
        int maxColumnIndex = (int) Math.floor(this.boardDimension.getX() / this.ballDimension) - 1;

        // This near cells are in common between odd and even row
        // Left Ball
        Pair<Integer,Integer> nearIndexes = new Pair<Integer,Integer>(columnIndex - 1, rowIndex);
        result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

        // Right Ball
        nearIndexes = new Pair<Integer,Integer>(columnIndex + 1, rowIndex);
        result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

        // Upper Ball
        nearIndexes = new Pair<Integer,Integer>(columnIndex, rowIndex + 1);
        result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

        // Bottom Ball
        nearIndexes = new Pair<Integer,Integer>(columnIndex, rowIndex - 1);
        result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

        if (rowIndex % 2 == 0) {
            // Even Row
            // Bottom left Ball
            nearIndexes = new Pair<Integer,Integer>(columnIndex - 1, rowIndex - 1);
            result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

            // Upper left Ball
            nearIndexes = new Pair<Integer,Integer>(columnIndex - 1, rowIndex + 1);
            result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));
        } else {
            // Odd Row
            // Bottom Right Ball
            nearIndexes = new Pair<Integer,Integer>(columnIndex + 1, rowIndex - 1);
            result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));

            // Upper Right Ball
            nearIndexes = new Pair<Integer,Integer>(columnIndex + 1, rowIndex + 1);
            result = result | (isPresent(nearIndexes, matrixBall) & isNear(nearIndexes, ballPosition, wallHeight));
        }

        // If there are some adiacent balls return the index
        if (result && this.isValid(possibleIndexes, maxColumnIndex))
            return new Pair<Integer,Integer>(columnIndex, rowIndex);
        
        // Else return null
        return null;

    }
    
    // Check if there is a ball in the specified cell
    private boolean isPresent(Pair<Integer, Integer> indexes, Ball[][] matrixBall) {
        try {
            if (matrixBall[indexes.getY()][indexes.getX()] != null)
                return true;
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean isValid(Pair<Integer, Integer> actualBallIndexes,
        int maxColumnIndex) {

        if (actualBallIndexes.getX() > maxColumnIndex || (actualBallIndexes.getY() % 2 != 0 && actualBallIndexes.getX() > (maxColumnIndex - 1))) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isNear(Pair<Integer, Integer> nearBallIndexes, Pair<Double, Double> actualPosition, double wallHeight) {

        Double yNear = this.boardDimension.getY() - wallHeight - (this.ballDimension * nearBallIndexes.getY()) - (this.ballDimension / 2);
        Double xNear;

        if (nearBallIndexes.getY() % 2 == 0) 
            xNear = (this.ballDimension * nearBallIndexes.getX()) + (this.ballDimension / 2);
        else
            xNear = (this.ballDimension * nearBallIndexes.getX()) + this.ballDimension;

        double distance = Math.sqrt(Math.pow(actualPosition.getX() - xNear, 2) + Math.pow(actualPosition.getY() - yNear, 2));
        System.out.println("\nRow: " + nearBallIndexes.getY() + " Column: " + nearBallIndexes.getX());
        System.out.println("x: " + xNear + " y: " + yNear);
        System.out.println("Distance: " + distance + " value: " + (distance <= this.ballDimension));
        
        if (distance <= this.ballDimension)
            return true;
        
        return false;

    }
}
