package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class LevelTest {

    JSONReader reader = new JSONReaderImpl();

    // File separator and the path with colors.json and level1.json
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String COLORS_PATH = "levels" + FILE_SEPARATOR + "colors.json";
    public static final String LEVEL1_PATH = "levels" + FILE_SEPARATOR + "level1.json";
    public static final String LEVELTEST_PATH = "levels" + FILE_SEPARATOR + "levelTest.json";

    BallFactory ballFactory = new BallFactoryImpl(
        JSONParserImpl.getIstance().parserColors(this.reader.readJSONFromFile(COLORS_PATH)), 15);

    @Test 
    void levelBasicTest() {
        Ball[][] expectedBalls = new Ball[10][10];

        expectedBalls[0][0] = this.ballFactory.createStaticBall("RED");
        expectedBalls[0][1] = this.ballFactory.createStaticBall("RED");
        expectedBalls[0][2] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[0][4] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[0][5] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[0][6] = this.ballFactory.createStaticBall("GREEN");

        Level testLevel = new LevelImpl(this.ballFactory, new Pair<Integer,Integer>(10, 10));

        Ball[][] realBalls = testLevel.getStartBalls(JSONParserImpl.getIstance().parserStarterBalls(this.reader.readJSONFromFile(LEVELTEST_PATH)));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (expectedBalls[i][j] != null && realBalls[i][j] != null) {
                    assertEquals(expectedBalls[i][j].toString(), realBalls[i][j].toString(), "Two Matrix are different");
                } else {
                    assertNull(expectedBalls[i][j]);
                    assertNull(realBalls[i][j]);
                }
            }
        }
    }

    @Test
    void level1Test() {
        Ball[][] expectedBalls = new Ball[10][10];

        expectedBalls[0][0] = this.ballFactory.createStaticBall("RED");
        expectedBalls[0][1] = this.ballFactory.createStaticBall("RED");
        expectedBalls[1][0] = this.ballFactory.createStaticBall("RED");
        expectedBalls[1][1] = this.ballFactory.createStaticBall("RED");
        expectedBalls[2][4] = this.ballFactory.createStaticBall("RED");
        expectedBalls[2][5] = this.ballFactory.createStaticBall("RED");
        expectedBalls[3][3] = this.ballFactory.createStaticBall("RED");
        expectedBalls[3][4] = this.ballFactory.createStaticBall("RED");

        expectedBalls[0][2] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[0][3] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[1][2] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[1][3] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[2][6] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[2][7] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[3][5] = this.ballFactory.createStaticBall("YELLOW");
        expectedBalls[3][6] = this.ballFactory.createStaticBall("YELLOW");

        expectedBalls[0][4] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[0][5] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[1][4] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[1][5] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[2][0] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[2][1] = this.ballFactory.createStaticBall("BLUE");
        expectedBalls[3][0] = this.ballFactory.createStaticBall("BLUE");

        expectedBalls[0][6] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[0][7] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[1][6] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[2][2] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[2][3] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[3][1] = this.ballFactory.createStaticBall("GREEN");
        expectedBalls[3][2] = this.ballFactory.createStaticBall("GREEN");

        Level testLevel = new LevelImpl(this.ballFactory, new Pair<Integer,Integer>(10, 10));

        Ball[][] realBalls = testLevel.getStartBalls(JSONParserImpl.getIstance().parserStarterBalls(this.reader.readJSONFromFile(LEVEL1_PATH)));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (expectedBalls[i][j] != null && realBalls[i][j] != null) {
                    assertEquals(expectedBalls[i][j].toString(), realBalls[i][j].toString(), "Two Matrix are different");
                } else {
                    assertNull(expectedBalls[i][j]);
                    assertNull(realBalls[i][j]);
                }
            }
        }
    }
    
}
