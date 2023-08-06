package it.unibo.puzbob.model;

public interface Model {

    // Change angle of the cannon
    public void changeCannonAngle(int angle);

    // Shot the ball
    public void shot();

    // Update time (needed for Physics class)
    public void updateTime(long currentTime);

    // Getter
    public int getScore();
    public Ball[][] getMatrixBall();
    public FlyingBallImpl getFlyingBall();
    public int getCannonAngle();
    public double getWallHeigth();

    // This return an enum GameStatus (RUNNING, WIN, LOST) with the status of the game
    public GameStatus getGameStatus();
}
