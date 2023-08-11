package it.unibo.puzbob.model;

/**
 * This is a class that implement the interface Score
 */
public class ScoreImpl implements Score {

    private int actualScore;

    /**
     * This is the constructor of score
     * @param baseScore the initial score
     */
    public ScoreImpl(int baseScore) {
        this.actualScore = baseScore;
    }

    /**
     * This is a getter for the score
     */
    public int getScore() {
        return this.actualScore;
    }

    /**
     * This increment the actual score
     */
    public void incScore(int increment) {
        if (increment >= 0) {
            this.actualScore += increment;
        }
    }

    /**
     * To string for score impl
     */
    public String toString() {
        return "Actual Score: " + this.actualScore;
    }
    
}
