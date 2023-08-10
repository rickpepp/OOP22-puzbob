package it.unibo.puzbob.model;

import java.util.ArrayList;

/** This is the implementation of Cannon interface */
public class CannonImpl implements Cannon{
    
    private final int MAX_ANGLE = 160;
    private final int MIN_ANGLE = 20;
    private final int CENTER_ANGLE = 90;

    private int angle;
    private BallFactory ballFactory;
    private Ball ball;
    private Pair<Double,Double> cannonPosition;

    public CannonImpl(BallFactory ballFactory, Pair<Double,Double> cannonPosition){
        this.angle = CENTER_ANGLE;
        this.ballFactory = ballFactory;
    }

    /** This method takes as input how many want to move the angle of the cannon */
    public void changeAngle(int angle){
        this.angle = this.angle + angle;
        if(this.angle < MIN_ANGLE){
            this.angle = MIN_ANGLE;
        }else if(this.angle > MAX_ANGLE){
            this.angle = MAX_ANGLE;
        }
    }

    /** This method return the current angle of the cannon */
    public int getAngle(){
        return this.angle;
    }

    /** This method return the current ball there is in the cannon */
    public Ball getCurrentBall(){
       return this.ball;
    }

    /** This method return the position of the cannon */
    public Pair<Double,Double> getCannonPosition(){
        return this.cannonPosition;
    }

    /** This method return which is the ball that will shot the cannon*/
    public void createBall(ArrayList<String> colors){
        int indexColor =  (int)Math.floor(Math.random() * colors.size());
        String color = colors.get(indexColor);
        if(this.ball == null){
            this.ball = ballFactory.createFlyingBall(color, this.cannonPosition);
        }
    }

    /** This method "shoots" the ball out of the cannon */
    public void shot(){
        this.ball = null;
    }

    public String toString(){
        return "Cannon has angle: " + this.angle;
    }
}
