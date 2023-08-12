package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** This is the class that implements the board interface */
public class BoardImpl implements Board{
    
    private final int ROW_MATRIX = 8;
    private final int COLUMN_MATRIX = 8;

    private Pair<Double, Double> dimension;
    private Ball[][] matrix;
    private Map<Pair<Integer,Integer>,Ball> ball4Remove = new HashMap<>();
    private Map<Pair<Integer,Integer>,Ball> ballFree4Remove = new HashMap<>();
    private Map<Pair<Integer,Integer>,Ball> ballChecked = new HashMap<>();
    private int score;

    /** Constructor of the class
     * @param height is the height of the board
     * @param width is the width of the board
     * @param matrixBall is the initial matrix of balls
    */
    public BoardImpl(Double height, Double width, Ball[][] matrixBall){
        this.dimension = new Pair<Double, Double>(width, height);
        this.matrix = matrixBall;
    }

    /** This method takes input the position where the ball is to be added and the ball to add */
    public void addBall(int x, int y, Ball ball){
        if(matrix[x][y] == null){
            matrix[x][y] = ball; 
        }
    }

    /** This method takes input the position of the ball to be removed 
     * @param x is the number of the row
     * @param y is the number of the column
     * @param ball is the ball to  be removed
     * @return is the sum of the scores of the balls removed 
    */
    public int removeBall(int x, int y, Ball ball){
        Pair<Integer, Integer> positionBall = new Pair<>(x,y);
        this.score = 0;
        
        checkRemoveBall(positionBall, ball);
    
        if(this.ball4Remove.size() >= 3){
            remove(this.ball4Remove);
        }
        this.ball4Remove.clear();

        for(int i = ROW_MATRIX - 1; i > 0; i--){
            for(int k = 0; k < COLUMN_MATRIX; k++){
                if(this.matrix[i][k] != null){
                    if(checkContain(new Pair<Integer,Integer>(i, k), this.ballChecked) == false){
                        checkFreeBall(i , k);
                    }
                }
            }
        } 

        remove(this.ballFree4Remove);
        this.ballFree4Remove.clear();
        this.ballChecked.clear();
        
        return this.score;       
    }

    /* This method takes as input the position of a ball and the ball itself and searches for adjacent balls */
    private Map<Pair<Integer,Integer>,Ball> searchNeighbour(int row, int column){
        ArrayList<Pair<Integer,Integer>> positionToCheck = new ArrayList<>();
        Map<Pair<Integer,Integer>,Ball> neighbour = new HashMap<>();
        
        positionToCheck.add(new Pair<Integer,Integer>(row - 1, column));
        positionToCheck.add(new Pair<Integer,Integer>(row, column - 1));
        positionToCheck.add(new Pair<Integer,Integer>(row + 1, column));
        positionToCheck.add(new Pair<Integer,Integer>(row, column + 1));

        if(row % 2 != 0){
            positionToCheck.add(new Pair<Integer, Integer>(row - 1, column + 1));
            positionToCheck.add(new Pair<Integer,Integer>(row + 1, column + 1));
        }else{
            positionToCheck.add(new Pair<Integer,Integer>(row - 1, column - 1));
            positionToCheck.add(new Pair<Integer,Integer>(row + 1, column - 1));
        }
        
        for (Pair<Integer,Integer> ball : positionToCheck) {
            try {
                if(this.matrix[ball.getX()][ball.getY()] != null){
                    neighbour.put(ball,this.matrix[ball.getX()][ball.getY()]);
                }
            }catch(Exception e) {
            }
        }

        return neighbour;
    }

    /* This method takes as input a ball and maps the balls adjacent to it*/
    private Map<Pair<Integer,Integer>,Ball> sameColorFinder(Map<Pair<Integer,Integer>,Ball> neighbour, Ball ball){
        Map<Pair<Integer,Integer>,Ball> sameColor = new HashMap<>();
        for (Pair<Integer, Integer> positionCurrentBall : neighbour.keySet()) {
            if(neighbour.get(positionCurrentBall).getColor().compareTo(ball.getColor()) == 0){
                sameColor.put(positionCurrentBall, neighbour.get(positionCurrentBall));
            }
        }
        return sameColor;
    }
   
    /* This method checks whether the position passed as input is contained in the map*/
    private boolean checkContain(Pair<Integer, Integer> positionBall, Map<Pair<Integer,Integer>,Ball> map){
        for (Pair<Integer,Integer> position : map.keySet()) {
                if(positionBall.getX() == position.getX() && positionBall.getY() == position.getY()){
                    return true;
                }else{continue;}
        }
        return false;
    }

    /* This method checks which balls are to be removed recursively*/
    private void checkRemoveBall(Pair<Integer, Integer> positionBall, Ball ball){
        Map<Pair<Integer,Integer>,Ball> neighbour = new HashMap<>();
        Map<Pair<Integer,Integer>,Ball> neighbourSameColor = new HashMap<>();

        this.ball4Remove.put(positionBall, ball);
        neighbour = searchNeighbour(positionBall.getX(), positionBall.getY());
        neighbourSameColor = sameColorFinder(neighbour, ball);
        for (Pair<Integer, Integer> positionCurrentBall : neighbourSameColor.keySet()) {
            if(checkContain(positionCurrentBall, this.ball4Remove) == false){
                checkRemoveBall(positionCurrentBall, neighbourSameColor.get(positionCurrentBall));
            }
        }
    }

    /* This method removes the balls passed in input */
    private void remove(Map<Pair<Integer,Integer>,Ball> mapBall){
        for (Pair<Integer, Integer> position : mapBall.keySet()) {
            this.score = this.score + this.matrix[position.getX()][position.getY()].getScore();
            this.matrix[position.getX()][position.getY()] = null;
        }
    }

    /* This method looks for the balls that are not connected to the others */
    private boolean checkFreeBall(int row, int column){
        Pair<Integer, Integer> position = new Pair<>(row, column);
        Map<Pair<Integer,Integer>,Ball> neighbour = new HashMap<>();

        if(checkContain(position, this.ballChecked)){
            return false;
        }else{
            this.ballChecked.put(position, this.matrix[position.getX()][position.getY()]);
            neighbour = searchNeighbour(row, column);
            for(Pair<Integer,Integer> currentPosition: neighbour.keySet()){
                if(currentPosition.getX() == 0){
                    return false;
                }else{
                    return checkFreeBall(currentPosition.getX(), currentPosition.getY());
                }
            }
        }
        this.ballFree4Remove.put(position, this.matrix[position.getX()][position.getY()]);
        return true;
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

    /* This method is used to print matrices with the same format so that the values can be better controlled */
    private String convertMatrixToString(Ball[][] matrix){
        String matrixToString = new String();
        for(int i = 0;  i < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++)
                matrixToString = matrixToString + "|" + matrix[i][k];
            
            matrixToString = matrixToString + "\n";
        }
        return matrixToString;
    }

    public String toString(){
        return "Board dimensions are height:" + dimension.getX() + " width: " + dimension.getY() + "\n Ball matrix:\n" + convertMatrixToString(this.matrix);
    }

}


