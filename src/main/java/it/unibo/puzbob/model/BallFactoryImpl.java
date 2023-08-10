package it.unibo.puzbob.model;

import java.util.Map;

/**
 * The Ball Factory create the new ball. This class need a map of Key: ColorName and Value: ScoreValue.
 * The first method create a BallImpl (Static ball whitout position), the other a FlyingBallImpl.
 */

public class BallFactoryImpl implements BallFactory {

    private Map<String, Integer> colorMap;
    private double size;

    /**
     * This is the constructor of the ball factory
     * @param colorMap a map with Key: 'Name of the color' Value:'Score of the color'
     * @param size the diameter of the ball
     */
    public BallFactoryImpl(Map<String, Integer> colorMap, double size) {
        this.colorMap = colorMap;
        this.size = size;
    }

    /**
     * This create a Ball without position. Throw an exception if the color not exist in the map.
     */
    public Ball createStaticBall(String color) {
        if (this.colorMap.get(color) == null) {
            throw new IllegalArgumentException("Specified Color doesn't exist");
        } 
        return new BallImpl(color, this.colorMap.get(color), this.size);
    }

    /**
     * This create a Ball with position. Throw an exception if the color not exist in the map.
     */
    public Ball createFlyingBall(String color, Pair<Double, Double> position) {
        if (this.colorMap.get(color) == null) {
            throw new IllegalArgumentException("Specified Color doesn't exist");
        } 
        return new FlyingBallImpl(color, this.colorMap.get(color), size, position);
    }

    /**
     * This is a getter for the diameter of the ball.
     */
    public Double getBallDimension() {
        return this.size;
    }
    
}
