package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

public class LevelImpl implements Level {

    private BallFactory ballFactory;
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private Ball[][] starterBalls;

    public LevelImpl(BallFactory ballFactory, Pair<Integer, Integer> dimension) {
        this.ballFactory = ballFactory;
        this.starterBalls = new Ball[dimension.getX()][dimension.getY()];
    }

    public Ball[][] getStartBalls(Map<String, List<Pair<Integer, Integer>>> ballsPosition) {
        for (String color: ballsPosition.keySet()) {
            for (Pair<Integer, Integer> ball: ballsPosition.get(color)) {
                this.starterBalls[ball.getX()][ball.getY()] = this.ballFactory.createStaticBall(color);
            }
        }
        return this.starterBalls;
    }
    
}
