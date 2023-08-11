package it.unibo.puzbob.model;

import java.util.ArrayList;

/** This is the interface for board It is the base for the playing field*/
public interface Board {
    
    /** Method for adding balls to the playing field */
    void addBall(int x, int y, Ball ball);

    /** Method for removing all balls of the same color and for those that are left free
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
