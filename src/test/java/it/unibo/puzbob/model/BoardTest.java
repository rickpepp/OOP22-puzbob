package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BoardTest {

    /* Constants for json path needed */
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String COLOR_FILE = "levels" + FILE_SEPARATOR + "colors.json";
    private static final String LEVEL_FILE = "levels" + FILE_SEPARATOR + "level1.json";
    private static final String REMOVE_FILE = "removeTest.json";
    private static final String REMOVE2_FILE = "removeTest2.json";
    private static final String REMOVE3_FILE = "removeTest3.json";
    private static final String REMOVE4_FILE = "removeTest4.json";
    private static final String REMOVE5_FILE = "removeTest5.json";

    private static final int SIZE_BALL = 15;
    private final Pair<Integer, Integer> DIMENSION = new Pair<Integer,Integer>(10, 10);
    private final int ROW_MATRIX = 10;
    private final int COLUMN_MATRIX = 10;

    /* Variable to read and decode json file */
    private JSONReader reader = new JSONReaderImpl();
    private JSONParser parser = new JSONParserImpl();

    /* Variable for create a ballFactory */
    private Map<String,Integer> colorMap = parser.parserColors(reader.readJSONFromFile(COLOR_FILE));
    

    /* Variables for create a level */
    private Map<String, List<Pair<Integer, Integer>>> levelMap = parser.parserStarterBalls(reader.readJSONFromFile(LEVEL_FILE));
    private BallFactory ballFactory = new BallFactoryImpl(colorMap, SIZE_BALL);

    /* Variable for create a matrixBall */
    Level level = new LevelImpl(ballFactory, DIMENSION);

    /* Variables for create a Board */
    Ball[][] matrixBall = level.getStartBalls(levelMap);
    Board board;

    /* Variable for compare results */
    Ball newBall = ballFactory.createStaticBall("RED");
    Ball[][] matrixTest = matrixBall;
    

    /* Variable for test removeBall method */
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE_FILE));
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap2 = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE2_FILE));
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap3 = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE3_FILE));
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap4 = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE4_FILE));
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap5 = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE5_FILE));

    /* This method is used to print matrices with the same format so that the values can be better controlled */
    private String convertMatrixToString(Ball[][] matrix){
        String matrixToString = new String();
        for(int i = 0;  i < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++)
                matrixToString = matrixToString + "|" + matrix[i][k];
            
            matrixToString = matrixToString + "\n";
        }
        return matrixToString;
    }

    /* This method  */
    private Ball[][] cleanMatrix(Ball[][] matrix){
        for(int i = 0; i < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++){
                matrix[i][k] = null;
            }
        }
        return matrix;
    }

    @Test
    void getStatusBoardTest(){
        board = new BoardImpl(300.0, 200.0, matrixBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal ");
    }

    @Test
    void getBoardSizeTest(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        Pair<Double, Double> boardDimension = new Pair<Double,Double>(300.0, 200.0);

        assertEquals(boardDimension.toString(), board.getBoardSize().toString(), "The dimension of the board are wrong");
    }

    @Test 
    void getColorsTest(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        ArrayList<String> colorsTest = new ArrayList<>();

        colorsTest.add("RED");
        colorsTest.add("YELLOW");
        colorsTest.add("BLUE");
        colorsTest.add("GREEN");

        assertEquals(colorsTest.toString(), board.getColors().toString(), "Color list do not match");
    }

    @Test
    void addBallTest(){
        board = new BoardImpl(300.0, 200.0, matrixBall);

        matrixTest[1][1] =  newBall;
        board.addBall(1, 1, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Error in adding the ball");
    }

    /* Basic test */
    @Test
    void removeBallAdTest1(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        int row = 4;
        int column = 5;

        matrixTest = cleanMatrix(matrixTest);
        matrixTest = level.getStartBalls(removeTestMap);

        board.addBall(row, column, newBall);
        board.removeBallAd(row, column, newBall.getColor());  

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
    }

    /* Test with additional balls */
    @Test
    void removeBallAdTest2(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        int row = 4;
        int column = 5;

        matrixTest = cleanMatrix(matrixTest);
        matrixTest = level.getStartBalls(removeTestMap2);

        board.addBall(row, row, newBall);
        board.addBall(row, column, blueBall);
        board.removeBallAd(row, row, newBall.getColor());

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
    }

    /* Test with center balls */
    @Test
    void removeBallAdTest3(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        Ball bluBall = ballFactory.createStaticBall("BLUE");
        Ball yellowBall = ballFactory.createStaticBall("YELLOW");
        Ball yelBall = ballFactory.createStaticBall("YELLOW");

        matrixTest = cleanMatrix(matrixTest);
        matrixTest = level.getStartBalls(removeTestMap3);

        board.addBall(4, 3, bluBall);
        board.addBall(4,4,newBall);
        board.addBall(4, 5, yelBall);
        board.addBall(5, 4, yellowBall);
        board.removeBallAd(4, 4, "RED");

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
    
    /* Test with balls at the edges */
    @Test
    void removeBallAdTest4(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        matrixTest = cleanMatrix(matrixTest);
        matrixTest = level.getStartBalls(removeTestMap4);

        board.removeBallAd(3, 0, "BLUE");
        board.removeBallAd(1,0,"RED");
        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
    
    /* Test with outer balls */
    @Test
    void removeBallAdTest5(){
        board = new BoardImpl(300.0, 200.0, matrixBall);
        Ball blueBall = ballFactory.createStaticBall("BLUE");

        matrixTest = cleanMatrix(matrixTest);
        matrixTest = level.getStartBalls(removeTestMap5);

        board.addBall(4,6, blueBall);
        board.removeBallAd(3, 6, "YELLOW");

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
}
