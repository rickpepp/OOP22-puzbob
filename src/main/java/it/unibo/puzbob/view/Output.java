package it.unibo.puzbob.view;

import org.json.JSONObject;

/**
 * This is a generic interface for a view class. In input has a JSONObject that show
 * the world status at the moment.
 */

public interface Output {
    
    public void displayGame(JSONObject world);

}
