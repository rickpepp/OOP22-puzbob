package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** This is the implementation of Cannon interface */
public class CannonImpl implements Cannon{
    
    private final int MAX_ANGLE = 160;
    private final int MIN_ANGLE = 20;
    private final int CENTER_ANGLE = 90;

    private int angle;

    public CannonImpl(){
        this.angle = CENTER_ANGLE;
    }

    /** This method takes as input how many want to move the angle of the cannon */
    public void changeAngle(int angle){
        this.angle = this.angle + angle;
        if(this.angle < MIN_ANGLE && this.angle > MAX_ANGLE){
            this.angle = CENTER_ANGLE;
        }
    }

    public int getAngle(){
        return this.angle;
    }

    /*
    public Ball getCurrentBall(ArrayList<String> colors){
        Pair<Double,Double> positionBall = new Pair<Double,Double>(0.0, 0.0);
        Ball ball = new BallFactoryImpl(, 72);
        int indexColor =  (int)Math.random() * colors.size();
        String color = colors.get(indexColor);
        return ball.createFlyingBall(color, positionBall);
    }
    */
}
