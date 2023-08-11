package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

/** This is the implementation of the Model interface */
public class ModelImpl implements Model{

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String COLOR_FILE = "levels" + FILE_SEPARATOR + "colors.json";
    private final Pair<Integer, Integer> DIMENSION = new Pair<Integer,Integer>(8, 8);
    private final double VELOCITY = 2.0;
    private final int MAX_SHOT = 5;
    private final double sizeBall;

    private Map<String, Integer> COLOR_MAP;
    private JSONReader reader;
    private JSONParser parser;
    private BallFactory ballFactory;
    private Level level;
    private Map<String, List<Pair<Integer, Integer>>> levelMap;
    private String startBallString;
    private Board board;
    private Cannon cannon;
    private Wall wall;
    private Score score;
    private Physics physics;
    private FlyingBallImpl flyingBall;
    private long time;
    private int nShot;
    private Lock lock;
    
    /** Constructor of the class
     * @param heightBoard is the height of the board
     * @param widthBoard is the width of the Board
     * @param sizeBall iss the diameter of the ball
     * @param nLevel is the number of the level to be played
     * @param score is the starting score
     */
    public ModelImpl(double heightBoard, double widthBoard, double sizeBall, int nLevel, Score score){
        Pair<Double,Double> cannonPosition = new Pair<Double,Double>(widthBoard / 2, 0.0);
        this.reader = new JSONReaderImpl();
        this.parser = new JSONParserImpl();
        JSONObject json = reader.readJSONFromFile(COLOR_FILE);
        this.COLOR_MAP = parser.parserColors(json);
        this.sizeBall = sizeBall;
        this.ballFactory = new BallFactoryImpl(COLOR_MAP, sizeBall);
        this.level = new LevelImpl(ballFactory, DIMENSION);
        this.startBallString = "levels" + FILE_SEPARATOR + "level" + nLevel + ".json";
        json = reader.readJSONFromFile(this.startBallString);
        this.levelMap = parser.parserStarterBalls(json);
        this.board = new BoardImpl(heightBoard, widthBoard, level.getStartBalls(this.levelMap));
        this.score = score;
        this.cannon = new CannonImpl(ballFactory, cannonPosition);
        this.cannon.createBall(board.getColors());
        this.nShot = 0;
        this.physics = new PhysicsImpl(this.board.getBoardSize(), VELOCITY, cannonPosition, sizeBall);
        this.wall = new WallImpl();
        this.lock = new ReentrantLock();
    }

    /** Taken as input an integer, changes the cannon's angle of gun sight
     * @param angle is the number of degrees by how much the cannon must move
     */
    public void changeCannonAngle(int angle){
        this.cannon.changeAngle(angle);
    }

    /* Method used to enclode the shot of a ball in a  separate thread so as not toblock the Model class */
    private void shotThread(){
        Pair<Integer, Integer> positionFlyingBall =  null;
        Pair<Double, Double> coordinatesFlyingBall;
        long timeStart;

        this.lock.lock();
        try{
            timeStart = this.time;

            this.flyingBall = new FlyingBallImpl(this.cannon.getCurrentBall().getColor(), this.cannon.getCurrentBall().getScore(), this.cannon.getCurrentBall().getBallSize(), this.cannon.getCannonPosition());

            this.cannon.shot();
        }finally{
            this.lock.unlock();
        }

        this.nShot++;
        
        while(positionFlyingBall == null){
            this.lock.lock();
            try{
                coordinatesFlyingBall = this.physics.getBallPosition(this.flyingBall, this.cannon.getAngle(), (this.time - timeStart));
                this.flyingBall.setPosition(coordinatesFlyingBall);
                positionFlyingBall = this.physics.isAttached(this.wall.getPosition(), board.getStatusBoard(), this.flyingBall);
            }finally{
                this.lock.unlock();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException event) {
                event.printStackTrace();
            }
        }

        
        if(nShot == MAX_SHOT){
            this.wall.goDown(this.sizeBall);
            nShot = 0;
        }

        this.lock.lock();
        try{
            this.board.addBall(positionFlyingBall.getY(), positionFlyingBall.getX(), this.flyingBall);
            this.score.incScore(this.board.removeBall(positionFlyingBall.getY(), positionFlyingBall.getX(), this.flyingBall));
            this.cannon.createBall(this.board.getColors());
        }finally{
            this.lock.unlock();
        }
    }

    /** Method that instantiates a new thread and does all the work of adding or removing the ball, in case it lowers the wall and updates the score */
    public void shot(){
        Thread thread = new Thread(() -> {
            shotThread();
        }); 
        thread.start();
    }

    /** Method that updates the time 
     * @param currentTime is the time now
    */
    public void updateTime(long currentTime){
        this.lock.lock();
        try{
            this.time = currentTime;
        }finally{
            this.lock.unlock();
        }
    }

    /** Method that returns the score*/
    public int getScore(){
        return this.score.getScore();
    }

    /** Method that returns the situation of static balls */
    public Ball[][] getMatrixBall(){
        return this.board.getStatusBoard();
    }

    /** Method taht returns the flying ball */
    public FlyingBallImpl getFlyingBall(){
        this.lock.lock();
        try{
            return this.flyingBall; 
        }finally{
            this.lock.unlock();
        }
    }

    /** Method that returns what angle the cannon has */
    public int getCannonAngle(){
        return this.cannon.getAngle();
    }

    /** Method that returns what ball is in the cannon */
    public Ball getCannonBall(){
        this.lock.lock();
        try{
            return this.cannon.getCurrentBall();
        }finally{
            this.lock.unlock();
        }
    }

    /** Method that returns what height the wall is at */
    public double getWallHeigth(){
        return this.wall.getPosition();
    }

    /* Method that calculates whether there are still balls in the board */
    private int getSizeMatrix(){
        int size = 0;
        Ball[][] matrix = this.board.getStatusBoard();
        for(int i = 0; i < DIMENSION.getX(); i++){
            for(int k = 0; k < DIMENSION.getY(); k++){
                if(matrix[i][k] != null){
                    size++;
                }
            }
        }
        return size;
    }

    /* Method that takes as input the number of a row and calculates there are balls */
    private boolean checkLineBlank(int line){
        int count = 0;
        Ball[][] matrix = this.board.getStatusBoard();
        for(int k = 0; k < DIMENSION.getY(); k++){
            if(matrix[line][k] != null){
                count++;
            }else{
                continue;
            }
        }
        if(count == 0){
            return true;
        }else{
            return false;
        }
    }

    /** Method that returns the state of the game */
    public GameStatus getGameStatus(){
        int lineGameOver = DIMENSION.getX() - (int)this.wall.getPosition();
        if(getSizeMatrix() == 0){
            return GameStatus.WIN;
        }else{
            if(checkLineBlank(lineGameOver - 1) == false){
                return GameStatus.LOST;
            }else{
                return GameStatus.RUNNING;
            }
        }
    }

    /** Method that return the position of the Cannon */
    public Pair<Double,Double> getCannonPosition(){
        return this.cannon.getCannonPosition();
    }
}
