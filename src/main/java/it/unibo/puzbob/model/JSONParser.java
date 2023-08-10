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
    
    Map<String, Integer> parserColors(JSONObject jsonObject);

    Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject);

    Map<String, String> parserColorsView(JSONObject jsonObject);

}
