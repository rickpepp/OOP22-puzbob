package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/* This test analyse if the output is the expected one. This test is done white BallImpl and FlyingBallImpl. */

public class WallTest {

    @Test
    void wallTest(){
        Wall wall1 = new WallImpl(1.2);
        Wall wall2 = new WallImpl();

        wall1.goDown(1.2);
        wall2.goDown(1.2);
        assertEquals(2.4, wall1.getPosition(), "Position need to be 2.4");
        assertEquals(1.2, wall2.getPosition(), "Position need to be 1.2");
    }
}
