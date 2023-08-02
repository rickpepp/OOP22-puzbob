package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParserImpl implements JSONParser {

    public Map<String, Integer> parserColors(JSONObject jsonObject) {
        
        JSONArray colorsArray = jsonObject.getJSONArray("ColorsList");
        Map<String, Integer> mapToReturn = new HashMap<>();
        
        for (int i = 0; i < colorsArray.length(); i++) {
            JSONArray singolColor = colorsArray.getJSONArray(i);
            mapToReturn.put(singolColor.getString(0), singolColor.getInt(1));
        }

        return mapToReturn;

    }

    public Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject) {
        
        JSONObject starterArray = jsonObject.getJSONObject("level");

        Map<String, List<Pair<Integer, Integer>>> mapToReturn = new HashMap<>();

        for (String color: starterArray.keySet()) {
            List<Pair<Integer, Integer>> arrayToUse = new ArrayList<>();
            JSONArray positionsArray = starterArray.getJSONArray(color);

            for (int i = 0; i < positionsArray.length(); i++) {
                JSONArray xyArray = positionsArray.getJSONArray(i);
                Pair<Integer, Integer> newPair = new Pair<Integer,Integer>(xyArray.getInt(0), 
                    xyArray.getInt(1));
                arrayToUse.add(newPair);
            }

            mapToReturn.put(color, arrayToUse);
        }

        return mapToReturn;
    }
    
}
