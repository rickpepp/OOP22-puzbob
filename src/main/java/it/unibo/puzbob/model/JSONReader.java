package it.unibo.puzbob.model;

import org.json.JSONObject;

/**
 * This interface is implementated in JSONReaderImpl. This class has only one method and
 * take in input the String of the locationPath and return a JSONObject
 */

public interface JSONReader {
    
    public JSONObject readJSONFromFile(String path);

}
