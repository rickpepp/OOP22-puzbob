package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * This test analyse if the output is the expected one. This test is done white BallImpl and FlyingBallImpl.
 */

public class BallTest {

    Ball ball1 = new BallImpl("BLACK", 15, 72);
    Ball ball2 = new FlyingBallImpl("WHITE", 3, 50.7, new Pair<Double,Double>(0.0, 0.0));

    Map<String, Integer> colorMap = new HashMap<>();

    // Next 3 test analyse basic property of ball
    @Test 
    void colorTest() {
        assertEquals("BLACK", ball1.getColor(), "Color need to be black");
        assertEquals("WHITE", ball2.getColor(), "Color need to be white");
    }

    @Test 
    void scoreTest() {
        assertEquals(15, ball1.getScore(), "Score need to be 15");
        assertEquals(3, ball2.getScore(), "Score need to be 3");
    }

    @Test 
    void sizeTest() {
        assertEquals(72, ball1.getBallSize(), "Size need to be 72");
        assertEquals(50.7, ball2.getBallSize(), "Size need to be 50.7");
    }

    @Test 
    void positionTest() {
        assertEquals(0, ((FlyingBallImpl) ball2).getPosition().getX(), "X Position need to be 0");
        assertEquals(0, ((FlyingBallImpl) ball2).getPosition().getY(), "Y Position need to be 0");

        // Try to change the position and check if the output is the expected
        ((FlyingBallImpl) ball2).setPosition(new Pair<Double,Double>(50.0, 15.0));

        assertEquals(50, ((FlyingBallImpl) ball2).getPosition().getX(), "X Position need to be 50");
        assertEquals(15, ((FlyingBallImpl) ball2).getPosition().getY(), "Y Position need to be 15");
    }

    // Here we analyze if BallFactory work as expected
    @Test
    void factoryTest() {

        // Put the color on the colormap
        colorMap.put("RED", 15);
        colorMap.put("BLUE", 45);
        colorMap.put("WHITE", 25);

        BallFactory ballFactory = new BallFactoryImpl(colorMap, 15);

        Pair<Double, Double> position = new Pair<Double,Double>(0.0, 0.0);
        Pair<Double, Double> newposition = new Pair<Double,Double>(15.1, 12.3);

        Ball ball3 = ballFactory.createStaticBall("RED");
        Ball ball4 = ballFactory.createFlyingBall("WHITE", position);

        // Check method of static ball
        assertEquals("RED", ball3.getColor(), "Color need to be red");
        assertEquals("WHITE", ball4.getColor(), "Color need to be white");

        assertEquals(15, ball3.getScore(), "Score need to be 15");
        assertEquals(25, ball4.getScore(), "Score need to be 25");

        assertEquals(15, ball3.getBallSize(), "Size need to be 15");
        assertEquals(15, ball4.getBallSize(), "Size need to be 15");

        // Check method of dynamic ball
        assertEquals(0, ((FlyingBallImpl) ball4).getPosition().getX(), "X Position need to be 0");
        assertEquals(0, ((FlyingBallImpl) ball4).getPosition().getY(), "Y Position need to be 0");

        ((FlyingBallImpl) ball4).setPosition(newposition);

        assertEquals(15.1, ((FlyingBallImpl) ball4).getPosition().getX(), "X new Position need to be 15.1");
        assertEquals(12.3, ((FlyingBallImpl) ball4).getPosition().getY(), "Y new Position need to be 12.3");
    }
}
