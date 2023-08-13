package it.unibo.puzbob.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This class open a JSON file and return a JSON Object
 */

public class JSONReaderImpl implements JSONReader {

    /**
     * This is a default constructor
     */
    public JSONReaderImpl() {}

    /**
     * This method return a JSONObject from a file on the file system with the URL specified in the args
     */
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

    @Override
    public void writeJSONFromObject(String path, JSONObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            // Scrivere l'oggetto JSON nel file
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject readJSONSaveState(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);

            try (Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\A")) {
                if (scanner.hasNext()) {
                    String jsonContent = scanner.next();
                    return new JSONObject(jsonContent);
                } else {
                    return null;
                }
            }
        } catch (IOException | JSONException e) {
            return null;
        }
    }
    
}
