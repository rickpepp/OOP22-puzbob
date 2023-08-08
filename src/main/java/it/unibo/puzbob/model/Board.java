package it.unibo.puzbob.model;

import java.util.ArrayList;

public interface Board {
    
    void addBall(int x, int y, Ball ball);

    void removeBall(int x, int y, Ball ball);

    Ball[][] getStatusBoard(); 
    
    Pair<Double, Double> getBoardSize();

    ArrayList<String> getColors();

}
