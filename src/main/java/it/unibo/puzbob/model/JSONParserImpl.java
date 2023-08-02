package it.unibo.puzbob.model;

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

    @Override
    public Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parserStarterBalls'");
    }
    
}
