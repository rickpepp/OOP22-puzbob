package it.unibo.puzbob.model;

public class ScoreImpl implements Score {

    private int actualScore;

    public ScoreImpl(int baseScore) {
        this.actualScore = baseScore;
    }

    public int getScore() {
        return this.actualScore;
    }

    public void incScore(int increment) {
        if (increment >= 0) {
            this.actualScore += increment;
        }
    }
    
}
