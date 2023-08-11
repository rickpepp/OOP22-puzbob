package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class calculate the physics about the shoted ball. The calc position method return the position of the ball
 * after the time specified and the angle of the cannon. The isAttached method return the index of the matrix where 
 * need to be attached or null if not need to be attached.
 */

public class PhysicsImpl implements Physics {

    private static final double COMPLEMENTARY_ANGLE = 90;
    private static final double BALL_ANGLE = 30;

    private Pair<Double, Double> boardDimension;
    private double velocity;
    private double ballDimension;
    private Pair<Double, Double> cannonPosition;

    private double rowDistance;

    /**
     * This is the constructor of physics
     * @param boardDimension the absolute dimension of the world
     * @param velocity the velocity of the ball
     * @param cannonPosition the actual cannon position
     * @param ballDimension the diametre of the ball
     */
    public PhysicsImpl(Pair<Double, Double> boardDimension, double velocity, Pair<Double, Double> cannonPosition, double ballDimension) {
        this.boardDimension = boardDimension;
        this.velocity = velocity;
        this.cannonPosition = cannonPosition;
        this.ballDimension = ballDimension;
        this.rowDistance = this.ballDimension * Math.cos(Math.toRadians(BALL_ANGLE));
    }

    /**
     * This method return the ball position after some time elapsed
     */
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
        double angle = Math.toRadians(cannonAngle - COMPLEMENTARY_ANGLE);
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

    /**
     * Check if the flying ball need to be attached at the matrix balls
     */
    public Pair<Integer, Integer> isAttached(double wallHeight, Ball[][] matrixBall, FlyingBallImpl ball) {
        
        Pair<Double, Double> ballPosition = ball.getPosition();

        boolean result = false;

        // Calc the relative row index
        //int rowIndex = (int) Math.floor((this.boardDimension.getY() - wallHeight - ballPosition.getY()) / this.ballDimension);
        int rowIndex = (int) Math.floor(((this.boardDimension.getY() - wallHeight - ballPosition.getY() - 
            ((this.ballDimension - this.rowDistance) / 2)) / this.rowDistance));
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
        List<Pair<Integer,Integer>> neighbourList = new ArrayList<>();

        // Left Ball
        neighbourList.add(new Pair<Integer,Integer>(columnIndex - 1, rowIndex));

        // Right Ball
        neighbourList.add(new Pair<Integer,Integer>(columnIndex + 1, rowIndex));

        // Upper Ball
        neighbourList.add(new Pair<Integer,Integer>(columnIndex, rowIndex + 1));

        // Bottom Ball
        neighbourList.add(new Pair<Integer,Integer>(columnIndex, rowIndex - 1));

        if (rowIndex % 2 == 0) {
            // Even Row
            // Bottom left Ball
            neighbourList.add(new Pair<Integer,Integer>(columnIndex - 1, rowIndex - 1));

            // Upper left Ball
            neighbourList.add(new Pair<Integer,Integer>(columnIndex - 1, rowIndex + 1));
        } else {
            // Odd Row
            // Bottom Right Ball
            neighbourList.add(new Pair<Integer,Integer>(columnIndex + 1, rowIndex - 1));

            // Upper Right Ball
            neighbourList.add(new Pair<Integer,Integer>(columnIndex + 1, rowIndex + 1));
        }

        // Check if there are any ball near to attach
        for (Pair<Integer, Integer> neighbour: neighbourList) {
            result = result | (isPresent(neighbour, matrixBall) & isNear(neighbour, ballPosition, wallHeight));
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

    // Check if the index are out of the board
    private boolean isValid(Pair<Integer, Integer> actualBallIndexes,
        int maxColumnIndex) {

        if (actualBallIndexes.getX() > maxColumnIndex || (actualBallIndexes.getY() % 2 != 0 && actualBallIndexes.getX() > (maxColumnIndex - 1))) {
            return false;
        } else {
            return true;
        }
    }

    // Check if two balls are tounching
    private boolean isNear(Pair<Integer, Integer> nearBallIndexes, Pair<Double, Double> actualPosition, double wallHeight) {

        // Calc near ball position
        Double yNear = this.boardDimension.getY() - wallHeight - (this.rowDistance * nearBallIndexes.getY()) - (this.ballDimension / 2);
        Double xNear;

        // x coordinate depends if the row is odd or even
        if (nearBallIndexes.getY() % 2 == 0) 
            xNear = (this.ballDimension * nearBallIndexes.getX()) + (this.ballDimension / 2);
        else
            xNear = (this.ballDimension * nearBallIndexes.getX()) + this.ballDimension;

        // Calc the distance between the two balls
        double distance = Math.sqrt(Math.pow(actualPosition.getX() - xNear, 2) + Math.pow(actualPosition.getY() - yNear, 2));
        
        // If this is equal or minor of ballDimension, they are tounching
        if (distance <= this.ballDimension)
            return true;
        
        return false;

    }
}
