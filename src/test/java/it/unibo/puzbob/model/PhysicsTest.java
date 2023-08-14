package it.unibo.puzbob.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicsTest {

    // File separator and the path with colors.json and level1.json
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String COLORS_PATH = "levels" + FILE_SEPARATOR + "colors.json";
    public static final String LEVEL1_PATH = "levels" + FILE_SEPARATOR + "level1.json";

    public static final Double BALL_SIZE = 1.875;
    public static final int MATRIX_DIMENSION = 10;
    public static final double X_STARTING_POSITION = 7.5;
    public static final double Y_STARTING_POSITION = 0.0;
    public static final double DELTA_TIME = 0.05;

    // Ball factory to create balls
    BallFactory ballFactory = new BallFactoryImpl(
        JSONParserImpl.getIstance().parserColors(JSONReaderImpl.getIstance().readJSONFromFile(COLORS_PATH)), BALL_SIZE);

    // Level will load del selected level
    Level testLevel = new LevelImpl(this.ballFactory, new Pair<Integer,Integer>(MATRIX_DIMENSION, MATRIX_DIMENSION));

    // Physics will calc the ball positioning
    Physics fisica = new PhysicsImpl(new Pair<Double,Double>(15.0, 10.0), 5, 
        new Pair<Double,Double>(X_STARTING_POSITION,Y_STARTING_POSITION), ballFactory.getBallDimension());

    // Load Level 1
    Ball[][] matrixBalls = testLevel.getStartBalls(JSONParserImpl.getIstance().parserStarterBalls(JSONReaderImpl.getIstance().readJSONFromFile(LEVEL1_PATH)));

    FlyingBallImpl ball = (FlyingBallImpl) ballFactory.createFlyingBall("RED", 
        new Pair<Double,Double>(X_STARTING_POSITION,Y_STARTING_POSITION));

    // Testing if the positionc calc work as expected
    @Test
    void positioningTest() {
        // Shot without bounce 25°
        assertEquals(2.968, 
            fisica.getBallPosition(ball, 25, 1).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(2.113, 
            fisica.getBallPosition(ball, 25, 1).getY(), 
            0.2, 
            "Not the result expected");
        
        // Shot without bounce 165°
        assertEquals(12.330, 
            fisica.getBallPosition(ball, 165, 1).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(1.294, 
            fisica.getBallPosition(ball, 165, 1).getY(), 
            0.2, 
            "Not the result expected");

        // Shot with 1 bounce 25°
        assertEquals(3.543, 
            fisica.getBallPosition(ball, 25, 2).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(3.961, 
            fisica.getBallPosition(ball, 25, 2).getY(), 
            0.3, 
            "Not the result expected");

        // Shot with 1 bounce 150°
        assertEquals(11.756, 
            fisica.getBallPosition(ball, 150, 2).getX(), 
            0.3, 
            "Not the result expected");

        assertEquals(4.446, 
            fisica.getBallPosition(ball, 150, 2).getY(), 
            0.3, 
            "Not the result expected");
        
        // Shot with 2 bounces 25°
        assertEquals(9.899, 
            fisica.getBallPosition(ball, 45, 6).getX(), 
            0.3, 
            "Not the result expected");

        assertEquals(18.084, 
            fisica.getBallPosition(ball, 45, 6).getY(), 
            1, 
            "Not the result expected");
    }

    // Test if the isAttached method work as expected in physics
    @Test
    void basicIndexesTest() {

        // Create a simple ball matrix with one ball
        Ball[][] basicMatrixBall = new Ball[10][10];
        basicMatrixBall[0][4] = ballFactory.createStaticBall("RED");

        // Create a FlyingBall
        FlyingBallImpl flyinfBall = (FlyingBallImpl) ballFactory.createFlyingBall("RED", 
            new Pair<Double,Double>(1.0, 9.1));

        // Positioning at the top-left
        Pair<Integer, Integer> indexes = fisica.isAttached(0, basicMatrixBall, flyinfBall);

        assertEquals(0, indexes.getY(), "Y need to be 0");
        assertEquals(0, indexes.getX(), "X need to be 0");

        // Positioning at the bottom of the existing ball
        flyinfBall.setPosition(new Pair<Double,Double>(7.5, 8.0));
        indexes = fisica.isAttached(0, basicMatrixBall, flyinfBall);

        assertEquals(1, indexes.getY(), "Y need to be 1");
        assertEquals(3, indexes.getX(), "X need to be 3");

        // There is no position because is still in the air
        flyinfBall.setPosition(new Pair<Double,Double>(6.5, 5.0));
        indexes = fisica.isAttached(0, basicMatrixBall, flyinfBall);

        assertEquals(null, indexes, "Y need to be null");
        assertEquals(null, indexes, "X need to be null");

        // Position at the top with the wall down
        flyinfBall.setPosition(new Pair<Double,Double>(7.4, 5.0));
        indexes = fisica.isAttached(4.0, basicMatrixBall, flyinfBall);

        assertEquals(0, indexes.getY(), "Y need to be 0");
        assertEquals(3, indexes.getX(), "X need to be 3");
    }

    // Test a shot simulation
    // Shot of 173° in level1
    @Test
    void physicsTest1() {
        // Create a FlyingBall
        FlyingBallImpl flyinfBall = (FlyingBallImpl) ballFactory.createFlyingBall("RED", 
            new Pair<Double,Double>(X_STARTING_POSITION, Y_STARTING_POSITION));

        Pair<Integer, Integer> result = null;
        double time = 0;

        // This simulate a shot, check the position every 0.05 time unit
        while (result == null) {
            Pair<Double, Double> position = fisica.getBallPosition(flyinfBall, 173, time);
            flyinfBall.setPosition(position);
            System.out.println(position);
            result = fisica.isAttached(0, matrixBalls, flyinfBall);
            time += DELTA_TIME;
        }

        assertEquals(0, result.getX(), "X need to be 2");
        assertEquals(4, result.getY(), "Y need to be 4");
    }

    @Test
    // Shot of 15° in level1
    void physicsTest2() {
        // Create a FlyingBall
        FlyingBallImpl flyinfBall = (FlyingBallImpl) ballFactory.createFlyingBall("RED", 
            new Pair<Double,Double>(X_STARTING_POSITION, Y_STARTING_POSITION));

        Pair<Integer, Integer> result = null;
        double time = 0;

        while (result == null) {
            Pair<Double, Double> position = fisica.getBallPosition(flyinfBall, 15, time);
            flyinfBall.setPosition(position);
            System.out.println(position);
            result = fisica.isAttached(0, matrixBalls, flyinfBall);
            time += DELTA_TIME;
        }

        assertEquals(1, result.getX(), "X need to be 3");
        assertEquals(4, result.getY(), "Y need to be 4");
    }

    @Test
    // Shot of 25° in level1 whitout 2 the first two balls of the 4rd row
    void physicsTest3() {
        // Create a FlyingBall
        FlyingBallImpl flyinfBall = (FlyingBallImpl) ballFactory.createFlyingBall("RED", 
            new Pair<Double,Double>(X_STARTING_POSITION, Y_STARTING_POSITION));

        matrixBalls[3][0] = null;
        matrixBalls[3][1] = null;

        Pair<Integer, Integer> result = null;
        double time = 0;

        while (result == null) {
            Pair<Double, Double> position = fisica.getBallPosition(flyinfBall, 25, time);
            flyinfBall.setPosition(position);
            System.out.println(position);
            result = fisica.isAttached(0, matrixBalls, flyinfBall);
            time += DELTA_TIME;
        }

        assertEquals(1, result.getX(), "X need to be 2");
        assertEquals(3, result.getY(), "Y need to be 4");
    }
    
}
