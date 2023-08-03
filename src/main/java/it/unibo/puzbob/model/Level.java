package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

/**
 * This interface take a map of every coordinate of every color on the board and return a
 * matrix of balls.
 */

public interface Level {

    public Ball[][] getStartBalls(Map<String, List<Pair<Integer, Integer>>> ballsPosition);

}
