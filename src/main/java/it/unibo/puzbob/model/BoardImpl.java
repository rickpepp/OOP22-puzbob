package it.unibo.puzbob.model;

import java.util.ArrayList;

public class BoardImpl implements Board{
    
    private Pair<Integer, Integer> dimension;
    private Ball[][] matrix;

    public BoardImpl(Integer height, Integer width){
        this.dimension = new Pair<Integer, Integer>(height, width);
        this.matrix = new Ball[300][100];
    }

    /**This method takes input the position where the ball is to be added and the ball to add */
    public void addBall(int x, int y, Ball ball){
        if(matrix[x][y] == null){
            matrix[x][y] = ball; 
        }else{
            matrix[x+1][y+1] = ball;
        }
    }

    /** This method takes input the position of the ball to be removed */
    public void removeBall(int x, int y){
        matrix[x][y] = null;   
    }

    /** This method return the status of board */
    public Ball[][] getStatusBoard(){
        return this.matrix;
    }

    /** This method return the dimension of board width and height */
    public Pair<Integer, Integer> getBoardSize(){
        return this.dimension;
    }

    /** This method return a list of colors that are inside the board */
    public ArrayList<String> getColors(){
        ArrayList<String> colors = new ArrayList<String>();
        return colors;
    }

    public String toString(){
        return "Board dimensions are height:" + dimension.getX() + " width: " + dimension.getY();
    }
}
