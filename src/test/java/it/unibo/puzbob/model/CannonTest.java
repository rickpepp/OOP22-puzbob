package it.unibo.puzbob.model;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CannonTest {

    private final int SIZE_BALL = 10; 
    private final Pair<Double, Double> POSITION = new Pair<Double, Double>(10.0,20.0);
    
    private Map<String, Integer> colorMap = new HashMap<String, Integer>();
    private BallFactory ballFactory;
    private ArrayList<String> colors = new ArrayList<>();

    private Cannon cannon;

    @Test
    void changeAngleTest(){
        ballFactory = new BallFactoryImpl(colorMap, SIZE_BALL);
        assertEquals(90, cannon.getAngle(), "Angle need to be 90");
        cannon.changeAngle(90);
        assertEquals(160, cannon.getAngle(), "Angle need to be 160");
        cannon.changeAngle(-180);
        assertEquals(20, cannon.getAngle(), "Angle need to be 20");
    }

    @Test
    void createBallTest(){
        colorMap.put("GREEN", 15);
        colorMap.put("PURPLE", 20);
        colorMap.put("YELLOW", 30);
        colorMap.put("BLUE", 25);
        colorMap.put("WHITE", 35);

        ballFactory = new BallFactoryImpl(colorMap, SIZE_BALL);
        cannon = new CannonImpl(ballFactory, POSITION);

        colors.add("RED");
        colors.add("GREEN");
        colors.add("PURPLE");

        cannon.createBall(colors);

        assertEquals(true, colors.contains(cannon.getCurrentBall().getColor()), "Color is not listed");
    }

    @Test
    void shotTest(){
        colorMap.put("GREEN", 15);
        colorMap.put("PURPLE", 20);
        colorMap.put("YELLOW", 30);
        colorMap.put("BLUE", 25);
        colorMap.put("WHITE", 35);
        colors.add("RED");
        colors.add("GREEN");

        ballFactory = new BallFactoryImpl(colorMap, SIZE_BALL);
        cannon = new CannonImpl(ballFactory, POSITION);

        cannon.createBall(colors);
        cannon.shot();

        assertNull(cannon.getCurrentBall(), "Is not null");
    }
}


