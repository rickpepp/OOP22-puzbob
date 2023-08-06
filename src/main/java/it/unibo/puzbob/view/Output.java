package it.unibo.puzbob.view;

import org.json.JSONObject;

/**
 * This is a basic interface for an output source. It take in input a JSONObject that contain the information
 * about the state of the actual world. It is done in json to have a separated view and model.
 */

public interface Output {
    
    public void displayGame(JSONObject world);

}
