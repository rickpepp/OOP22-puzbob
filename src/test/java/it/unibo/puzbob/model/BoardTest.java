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
    private static final String REMOVE_FILE = "board" + FILE_SEPARATOR + "removeTest.json";
    private static final String REMOVE2_FILE = "board" + FILE_SEPARATOR + "removeTest2.json";
    private static final String REMOVE3_FILE = "board" + FILE_SEPARATOR + "removeTest3.json";
    private static final String REMOVE4_FILE = "board" + FILE_SEPARATOR + "removeTest4.json";
    private static final String REMOVE5_FILE = "board" + FILE_SEPARATOR + "removeTest5.json";

    /* Constant for test */
    private static final int SIZE_BALL = 15;
    private final Pair<Integer, Integer> DIMENSION = new Pair<Integer,Integer>(10, 10);
    private final Pair<Double, Double> DIMENSION_BOARD = new Pair<Double, Double>(300.0, 200.0);
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
    Level level4TestR1 = new LevelImpl(ballFactory, DIMENSION);
    Level level4TestR2 = new LevelImpl(ballFactory, DIMENSION);
    Level level4TestR3 = new LevelImpl(ballFactory, DIMENSION);
    Level level4TestR4 = new LevelImpl(ballFactory, DIMENSION);
    Level level4TestR5 = new LevelImpl(ballFactory, DIMENSION);

    /* Variables for create a Board */
    Ball[][] matrixBall = level.getStartBalls(levelMap);
    Board board;

    /* Variables for compare results */
    Ball[][] matrixTest = new Ball[DIMENSION.getX()][DIMENSION.getY()];
    Ball newBall = ballFactory.createStaticBall("RED");    

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

    /* This method cleans matrixTest to proceed with other tests*/
    private Ball[][] cleanMatrix(Ball[][] matrix){
        for(int i = 0; i < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++){
                matrix[i][k] = null;
            }
        }
        return matrix;
    }

    private Ball[][] copyMatrix(Ball[][] matrix){
        Ball[][] matrixCopy = new Ball[DIMENSION.getX()][DIMENSION.getY()];
        for(int i = 0; i < DIMENSION.getX(); i++){
            for(int k = 0; k < DIMENSION.getY(); k++){
                if(matrix[i][k] != null){
                    matrixCopy[i][k] = ballFactory.createStaticBall(matrix[i][k].getColor());
                }
                else{
                    matrixCopy[i][k] = null;
                }
            }
        }
        return matrixCopy;
    }
    @Test
    void getStatusBoardTest(){
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);
        matrixTest = matrixBall;

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal ");
    }

    @Test
    void getBoardSizeTest(){
        Pair<Double, Double> boardDimension = new Pair<Double,Double>(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY());

        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        assertEquals(boardDimension.toString(), board.getBoardSize().toString(), "The dimension of the board are wrong");
    }

    @Test 
    void getColorsTest(){
        ArrayList<String> colorsTest = new ArrayList<>();

        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixBall);

        colorsTest.add("RED");
        colorsTest.add("YELLOW");
        colorsTest.add("BLUE");
        colorsTest.add("GREEN");

        assertEquals(colorsTest.toString(), board.getColors().toString(), "Color list do not match");
    }

    @Test
    void addBallTest(){
        Ball[][] matrixStart;

        matrixStart = copyMatrix(matrixBall);
        matrixTest = copyMatrix(matrixBall);

        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixStart);

        matrixTest[1][1] =  newBall;
        board.addBall(1, 1, newBall);

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Error in adding the ball");
    }

    /* Basic test */
    @Test
    void removeBallAdTest1(){
        int row = 4;
        int column = 5;
        Ball[][] matrixStart = new Ball[DIMENSION.getX()][DIMENSION.getY()];

        cleanMatrix(matrixTest);
        matrixTest = copyMatrix(level4TestR1.getStartBalls(removeTestMap));
        matrixStart = copyMatrix(matrixBall);

        board = new BoardImpl(300.0, 200.0, matrixStart);

        board.addBall(row, column, newBall);
        board.removeBallAd(row, column, newBall.getColor()); 

        System.out.println("Test:\n" + convertMatrixToString(matrixTest));
        System.out.println("Board: \n" + convertMatrixToString(board.getStatusBoard()));

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
    }

    /* Test with additional balls */
    @Test
    void removeBallAdTest2(){
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        int row = 4;
        int column = 5;
        Ball[][] matrixStart = new Ball[DIMENSION.getX()][DIMENSION.getY()];

        matrixStart = copyMatrix(matrixBall);
        cleanMatrix(matrixTest);
        matrixTest = copyMatrix(level4TestR2.getStartBalls(removeTestMap2));
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixStart);

        board.addBall(row, row, newBall);
        board.addBall(row, column, blueBall);
        board.removeBallAd(row, row, newBall.getColor());

        System.out.println("Test:\n" + convertMatrixToString(matrixTest));
        System.out.println("Board: \n" + convertMatrixToString(board.getStatusBoard()));

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equals");
    }

    /* Test with center balls */
    @Test
    void removeBallAdTest3(){
        Ball bluBall = ballFactory.createStaticBall("BLUE");
        Ball yellowBall = ballFactory.createStaticBall("YELLOW");
        Ball yelBall = ballFactory.createStaticBall("YELLOW");
        Ball[][] matrixStart = new Ball[DIMENSION.getX()][DIMENSION.getY()];

        matrixStart = copyMatrix(matrixBall);
        cleanMatrix(matrixTest);
        matrixTest = copyMatrix(level4TestR3.getStartBalls(removeTestMap3));
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixStart);
        
        board.addBall(4, 3, bluBall);
        board.addBall(4,4,newBall);
        board.addBall(4, 5, yelBall);
        board.addBall(5, 4, yellowBall);
        board.removeBallAd(4, 4, "RED");

        System.out.println("Test:\n" + convertMatrixToString(matrixTest));
        System.out.println("Start:\n" + convertMatrixToString(board.getStatusBoard()));

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
    
    /* Test with balls at the edges */
    @Test
    void removeBallAdTest4(){
        Ball[][] matrixStart = new Ball[DIMENSION.getX()][DIMENSION.getY()];

        matrixStart = copyMatrix(matrixBall);
        cleanMatrix(matrixTest);
        matrixTest = copyMatrix(level4TestR4.getStartBalls(removeTestMap4));
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixStart);

        board.removeBallAd(3, 0, "BLUE");
        board.removeBallAd(1,0,"RED");

        
        System.out.println("Test:\n" + convertMatrixToString(matrixTest));
        System.out.println("Start:\n" + convertMatrixToString(board.getStatusBoard()));

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
    
    /* Test with outer balls */
    @Test
    void removeBallAdTest5(){
        Ball blueBall = ballFactory.createStaticBall("BLUE");
        Ball[][] matrixStart = new Ball[DIMENSION.getX()][DIMENSION.getY()];

        matrixStart = copyMatrix(matrixBall);
        cleanMatrix(matrixTest);
        matrixTest = copyMatrix(level4TestR5.getStartBalls(removeTestMap5));
        board = new BoardImpl(DIMENSION_BOARD.getX(), DIMENSION_BOARD.getY(), matrixStart);

        board.addBall(4,6, blueBall);
        board.removeBallAd(3, 6, "YELLOW");

        System.out.println("Test:\n" + convertMatrixToString(matrixTest));
        System.out.println("Start:\n" + convertMatrixToString(board.getStatusBoard()));

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal");
    }
}
