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
            // getClassLoader take resources from the directory "resources"
            InputStream inputStream = JSONReaderImpl.class.getClassLoader().getResourceAsStream(path);

            try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
                if (scanner.hasNext()) {
                    String jsonContent = scanner.next();
                    return new JSONObject(jsonContent);
                } else {
                    return null;
                }
            }

        // Exceptions
        } catch (NullPointerException | JSONException e) {
            return null;
        }
        
    }
    
}
