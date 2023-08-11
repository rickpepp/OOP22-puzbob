package it.unibo.puzbob.model;

import java.util.ArrayList;

/** This is the interface for board It is the base for the playing field*/
public interface Board {
    
    /** Method for adding balls to the playing field 
     * @param x is the number of the row where to add the ball
     * @param y is the number of the column where to add the ball
     * @param ball is the ball to add
    */
    void addBall(int x, int y, Ball ball);

    /** Method for removing all balls of the same color and for those that are left free
     * @param x is the number of the row where to add the ball
     * @param y is the number of the column where to add the ball
     * @param ball is the ball to add
     * @return the score of the removed balls
     */
    int removeBall(int x, int y, Ball ball);

    /** Getter method to know the state of the board
     * @return an matrix of balls
     */
    Ball[][] getStatusBoard(); 
    
    /** Getter method to know the size of the board
     * @return a pair of double values: heightand width
     */
    Pair<Double, Double> getBoardSize();

    /** Getter method toknow the colors of the balls in the board 
     * @return a list of the colors present
     */
    ArrayList<String> getColors();

}
