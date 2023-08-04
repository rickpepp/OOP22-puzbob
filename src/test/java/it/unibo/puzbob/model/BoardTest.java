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
    Board board = new BoardImpl(300.0, 200.0, matrixBall);

    /* Variable for compare results */
    Ball newBall = ballFactory.createStaticBall("RED");
    Ball[][] matrixTest = matrixBall;
    

    /* Variable for test removeBall method */
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE_FILE));
    private Map<String, List<Pair<Integer, Integer>>> removeTestMap2 = parser.parserStarterBalls(reader.readJSONFromFile(REMOVE2_FILE));

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

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal ");
    }

    @Test
    void getBoardSizeTest(){
        Pair<Double, Double> boardDimension = new Pair<Double,Double>(300.0, 200.0);
        assertEquals(boardDimension.toString(), board.getBoardSize().toString(), "The dimension of the board are wrong");
    }

    @Test 
    void getColorsTest(){
        ArrayList<String> colorsTest = new ArrayList<>();
        colorsTest.add("RED");
        colorsTest.add("YELLOW");
        colorsTest.add("BLUE");
        colorsTest.add("GREEN");
        assertEquals(colorsTest.toString(), board.getColors().toString(), "Color list do not match");
    }

    @Test
    void addBallTest(){
        matrixTest[1][1] =  newBall;
        board.addBall(1, 1, newBall);
        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Error in adding the ball");
    }

    /* Basic test */
    @Test
    void removeBallAdTest1(){
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
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        int row = 4;
        int column = 5;

        matrixTest = cleanMatrix(matrixTest);

        matrixTest = level.getStartBalls(removeTestMap2);
        board.addBall(row, column, blueBall);
        board.addBall(row, row, newBall);
        board.removeBallAd(row, row, newBall.getColor());
        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
    }
    
}
