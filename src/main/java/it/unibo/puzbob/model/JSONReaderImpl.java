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

    private static JSONReaderImpl reader;

    /**
     * This is a default constructor
     */
    public JSONReaderImpl() {}

    /** This method creates a single istance of this class
     * @return is the istance of JSONReaderImpl
      */
    public static synchronized JSONReaderImpl getIstance(){
        if(reader == null){
            reader = new JSONReaderImpl();
        }
        return reader;
    }

    /**
     * This method return a JSONObject from a file on the file system with the URL specified 
     * in the args
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

    // Write a JSONObject on the file system
    public void writeJSONFromObject(String dirPath, String filePath, JSONObject jsonObject) {
        File dir= new File(dirPath);
        File jsonFile = new File(filePath);

        // If the directory doesn't exist
        if (!dir.exists()) {
            try {
                // Create the directory
                dir.mkdir(); 
            } catch (Exception ioe) {
                System.err.println("Impossibile creare la cartella: " + ioe.getMessage());
            }
        } 

        // Write the JSONObject
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            // Scrivere l'oggetto JSON nel file
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {}

    }

    // Read a save and return a JSONObject
    public JSONObject readJSONSaveState(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);

            try (Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\A")) {
                if (scanner.hasNext()) {
                    String jsonContent = scanner.next();

                    // If there is a save retun it
                    return new JSONObject(jsonContent);
                } else {

                    // Else return null
                    return null;
                }
            }
        } catch (IOException | JSONException e) {
            return null;
        }
    }

    // Delete the save file
    public void deleteSaveState(String path) {
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }
    
}
