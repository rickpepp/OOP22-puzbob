package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * This interface is implemented in JSONParserImpl. The parseColors method take in input a JSONObject
 * (read by the JSONReader for example) and return a map where the keys are the colors, and the value is
 * the score of every Color. The parserStarterBalls method take a JSONObject too, but it return a map
 * of key: color of the balls, and the values are a list of coordinates for every ball of that color in
 * the starting board.
 */

public interface JSONParser {

    /**
     * This is a parser of a JSONObject. This is for Key: Name of the color, Value: score of the color
     * @param jsonObject the JSONObject read from the file system
     * @return a map with Key: name of the color, Value: score of the color
     */
    Map<String, Integer> parserColors(JSONObject jsonObject);

    /**
     * This is a parser of a JSONObject. This is for Key: Name of the color, Value: List of pair that indicates the relative coordinate in the matrix of the ball. 
     * @param jsonObject the JSONObject read from the file system
     * @return this is a map of type Key: Name of the color, Value: List of pair that indicates the relative coordinate in the matrix of the ball. 
     */
    Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject);

    /**
     * This is a parser of a JSONObject. This is for Key: name of the color, Value: hexadecimal of the color you want
     * @param jsonObject the JSONObject read from the file system
     * @return This is for Key: name of the color, Value: hexadecimal of the color you want
     */
    Map<String, String> parserColorsView(JSONObject jsonObject);

    /**
     * This return the score from a JSONObject
     * @param jsonObject the jsonobject to read
     * @return the score
     */
    int parserScore(JSONObject jsonObject);

    /**
     * This return the number of the level from a JSONObject
     * @param jsonObject the jsonobject to read
     * @return the level readed
     */
    int parserLevel(JSONObject jsonObject);

    /**
     * This encode a JSONObject from the score and the level
     * @param score score to encode
     * @param level level to encode
     * @return a JSONObject
     */
    JSONObject saveState(int score, int level);

}
