package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

/**
 * This is the implementation of level. This class return the configuration
 * of the board passed in input.
 */

public class LevelImpl implements Level {

    private BallFactory ballFactory;
    private Ball[][] starterBalls;

    /**
     * Initialize the matrix
     * @param ballFactory a ball factory to create balls
     * @param dimension diameter of the ball
     */
    public LevelImpl(BallFactory ballFactory, Pair<Integer, Integer> dimension) {
        this.ballFactory = ballFactory;
        this.starterBalls = new Ball[dimension.getX()][dimension.getY()];
    }

    /**
     * This method return a matrix of ball from a map of "Color": List of pair with coordinates (color and relative coordinates)
     */
    public Ball[][] getStartBalls(Map<String, List<Pair<Integer, Integer>>> ballsPosition) {
        
        // Check every color in the map
        for (String color: ballsPosition.keySet()) {

            // Check every coordinates of a color
            for (Pair<Integer, Integer> ball: ballsPosition.get(color)) {

                // If the position is free create the ball of the color specified
                if (this.starterBalls[ball.getX()][ball.getY()] != null) {
                    
                    try {
                        throw new Exception("The next ball position is already occupated");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    this.starterBalls[ball.getX()][ball.getY()] = this.ballFactory.createStaticBall(color);
                }
                
            }
        }
        return this.starterBalls;
    }
    
}
