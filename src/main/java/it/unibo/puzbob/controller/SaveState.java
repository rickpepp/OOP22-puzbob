package it.unibo.puzbob.controller;

public interface SaveState {
    
    public boolean thereIsState();

    public void saveState(int score, int level);

    public int getScore();

    public int getLevel();

}
