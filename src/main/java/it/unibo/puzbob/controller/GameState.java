package it.unibo.puzbob.controller;

import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.model.ModelImpl;
import it.unibo.puzbob.model.Score;
import it.unibo.puzbob.model.ScoreImpl;
import it.unibo.puzbob.view.Output;

public class GameState {
    private final long PERIOD = 15;
    private final int MAX_LEVEL = 10;

    private Model model;
    private GameLoop gameloop;
    private Output output;
    private GameStatus gameStatus;
    private Score score;
    //private Input input;

    private int nLevel;

    public GameState(Output output){
        this.nLevel = 1;
        this.output = output;
        this.score = new ScoreImpl(0);
        this.model = new ModelImpl(this.output.getBoardDimension().getX(), this.output.getBoardDimension().getY(), this.output.getSizeBall(), nLevel, this.score);
        this.gameloop = new GameLoop(PERIOD, this.model, this.output);
        startNewLevel();
    }

    /** Method that istantiates the GameLoop class and updates the level */
    public void startNewLevel(){
        this.score.incScore(this.model.getScore());
        this.gameStatus = this.gameloop.mainLoop();

        if(gameStatus == GameStatus.WIN && this.nLevel < MAX_LEVEL){
            this.nLevel++;
            this.model = new ModelImpl(this.output.getBoardDimension().getX(), this.output.getBoardDimension().getY(), this.output.getSizeBall(), this.nLevel, this.score);
            this.gameloop = new GameLoop(PERIOD, this.model, this.output);
            startNewLevel();
        }else{
            System.exit(0);
        }
    }

    /** Method that returns the Gameloop */
    public GameLoop getGameLoop(){
        return this.gameloop;
    }
}
