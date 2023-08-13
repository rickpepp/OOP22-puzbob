package it.unibo.puzbob.controller;

public class SaveStateImpl implements SaveState {

    @Override
    public boolean thereIsState() {
        return true;
    }

    @Override
    public void saveState(int score, int level) {
        System.out.println("Score: " + score + " Level: " + level);
    }

    @Override
    public int getScore() {
        return 2000;
    }

    @Override
    public int getLevel() {
        return 9;
    }
    
}
