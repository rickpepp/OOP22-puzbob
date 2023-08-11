package it.unibo.puzbob.view;

import org.json.JSONObject;

import it.unibo.puzbob.model.Pair;

/**
 * This is a basic interface for an output source. It take in input a JSONObject that contain the information
 * about the state of the actual world. It is done in json to have a separated view and model.
 */

public interface Output {
    
    /**
     * This render in the output the JSONObject in input
     * @param world a JSONObject that describe the actuale state of the world
     */
    public void displayGame(JSONObject world);

    /** Getter method to get the size of  the board
     * @return a pair of double whose values are: height, width
     */
    Pair<Double,Double> getBoardDimension();

    /** Getter method for the size of the ball
     * @return the diameter of the ball
     */
    double getSizeBall();
}
