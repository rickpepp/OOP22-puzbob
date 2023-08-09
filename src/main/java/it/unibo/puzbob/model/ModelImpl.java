package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

public class ModelImpl implements Model{

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String COLOR_FILE = "levels" + FILE_SEPARATOR + "colors.json";
    private final Pair<Integer, Integer> DIMENSION = new Pair<Integer,Integer>(8, 8);

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

    public ModelImpl(double heightBoard, double widthBoard, double sizeBall, int nLevel, double velocity, Pair<Double,Double> cannonPosition){
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
        this.physics = new PhysicsImpl(this.board.getBoardSize(), velocity, cannonPosition, sizeBall);
    }

    
    public void changeCannonAngle(int angle){
        this.cannon.changeAngle(angle);
    }

    public void shot(){
        Pair<Integer, Integer> positionFlyingBall;
        FlyingBallImpl ball = new FlyingBallImpl(this.cannon.getCurrentBall().getColor(), this.cannon.getCurrentBall().getScore(), this.cannon.getCurrentBall().getBallSize(), this.cannon.getCannonPosition());

        this.cannon.shot();
        positionFlyingBall =  this.physics.isAttached(this.wall.getPosition(), board.getStatusBoard(), ball);
        this.board.addBall(positionFlyingBall.getY(), positionFlyingBall.getX(), ball);
        this.score.incScore(this.board.removeBall(positionFlyingBall.getY(), positionFlyingBall.getX(), ball));
        this.cannon.createBall(this.board.getColors());

    }


    // Update time (needed for Physics class)
    public void updateTime(long currentTime){

    }

    public int getScore(){
        return this.score.getScore();
    }

    public Ball[][] getMatrixBall(){
        return this.board.getStatusBoard();
    }

    public FlyingBallImpl getFlyingBall(){
        FlyingBallImpl ball = new FlyingBallImpl(this.cannon.getCurrentBall().getColor(), this.cannon.getCurrentBall().getScore(), this.cannon.getCurrentBall().getBallSize(), this.cannon.getCannonPosition());
        return ball;
    }

    public int getCannonAngle(){
        return this.cannon.getAngle();
    }

    public double getWallHeigth(){
        return this.wall.getPosition();
    }

    public GameStatus getGameStatus(){
        return GameStatus.RUNNING;
    }
}
