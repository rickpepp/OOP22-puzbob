package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class JSONTest {

    JSONReader reader = new JSONReaderImpl();

    JSONParser parser = new JSONParserImpl();

    // File separator and the path with colors.json and level1.json
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String COLORS_PATH = "levels" + FILE_SEPARATOR + "colors.json";
    public static final String LEVEL1_PATH = "levels" + FILE_SEPARATOR + "level1.json";
    public static final String LEVELTEST_PATH = "levels" + FILE_SEPARATOR + "levelTest.json";

    // Test if the reader ork as expected
    @Test 
    void jsonReaderTest() {
        String colors = 
        "{\"ColorsList\":[[\"RED\",10],[\"YELLOW\",10],[\"BLUE\",10],[\"GREEN\",10],[\"BLACK\",10],[\"PURPLE\",10],[\"ORANGE\",10],[\"GREY\",10]]}";

        String level1 = 
        "{\"level\":{\"RED\":[[0,0],[0,1],[1,0],[2,4],[2,5],[3,3],[3,4]],\"BLUE\":[[0,4],[0,5],[1,4],[1,5],[2,0],[2,1],[3,0]],\"YELLOW\":[[0,2],[0,3],[1,2],[1,3],[2,6],[2,7],[3,5],[3,6]],\"GREEN\":[[0,6],[0,7],[1,6],[2,2],[2,3],[3,1],[3,2]]}}";

        assertEquals(colors, reader.readJSONFromFile(COLORS_PATH).toString(), "Not the expected string");
        assertEquals(level1, reader.readJSONFromFile(LEVEL1_PATH).toString(), "Not the expected string");
    }

    @Test
    void jsonParserColorTest() {
        Map<String, Integer> mapExpected = new HashMap<>();

        mapExpected.put("RED",10);
        mapExpected.put("YELLOW",10);
        mapExpected.put("BLUE",10);
        mapExpected.put("GREEN",10);
        mapExpected.put("BLACK",10);
        mapExpected.put("PURPLE",10);
        mapExpected.put("ORANGE",10);
        mapExpected.put("GREY",10);

        assertEquals(mapExpected, parser.parserColors(reader.readJSONFromFile(COLORS_PATH)), "Not the map Expected");
        
    }

    @Test
    void jsonParserLevelTest() {
        Map<String, List<Pair<Integer, Integer>>> mapExpected = new HashMap<>();

        List<Pair<Integer, Integer>> listRED = new ArrayList<>();
        listRED.add(new Pair<Integer,Integer>(0, 0));
        listRED.add(new Pair<Integer,Integer>(0, 1));

        List<Pair<Integer, Integer>> listYELLOW = new ArrayList<>();
        listYELLOW.add(new Pair<Integer,Integer>(0, 2));

        List<Pair<Integer, Integer>> listBLUE = new ArrayList<>();
        listBLUE.add(new Pair<Integer,Integer>(0, 4));
        listBLUE.add(new Pair<Integer,Integer>(0, 5));

        List<Pair<Integer, Integer>> listGREEN = new ArrayList<>();
        listGREEN.add(new Pair<Integer,Integer>(0, 6));

        mapExpected.put("RED", listRED);
        mapExpected.put("YELLOW", listYELLOW);
        mapExpected.put("BLUE", listBLUE);
        mapExpected.put("GREEN", listGREEN);

        assertEquals(mapExpected.toString(), 
            parser.parserStarterBalls(reader.readJSONFromFile(LEVELTEST_PATH)).toString(), 
            "Map of starting ball non as expected");
    }
    
}
