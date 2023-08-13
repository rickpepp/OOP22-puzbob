package it.unibo.puzbob.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;

import it.unibo.puzbob.model.JSONParser;
import it.unibo.puzbob.model.JSONParserImpl;
import it.unibo.puzbob.model.JSONReader;
import it.unibo.puzbob.model.JSONReaderImpl;

public class SaveStateImpl implements SaveState {

    private JSONReader reader;
    private JSONParser parser;
    private static final String DIR_HOME = System.getProperty("user.home");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String DIR_PATH = DIR_HOME + FILE_SEPARATOR + "puzbob";
    private static final String DIR_SAVE =  DIR_PATH + FILE_SEPARATOR + "save.json";

    public SaveStateImpl() {
        reader = new JSONReaderImpl();
        parser = new JSONParserImpl();
    }

    @Override
    public boolean thereIsState() {
        return this.reader.readJSONSaveState(DIR_SAVE) != null;
    }

    @Override
    public void saveState(int score, int level) {
        JSONObject jsonObject = parser.saveState(score, level);

        File directory = new File(DIR_PATH);

        if (!directory.exists()) {
                try {
                    directory.mkdir(); // Crea la cartella
                    System.out.println("Cartella creata con successo.");
                } catch (Exception ioe) {
                    System.err.println("Impossibile creare la cartella: " + ioe.getMessage());
                }
            } 
        reader.writeJSONFromObject(DIR_SAVE, jsonObject);
        
    }

    @Override
    public int getScore() {
        JSONObject jsonObject = this.reader.readJSONSaveState(DIR_SAVE);
        return this.parser.parserScore(jsonObject);
    }

    @Override
    public int getLevel() {
        JSONObject jsonObject = this.reader.readJSONSaveState(DIR_SAVE);
        return this.parser.parserLevel(jsonObject);
    }
    
}
