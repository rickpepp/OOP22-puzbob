package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface JSONPareser {
    
    Map<String, Integer> parserColors(JSONObject jsonObject);

    Map<String, List<Pair<Integer, Integer>>> parserStarterBalls(JSONObject jsonObject);

}
