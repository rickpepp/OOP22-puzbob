package it.unibo.puzbob.model;

import java.util.ArrayList;

/** This is an interface for a Cannon  */
public interface Cannon {

    /** Method that changes the angle of the cannon
     * @param angle is how many degrees tomove the cannon
     */
    void changeAngle(int angle);

    /** Getter method for cannon angle
     * @return the angle of the cannon incline
     */
    int getAngle();

    /** Getter method for the ball present on  the cannon
     * @return the ball that is to be shot
     */
    Ball getCurrentBall();

    /** Getter method for the position of the cannon
     * @return a pair of double with the value: x and y
     */
    Pair<Double,Double> getCannonPosition();

    /** Method for creating the next ball on the cannon 
     * @param colors is a list of colors to choose from
     */
    void createBall(ArrayList<String> colors);

    /** Method for launching a ball */
    void shot();
}
