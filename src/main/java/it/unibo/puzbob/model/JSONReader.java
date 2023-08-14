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

    /**
     * This method save a JSONObject on the file system
     * @param dirPath the ULR of the parent path
     * @param path the URL of the JSONObject
     * @param jsonObject the JSONObject
     */
    public void writeJSONFromObject(String dirPath, String path, JSONObject jsonObject);

    /**
     * Read from the file system the save
     * @param path the URL of the save
     * @return a JSONObject
     */
    public JSONObject readJSONSaveState(String path);

    /**
     * Delete the save from the file system 
     * @param path where is the save in the file system
     */
    public void deleteSaveState(String path);

}
