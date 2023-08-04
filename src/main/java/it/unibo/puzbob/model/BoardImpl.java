package it.unibo.puzbob.model;

import java.util.ArrayList;

public class BoardImpl implements Board{
    
    private final int ROW_MATRIX = 10;
    private final int COLUMN_MATRIX = 10;

    private Pair<Double, Double> dimension;
    private Ball[][] matrix;

    public BoardImpl(Double height, Double width, Ball[][] matrixBall){
        this.dimension = new Pair<Double, Double>(height, width);
        this.matrix = matrixBall;
    }

    /**This method takes input the position where the ball is to be added and the ball to add */
    public void addBall(int x, int y, Ball ball){
        if(matrix[x][y] == null){
            matrix[x][y] = ball; 
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
    public Pair<Double, Double> getBoardSize(){
        return this.dimension;
    }

    /** This method return a list of colors that are inside the board */
    public ArrayList<String> getColors(){
        ArrayList<String> colors = new ArrayList<String>();
        for(int i = 0; i  < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++){
                if(this.matrix[i][k] != null){
                    if(checkColor(colors, this.matrix[i][k].getColor()) == false){
                        colors.add(this.matrix[i][k].getColor());
                    }else{
                        continue;
                    }
                }else{
                    continue;
                }
            }
        }
        return colors;
    }

    /* This method is for not having twins color in the color list */
    private boolean checkColor(ArrayList<String> colors, String color){
        if(colors.size() > 0){
            for (String currentColor : colors) {
                if(currentColor == color){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }


    public String toString(){
        return "Board dimensions are height:" + dimension.getX() + " width: " + dimension.getY();
    }
}
