package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implements the JSONParser interface. In JSONParser there are more information on the utility of this class.
 */

public class JSONParserImpl implements JSONParser {

    public Map<String, Integer> parserColors(JSONObject jsonObject) {
        
        // Create a map to return and extract the array from ColorList
        JSONArray colorsArray = jsonObject.getJSONArray("ColorsList");
        Map<String, Integer> mapToReturn = new HashMap<>();
        
        // Get every pair of color and score and put in the map to return
        for (int i = 0; i < colorsArray.length(); i++) {
            JSONArray singolColor = colorsArray.getJSONArray(i);
            mapToReturn.put(singolColor.getString(0), singolColor.getInt(1));
        }

        return mapToReturn;

    }

    public Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject) {
        
        // Create a map to return and extract the array from level
        JSONObject starterArray = jsonObject.getJSONObject("level");
        Map<String, List<Pair<Integer, Integer>>> mapToReturn = new HashMap<>();

        // Check every color in the json Object
        for (String color: starterArray.keySet()) {
            List<Pair<Integer, Integer>> arrayToUse = new ArrayList<>();
            JSONArray positionsArray = starterArray.getJSONArray(color);

            // Put in a list every coordinates readed
            for (int i = 0; i < positionsArray.length(); i++) {
                JSONArray xyArray = positionsArray.getJSONArray(i);
                Pair<Integer, Integer> newPair = new Pair<Integer,Integer>(xyArray.getInt(0), 
                    xyArray.getInt(1));
                arrayToUse.add(newPair);
            }

            // Put in the map the result
            mapToReturn.put(color, arrayToUse);
        }

        return mapToReturn;
    }

    @Override
    public Map<String, String> parserColorsView(JSONObject jsonObject) {
        // Create a map to return and extract the array from ColorList
        JSONArray colorsArray = jsonObject.getJSONArray("ColorsList");
        Map<String, String> mapToReturn = new HashMap<>();
        
        // Get every pair of color and score and put in the map to return
        for (int i = 0; i < colorsArray.length(); i++) {
            JSONArray singolColor = colorsArray.getJSONArray(i);
            mapToReturn.put(singolColor.getString(0), singolColor.getString(1));
        }

        return mapToReturn;
    }
    
}
