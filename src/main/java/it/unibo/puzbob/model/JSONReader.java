package it.unibo.puzbob.model;

import org.json.JSONObject;

/**
 * This interface is implementated in JSONReaderImpl. This class has only one method and
 * take in input the String of the locationPath and return a JSONObject
 */

public interface JSONReader {
    
    /**
     * This method return a JSONObject from a file on the file system with the URL specified in the args
     * @param path a string that show the URL of the resource
     * @return a generic JSONObject
     */
    public JSONObject readJSONFromFile(String path);

    public void writeJSONFromObject(String path, JSONObject jsonObject);

    public JSONObject readJSONSaveState(String path);

}
