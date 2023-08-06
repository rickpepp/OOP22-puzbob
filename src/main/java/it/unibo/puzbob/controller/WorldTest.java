package it.unibo.puzbob.controller;

import it.unibo.puzbob.model.Ball;
import it.unibo.puzbob.model.BallImpl;
import it.unibo.puzbob.model.FlyingBallImpl;
import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.model.Pair;

public class WorldTest implements Model {

    private int angle = 0;
    private boolean shot = false;
    private int cycle = 0;

    public void changeCannonAngle(int angle) {
        this.angle += angle;

        if (this.angle < 0) {
            this.angle = 0;
        } else if (this.angle > 180) {
            this.angle = 180;
        }
    }

    public void shot() {
        this.shot = true;
    }

    public void updateTime(long currentTime) {

        this.cycle += 1;
        return;
    }

    public int getScore() {
        return 350;
    }

    public Ball[][] getMatrixBall() {
        Ball[][] matrixToReturn = new Ball[10][5];
        matrixToReturn[5][3] = new BallImpl("RED", 50, 50);
        matrixToReturn[1][2] = new BallImpl("BLUE", 50, 50);
        matrixToReturn[8][4] = new BallImpl("YELLOW", 50, 50);
        matrixToReturn[5][2] = new BallImpl("RED", 50, 50);
        return matrixToReturn;
    }

    public FlyingBallImpl getFlyingBall() {
        if (this.shot == true) {
            return new FlyingBallImpl("BLACK", cycle, angle, new Pair<Double,Double>(50.5, 12.0));
        } else {
            return null;
        }
    }

    public int getCannonAngle() {
        return this.angle;
    }

    public double getWallHeigth() {
        return 6;
    }

    public GameStatus getGameStatus() {
        if (this.cycle < 200) {
            return GameStatus.RUNNING;
        } else {
            return GameStatus.LOST;
        }
    }

}
