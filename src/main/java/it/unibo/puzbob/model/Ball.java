package it.unibo.puzbob.model;

/**
 * This is an interface for a simple ball. This is the base for both a static ball and a flying ball. 
 */

public interface Ball {

    public String getColor();

    public int getScore();

    public double getBallSize();

}
