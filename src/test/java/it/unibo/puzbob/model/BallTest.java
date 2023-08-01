package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This test analyse if the output is the expected one. This test is done white BallImpl and FlyingBallImpl.
 */

public class BallTest {

    Ball ball1 = new BallImpl("BLACK", 15, 72);
    Ball ball2 = new FlyingBallImpl("WHITE", 3, 50.7, new Pair<Double,Double>(0.0, 0.0));

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
}
