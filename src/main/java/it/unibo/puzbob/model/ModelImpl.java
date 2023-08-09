package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

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
    private long time;
    private int nShot;
    

    public ModelImpl(double heightBoard, double widthBoard, double sizeBall, int nLevel, Pair<Double,Double> cannonPosition){
        this.reader = new JSONReaderImpl();
        this.parser = new JSONParserImpl();
        this.COLOR_MAP = parser.parserColors(reader.readJSONFromFile(COLOR_FILE));
        this.ballFactory = new BallFactoryImpl(COLOR_MAP, sizeBall);
        this.level = new LevelImpl(ballFactory, DIMENSION);
        this.startBallString = "levels" + FILE_SEPARATOR + "level" + nLevel + ".json";
        this.levelMap = parser.parserStarterBalls(reader.readJSONFromFile(this.startBallString));
        this.board = new BoardImpl(heightBoard, widthBoard, level.getStartBalls(this.levelMap));
        this.cannon = new CannonImpl(ballFactory, cannonPosition);
        this.cannon.createBall(board.getColors());
        this.wall = new WallImpl();
        this.score = new ScoreImpl(0);
        this.physics = new PhysicsImpl(this.board.getBoardSize(), VELOCITY, cannonPosition, sizeBall);
        this.nShot = 0;
        this.sizeBall = sizeBall;
    }

    
    public void changeCannonAngle(int angle){
        this.cannon.changeAngle(angle);
    }

    public void shot(){
        Pair<Integer, Integer> positionFlyingBall;
        FlyingBallImpl ball = new FlyingBallImpl(this.cannon.getCurrentBall().getColor(), this.cannon.getCurrentBall().getScore(), this.cannon.getCurrentBall().getBallSize(), this.cannon.getCannonPosition());
        Pair<Double, Double> coordinatesFlyingBall;
        long timeStart = this.time;

        nShot++;
        positionFlyingBall =  null;
        this.cannon.shot();
        
        while(positionFlyingBall == null){
            coordinatesFlyingBall = this.physics.getBallPosition(ball, this.cannon.getAngle(), this.time);
            positionFlyingBall = this.physics.isAttached(this.wall.getPosition(), board.getStatusBoard(), ball);
        }
        
        if(nShot == MAX_SHOT){
            this.wall.goDown(this.sizeBall);
            nShot = 0;
        }

        this.board.addBall(positionFlyingBall.getY(), positionFlyingBall.getX(), ball);
        this.score.incScore(this.board.removeBall(positionFlyingBall.getY(), positionFlyingBall.getX(), ball));
        this.cannon.createBall(this.board.getColors());

    }


    // Update time (needed for Physics class)
    public void updateTime(long currentTime){
        this.time = currentTime;
    }

    public int getScore(){
        return this.score.getScore();
    }

    public Ball[][] getMatrixBall(){
        return this.board.getStatusBoard();
    }

    public FlyingBallImpl getFlyingBall(){
        return new FlyingBallImpl(this.cannon.getCurrentBall().getColor(), this.cannon.getCurrentBall().getScore(), this.cannon.getCurrentBall().getBallSize(), this.cannon.getCannonPosition()); 
    }

    public int getCannonAngle(){
        return this.cannon.getAngle();
    }

    public Ball getCannonBall(){
        return this.cannon.getCurrentBall();
    }

    public double getWallHeigth(){
        return this.wall.getPosition();
    }

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

    public GameStatus getGameStatus(){
        int lineGameOver = DIMENSION.getX() - (int)this.wall.getPosition();
        if(getSizeMatrix() == 0){
            return GameStatus.WIN;
        }else{
            if(checkLineBlank(lineGameOver) == false){
                return GameStatus.LOST;
            }else{
                return GameStatus.RUNNING;
            }
        }
    }
}
