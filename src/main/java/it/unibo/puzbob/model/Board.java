package it.unibo.puzbob.model;

import java.util.ArrayList;

public interface Board {
    
    void addBall(int x, int y, Ball ball);

    void removeBall(int x, int y);

    Ball[][] getStatusBoard(); 
    
    Pair<Double, Double> getBoardSize();

    ArrayList<String> getColors();

    void removeBallAd(int r, int c, String color);
}
