package it.unibo.puzbob.controller;

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
        reader.writeJSONFromObject(DIR_PATH, DIR_SAVE, jsonObject);
        
    }

    public void deleteState() {
        this.reader.deleteSaveState(DIR_SAVE);
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
