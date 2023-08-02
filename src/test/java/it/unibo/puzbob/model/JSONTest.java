package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JSONTest {

    JSONReader reader = new JSONReaderImpl();

    // File separator and the path with colors.json and level1.json
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String COLORS_PATH = "levels" + FILE_SEPARATOR + "colors.json";
    public static final String LEVEL1_PATH = "levels" + FILE_SEPARATOR + "level1.json";

    // Test if the reader ork as expected
    @Test 
    void jsonReaderTest() {
        String colors = 
        "{\"ColorsList\":[\"RED\",\"YELLOW\",\"BLUE\",\"GREEN\",\"BLACK\",\"PURPLE\",\"ORANGE\",\"GREY\"]}";

        String level1 = 
        "{\"level\":{\"RED\":[[0,0],[0,1],[1,0],[2,4],[2,5],[3,3],[3,4]],\"BLUE\":[[0,4],[0,5],[1,4],[1,5],[2,0],[2,1],[3,0]],\"YELLOW\":[[0,2],[0,3],[1,2],[1,3],[2,6],[2,7],[3,5],[3,6]],\"GREEN\":[[0,6],[0,7],[1,6],[2,2],[2,3],[3,1],[3,2]]}}";

        assertEquals(colors, reader.readJSONFromFile(COLORS_PATH).toString(), "Not the expected string");
        assertEquals(level1, reader.readJSONFromFile(LEVEL1_PATH).toString(), "Not the expected string");
    }
    
}
