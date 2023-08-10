package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

/**
 * This interface take a map of every coordinate of every color on the board and return a
 * matrix of balls.
 */

public interface Level {

    /**
     * This method return a matrix of ball from a map of "Color": List of pair with the coordinates (color and relative coordinates)
     * @param ballsPosition map with Key: name of the color, Value: list of pair with the coordinates of the ball in the matrix
     * @return a matrix of Ball with map in input 
     */
    public Ball[][] getStartBalls(Map<String, List<Pair<Integer, Integer>>> ballsPosition);

}
