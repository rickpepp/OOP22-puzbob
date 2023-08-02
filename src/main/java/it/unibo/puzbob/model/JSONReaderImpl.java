package it.unibo.puzbob.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

/**
 * This class open a JSON file and return a JSON Object
 */

public class JSONReaderImpl implements JSONReader {

    public JSONObject readJSONFromFile(String path) {

        try {
            InputStream inputStream = JSONReaderImpl.class.getClassLoader().getResourceAsStream(path);

            /* This try catch resources return a JSONObject, and then
            close the file */

            try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
                if (scanner.hasNext()) {
                    String jsonContent = scanner.next();
                    return new JSONObject(jsonContent);
                } else {
                    return null;
                }
            }

        } catch (NullPointerException | JSONException e) {
            return null;
        }
        
    }
    
}
