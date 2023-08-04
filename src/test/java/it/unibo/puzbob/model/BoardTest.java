package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BoardTest {

    /* Constants for json path needed */
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String COLOR_FILE = "levels" + FILE_SEPARATOR + "colors.json";
    private static final String LEVEL_FILE = "levels" + FILE_SEPARATOR + "level1.json";

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
    Ball[][] matrixTest = matrixBall;

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

        assertEquals(convertMatrixToString(matrixTest), convertMatrixToString(board.getStatusBoard()), "Matrices are not equal ");
    }


/*
    @Test
    void addBallTest(){
        Ball newBall = ballFactory.createStaticBall("RED", position);
        
        

        /* Test to add the ball in the available place 
        matrixTest[1][1] =  newBall;
        board.addBall(1, 1, newBall);
        assertEquals(matrixTest.toString(), matrixBall.toString(), "Errore nell'aggiunta dell'ultima pallina");
        
        /* Test to add the ball in the rong place 
        matrixTest[4][0] =  newBall;
        board.addBall(2, 1, newBall);
        assertEquals(matrixTest.toString(), matrixBall.toString(), "Errore pallina esistente");
        
    }
    */
}
