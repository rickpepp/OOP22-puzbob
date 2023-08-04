package it.unibo.puzbob.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicsTest {

    Physics fisica = new PhysicsImpl(new Pair<Double,Double>(15.0, 10.0), 5, new Pair<Double,Double>(7.5,0.0));

    FlyingBallImpl ball2 = new FlyingBallImpl("WHITE", 3, 0.5, new Pair<Double,Double>(7.5, 0.0));

    @Test
    void positioningTest() {
        // Shot without bounce 25°
        assertEquals(2.968, 
            fisica.getBallPosition(ball2, 25, 1).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(2.113, 
            fisica.getBallPosition(ball2, 25, 1).getY(), 
            0.2, 
            "Not the result expected");
        
        // Shot without bounce 165°
        assertEquals(12.330, 
            fisica.getBallPosition(ball2, 165, 1).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(1.294, 
            fisica.getBallPosition(ball2, 165, 1).getY(), 
            0.2, 
            "Not the result expected");

        // Shot with 1 bounce 25°
        assertEquals(2.017, 
            fisica.getBallPosition(ball2, 25, 2).getX(), 
            0.2, 
            "Not the result expected");

        assertEquals(3.961, 
            fisica.getBallPosition(ball2, 25, 2).getY(), 
            0.3, 
            "Not the result expected");

        // Shot with 1 bounce 150°
        assertEquals(13.109, 
            fisica.getBallPosition(ball2, 150, 2).getX(), 
            0.3, 
            "Not the result expected");

        assertEquals(4.446, 
            fisica.getBallPosition(ball2, 150, 2).getY(), 
            0.3, 
            "Not the result expected");
        
        // Shot with 2 bounces 25°
        assertEquals(12.374, 
            fisica.getBallPosition(ball2, 45, 6).getX(), 
            0.3, 
            "Not the result expected");

        assertEquals(17.060, 
            fisica.getBallPosition(ball2, 45, 6).getY(), 
            1, 
            "Not the result expected");

    }
    
}
