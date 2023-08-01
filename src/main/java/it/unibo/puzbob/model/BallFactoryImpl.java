package it.unibo.puzbob.model;

import java.util.Map;

/**
 * The Ball Factory create the new ball. This class need a map of Key: ColorName and Value: ScoreValue.
 * The first method create a BallImpl (Static ball whitout position), the other a FlyingBallImpl.
 */

public class BallFactoryImpl implements BallFactory {

    private Map<String, Integer> colorMap;
    private double size;

    public BallFactoryImpl(Map<String, Integer> colorMap, double size) {
        this.colorMap = colorMap;
        this.size = size;
    }

    public Ball createStaticBall(String color) {
        return new BallImpl(color, this.colorMap.get(color), this.size);
    }

    public Ball createFlyingBall(String color, Pair<Double, Double> position) {
        return new FlyingBallImpl(color, this.colorMap.get(color), size, position);
    }
    
}
