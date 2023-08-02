package it.unibo.puzbob.model;

import org.json.JSONObject;

public interface JSONReader {
    
    public JSONObject readJSONFromFile(String path);

}
