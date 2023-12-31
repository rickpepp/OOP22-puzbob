package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class BoardTest {

    /* Constants for json path needed */
    private static final String COLOR_FILE = "colors.json";
    private static final String LEVEL_FILE = "level1.json";

    /* Constant for test */
    private static final int SIZE_BALL = 15;
    private final Pair<Integer, Integer> DIMENSION = new Pair<Integer,Integer>(10, 10);
    private final Pair<Double, Double> DIMENSION_BOARD = new Pair<Double, Double>(300.0, 200.0);
    private final int ROW_MATRIX = 10;
    private final int COLUMN_MATRIX = 10;

    /* Variable for create a ballFactory */
    private Map<String,Integer> colorMap = JSONParserImpl.getIstance().parserColors(JSONReaderImpl.getIstance().readJSONFromFile(COLOR_FILE));
    
    /* Variables for create a level */
    private Map<String, List<Pair<Integer, Integer>>> levelMap = JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(LEVEL_FILE));
    private BallFactory ballFactory = new BallFactoryImpl(colorMap, SIZE_BALL);

    /* Variable for create a matrixBall */
    Level level = new LevelImpl(ballFactory, DIMENSION);

    /* Variables for create a Board */
    Ball[][] matrixBall;
    Board board;

    /* Variables for compare results */
    Ball[][] matrixTest = new Ball[DIMENSION.getX()][DIMENSION.getY()];
    Ball newBall = ballFactory.createStaticBall("RED"); 
    int score = 0;   

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

    @Test
    void getStatusBoardTest(){
        matrixBall = level.getStartBalls(levelMap);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        Level levelTest = new LevelImpl(ballFactory, DIMENSION);

        matrixTest = levelTest.getStartBalls(levelMap);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal ");
    }

    @Test
    void getBoardSizeTest(){
        Pair<Double, Double> boardDimension = new Pair<Double,Double>(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY());
        
        matrixBall = level.getStartBalls(levelMap);
        board = new BoardImpl(DIMENSION_BOARD.getY(), DIMENSION_BOARD.getX(), matrixBall);

        assertEquals(boardDimension.toString(), board.getBoardSize().toString(), "The dimension of the board are wrong");
    }

    @Test 
    void getColorsTest(){
        ArrayList<String> colorsTest = new ArrayList<>();

        matrixBall = level.getStartBalls(levelMap);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        colorsTest.add("RED");
        colorsTest.add("YELLOW");
        colorsTest.add("BLUE");
        colorsTest.add("GREEN");

        assertEquals(colorsTest.toString(), board.getColors().toString(), "Color list do not match");
    }

    /* Is used to check if it adds a ball */
    @Test
    void addBallTest(){
        matrixBall = level.getStartBalls(levelMap);

        Level levelTest = new LevelImpl(ballFactory, DIMENSION);

        matrixTest = levelTest.getStartBalls(levelMap);

        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        matrixTest[4][0] =  newBall;
        board.addBall(4, 0, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Error in adding the ball");
    }

    /* Is used to check if it does not add the ball when it is already there */
    @Test
    void addBallTest2(){
        matrixBall = level.getStartBalls(levelMap);

        Level levelTest = new LevelImpl(ballFactory, DIMENSION);

        matrixTest = levelTest.getStartBalls(levelMap);

        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        board.addBall(0,4, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Error in adding the ball");
    }

    /* Basic test */
    @Test
    void removeBallTest1(){
        String REMOVE_FILE = "removeTest.json";
        Level level4TestR1 = new LevelImpl(ballFactory, DIMENSION);
        JSONObject json = JSONReaderImpl.getIstance().readJSONFromFile(REMOVE_FILE);
        Map<String, List<Pair<Integer, Integer>>> removeTestMap = JSONParserImpl.getIstance().parserStarterBalls(json);
        int row = 4;
        int column = 5;
        
        matrixBall = level.getStartBalls(levelMap);
        matrixTest = level4TestR1.getStartBalls(removeTestMap);

        board = new BoardImpl(300.0, 200.0, matrixBall);

        board.addBall(row, column, newBall);
        score = board.removeBall(row, column, newBall); 

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
        assertEquals(50, score, "Score is not equal");
    }

    /* Test with additional balls */
    @Test
    void removeBallTest2(){
        String REMOVE2_FILE = "removeTest2.json";
        Level level4TestR2 = new LevelImpl(ballFactory, DIMENSION);
        Map<String, List<Pair<Integer, Integer>>> removeTestMap2 = JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(REMOVE2_FILE));
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        matrixBall = level.getStartBalls(levelMap);
        int row = 4;
        int column = 5;
        
        matrixTest = level4TestR2.getStartBalls(removeTestMap2);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        board.addBall(row, row, newBall);
        board.addBall(row, column, blueBall);
        score = board.removeBall(row, row, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
        assertEquals(50, score, "Score is not equal");
    }

    /* Test with center balls */
    @Test
    void removeBallTest3(){
        String REMOVE3_FILE = "removeTest3.json";
        Level level4TestR3 = new LevelImpl(ballFactory, DIMENSION);
        Map<String, List<Pair<Integer, Integer>>> removeTestMap3 = JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(REMOVE3_FILE));
        Ball bluBall = ballFactory.createStaticBall("BLUE");
        Ball yellowBall = ballFactory.createStaticBall("YELLOW");
        Ball yelBall = ballFactory.createStaticBall("YELLOW");

        matrixBall = level.getStartBalls(levelMap);
        matrixTest = level4TestR3.getStartBalls(removeTestMap3);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);
        
        board.addBall(4, 3, bluBall);
        board.addBall(4,4,newBall);
        board.addBall(4, 5, yelBall);
        board.addBall(5, 4, yellowBall);
        board.removeBall(4, 4, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }

    /* Test with balls at the edges */
    @Test
    void removeBallTest4(){
        String REMOVE4_FILE = "removeTest4.json";
        Level level4TestR4 = new LevelImpl(ballFactory, DIMENSION);
        Map<String, List<Pair<Integer, Integer>>> removeTestMap4 = JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(REMOVE4_FILE));
        Ball blueBall = ballFactory.createStaticBall("BLUE");

        matrixBall = level.getStartBalls(levelMap);
        matrixTest = level4TestR4.getStartBalls(removeTestMap4);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        score = board.removeBall(3,0,blueBall);
        score = score + board.removeBall(0, 0, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
        assertEquals(70, score, "Score is not equal");
    }

    /* Test with outer balls */
    @Test
    void removeBallAdTest5(){
        String REMOVE5_FILE = "removeTest5.json";
        Level level4TestR5 = new LevelImpl(ballFactory, DIMENSION);
        Map<String, List<Pair<Integer, Integer>>> removeTestMap5 = JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(REMOVE5_FILE));
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        Ball yellowBall = ballFactory.createStaticBall("YELLOW");

        matrixBall = level.getStartBalls(levelMap);
        matrixTest = level4TestR5.getStartBalls(removeTestMap5);
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        board.addBall(4,6, blueBall);
        score = board.removeBall(3, 6, yellowBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
        assertEquals(50, score, "Score is not equal");
    }

}
