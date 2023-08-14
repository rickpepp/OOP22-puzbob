package it.unibo.puzbob.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    public void writeJSONFromObject(String dirPath, String filePath, JSONObject jsonObject) {
        File dir= new File(dirPath);
        File jsonFile = new File(filePath);

        if (!dir.exists()) {
            try {
                dir.mkdir(); 
            } catch (Exception ioe) {
                System.err.println("Impossibile creare la cartella: " + ioe.getMessage());
            }
        } 

        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            // Scrivere l'oggetto JSON nel file
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {}

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

    public void deleteSaveState(String path) {
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }
    
}
