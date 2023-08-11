package it.unibo.puzbob.model;

/**
 * This interface model a generic score class.
 */
public interface Score {
    
    /**
     * This is a getter for the score
     * @return the score
     */
    public int getScore();

    /**
     * This increment the actual score
     * @param increment the amount to increment the score
     */
    public void incScore(int increment);

}
