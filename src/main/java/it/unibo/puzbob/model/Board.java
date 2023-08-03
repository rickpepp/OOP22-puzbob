package it.unibo.puzbob.model;

import java.util.ArrayList;

public interface Board {
    
    void addBall(int x, int y, Ball ball);

    void removeBall(int x, int y);

    Ball[][] getStatusBoard(); 
    
    Pair<Integer, Integer> getBoardSize();

    ArrayList<String> getColors();
}
