package it.unibo.puzbob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardImpl implements Board{
    
    private final int ROW_MATRIX = 10;
    private final int COLUMN_MATRIX = 10;

    private Pair<Double, Double> dimension;
    public Ball[][] matrix;
    private Map<Pair<Integer,Integer>,Ball> ball4Remove = new HashMap<>();
    private Map<Pair<Integer,Integer>,Ball> ballFree4Remove = new HashMap<>();
    private Map<Pair<Integer,Integer>,Ball> ballChecked = new HashMap<>();

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
    public void removeBall(int x, int y, Ball ball){
        Pair<Integer, Integer> positionBall = new Pair<>(x,y);
        checkRemoveBall(positionBall, ball);
    
        if(ball4Remove.size() >= 3){
            remove(ball4Remove);
        }


        for(int i = 1; i < ROW_MATRIX; i++){
            for(int k = 0; k < COLUMN_MATRIX; k++){
                if(this.matrix[i][k] != null){
                    if(checkContain(new Pair<Integer,Integer>(i, k), ballChecked) == false){
                        if(checkFreeBall(i , k) == true){
                            //System.out.println("ho trovato una pallina libera");
                            remove(ballFree4Remove);
                            this.ballFree4Remove.clear();
                            this.ballChecked.clear();
                        }
                    }
                }
            }
        }

        //System.out.println(this.ballFree4Remove);
        //System.out.println(this.ballChecked);
        
    }

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

    private Map<Pair<Integer,Integer>,Ball> sameColorFinder(Map<Pair<Integer,Integer>,Ball> neighbour, Ball ball){
        Map<Pair<Integer,Integer>,Ball> sameColor = new HashMap<>();
        for (Pair<Integer, Integer> positionCurrentBall : neighbour.keySet()) {
            if(neighbour.get(positionCurrentBall).getColor() == ball.getColor()){
                sameColor.put(positionCurrentBall, neighbour.get(positionCurrentBall));
            }
        }
        return sameColor;
    }
    
    private boolean checkContain(Pair<Integer, Integer> positionBall, Map<Pair<Integer,Integer>,Ball> map){
        for (Pair<Integer,Integer> position : map.keySet()) {
                if(positionBall.getX() == position.getX() && positionBall.getY() == position.getY()){
                    return true;
                }else{continue;}
        }
        return false;
    }

    private void checkRemoveBall(Pair<Integer, Integer> positionBall, Ball ball){
        Map<Pair<Integer,Integer>,Ball> neighbour = new HashMap<>();
        Map<Pair<Integer,Integer>,Ball> neighbourSameColor = new HashMap<>();

        ball4Remove.put(positionBall, ball);
        neighbour = searchNeighbour(positionBall.getX(), positionBall.getY());
        neighbourSameColor = sameColorFinder(neighbour, ball);
        for (Pair<Integer, Integer> positionCurrentBall : neighbourSameColor.keySet()) {
            if(checkContain(positionCurrentBall, ball4Remove) == false){
                checkRemoveBall(positionCurrentBall, neighbourSameColor.get(positionCurrentBall));
            }
        }
    }

    private void remove(Map<Pair<Integer,Integer>,Ball> mapBall){
        for (Pair<Integer, Integer> position : mapBall.keySet()) {
            //System.out.println("forse sono il null: " + this.matrix[position.getX()][position.getY()]);
            this.matrix[position.getX()][position.getY()] = null;
            
        }
    }

    private boolean checkFreeBall(int row, int column){
        Pair<Integer, Integer> position = new Pair<>(row, column);
        Map<Pair<Integer,Integer>,Ball> neighbour = new HashMap<>();

        if(checkContain(position, ballChecked)){
            return false;
        }else{
            ballChecked.put(position, this.matrix[position.getX()][position.getY()]);
            neighbour = searchNeighbour(row, column);
            for(Pair<Integer,Integer> currentPosition: neighbour.keySet()){
                if(currentPosition.getX() == 0){
                    return false;
                }else{
                    return checkFreeBall(currentPosition.getX(), currentPosition.getY());
                }
            }
        }
        ballFree4Remove.put(position, this.matrix[position.getX()][position.getY()]);
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

    public String toString(){
        return "Board dimensions are height:" + dimension.getX() + " width: " + dimension.getY();
    }

/*
    public static void main (String[] args){
        
        Ball[][] matrixBall = new Ball[10][10];
        
        matrixBall[0][0] = new BallImpl("RED", 10, 40);
        matrixBall[0][1] = new BallImpl("RED", 10, 40);
        matrixBall[0][2] = new BallImpl("YELLOW", 10, 40);
        matrixBall[0][3] = new BallImpl("YELLOW", 10, 40);
        matrixBall[0][4] = new BallImpl("BLUE", 10, 40);
        matrixBall[0][5] = new BallImpl("BLUE", 10, 40);
        matrixBall[0][6] = new BallImpl("GREEN", 10, 40);
        matrixBall[0][7] = new BallImpl("GREEN", 10, 40);
        matrixBall[1][0] = new BallImpl("RED", 10, 40);
        matrixBall[1][1] = new BallImpl("RED", 10, 40);
        matrixBall[1][2] = new BallImpl("YELLOW", 10, 40);
        matrixBall[1][3] = new BallImpl("YELLOW", 10, 40);
        matrixBall[1][4] = new BallImpl("BLUE", 10, 40);
        matrixBall[1][5] = new BallImpl("BLUE", 10, 40);
        matrixBall[1][6] = new BallImpl("GREEN", 10, 40);
        matrixBall[2][0] = new BallImpl("BLUE", 10, 40);
        matrixBall[2][1] = new BallImpl("BLUE", 10, 40);
        matrixBall[2][2] = new BallImpl("GREEN", 10, 40);
        matrixBall[2][3] = new BallImpl("GREEN", 10, 40);
        matrixBall[2][4] = new BallImpl("RED", 10, 40);
        matrixBall[2][5] = new BallImpl("RED", 10, 40);
        matrixBall[2][6] = new BallImpl("YELLOW", 10, 40);
        matrixBall[2][7] = new BallImpl("YELLOW", 10, 40);
        matrixBall[3][0] = new BallImpl("BLUE", 10, 40);
        matrixBall[3][1] = new BallImpl("GREEN", 10, 40);
        matrixBall[3][2] = new BallImpl("GREEN", 10, 40);
        matrixBall[3][3] = new BallImpl("RED", 10, 40);
        matrixBall[3][4] = new BallImpl("RED", 10, 40);
        matrixBall[3][5] = new BallImpl("YELLOW", 10, 40);
        matrixBall[3][6] = new BallImpl("YELLOW", 10, 40);
        
        Board board = new BoardImpl(300.0, 200.0, matrixBall);
        Ball greenBall = new BallImpl("GREEN", 15, 40);
        String matrixToString = new String();

        board.addBall(4, 0, greenBall);

        board.removeBall(3, 0, matrixBall[3][0]);

        for(int i = 0;  i < 10; i++){
            for(int k = 0; k < 10; k++)
                matrixToString = matrixToString + "|" + matrixBall[i][k];
            
            matrixToString = matrixToString + "\n\n";
        }
        System.out.println(matrixToString);
    }*/
}


