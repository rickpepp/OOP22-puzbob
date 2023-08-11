package it.unibo.puzbob.model;

/** Game Model interface. Encapsulates all the logic of the game and all its parts */
public interface Model {

    /** Method change the angle of the cannon 
     * @param angle is the number of degrees of how much to move th cannon 
    */
    public void changeCannonAngle(int angle);

    /** Method for launching the ball that is in the cannon */
    public void shot();

    /** Method to update the time (needed for Physics class) 
     * @param currentTime is the time at the present time
    */
    public void updateTime(long currentTime);

    // Getter
    /** Getter method of the score
     * @return the number of the score
     */
    public int getScore();

    /** Getter method of the state of the balls in the Board
     * @return a matrix of balls  
     */
    public Ball[][] getMatrixBall();

    /** Getter method of the flying ball
     * @return the ball that was launched
     */
    public FlyingBallImpl getFlyingBall();

    /** Getter method of cannon angle
     * @return the number of degrees in which the cannon is inclined
     */
    public int getCannonAngle();

    /** Getter method of wall height
     * @return the size of the height of the wall
     */
    public double getWallHeigth();

    /** Getter method of the ball present on the cannon
     * @return the ball that is to launch the cannon
     */
    public Ball getCannonBall();

    /** Getter method of  the position of the cannon
     * @return the position of the cannon: x, y
     */
    public Pair<Double,Double> getCannonPosition();

    /** Getter method of the state of  the game
     * @return an enum GameStatus (RUNNING, WIN, LOST)
     */
    public GameStatus getGameStatus();
}
