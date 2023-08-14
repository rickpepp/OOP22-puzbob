package it.unibo.puzbob.controller;

/**
 * This class manage the save in the file system
 */
public interface SaveState {
    
    /**
     * Check if there is a save in the file System
     * @return a boolean value
     */
    public boolean thereIsState();

    /**
     * Save the state in the file system
     * @param score the score to save
     * @param level the level to save
     */
    public void saveState(int score, int level);

    /**
     * Get the score from the save
     * @return number of the score
     */
    public int getScore();

    /**
     * Get the level from the save
     * @return number of the level
     */
    public int getLevel();

    /**
     * Delete the save
     */
    public void deleteState();

}
