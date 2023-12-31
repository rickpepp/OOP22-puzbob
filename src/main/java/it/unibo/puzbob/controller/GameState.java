package it.unibo.puzbob.controller;

import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.model.ModelImpl;
import it.unibo.puzbob.model.Score;
import it.unibo.puzbob.model.ScoreImpl;
import it.unibo.puzbob.view.Output;
import javafx.application.Platform;

/**
 * This class is used as an intermediary between the model and the view and determines which level of the game to start
 */
public class GameState {
    private final long PERIOD = 15;
    private final int MAX_LEVEL = 10;

    private Model model;
    private GameLoop gameloop;
    private Output output;
    private GameStatus gameStatus;
    private Score score;
    private SaveState st;

    private int nLevel;

    /**
     * This is the constructor of the GameState
     * @param output this contain the output that will render the game
     * @param st this is the class that save the state of the game
     * @param score The starting score of the level
     * @param level The starting level
     */
    public GameState(Output output, SaveState st, int score, int level){
        this.nLevel = level;
        this.output = output;
        this.score = new ScoreImpl(score);
        this.model = new ModelImpl(this.output.getBoardDimension().getY(), this.output.getBoardDimension().getX(), this.output.getSizeBall(), nLevel, this.score);
        this.gameloop = new GameLoop(PERIOD, this.model, this.output);
        this.st = st;
    }

    /** 
     * This is the constructor that initializes the level number, score, model and gameloop
     * @param output is the object of type Output and is used to configure the size the other objects
     * @param st is the object of type SaveState and is used to configure the saving the level
     */
    public GameState(Output output, SaveState st){
        this(output, st, 0, 1);
    }

    /** Method that istantiates the GameLoop class and updates the level */
    public void startNewLevel(){
        // Start the main loop and wait for the result
        this.gameStatus = this.gameloop.mainLoop();

        // If the user win and the level aren't finished continue
        if(gameStatus == GameStatus.WIN && this.nLevel < MAX_LEVEL){
            // Save the actual state
            st.saveState(this.score.getScore(), this.nLevel + 1);

            // Inc the level and istantiate the new and gameloop
            this.nLevel++;
            this.model = new ModelImpl(this.output.getBoardDimension().getY(), this.output.getBoardDimension().getX(), this.output.getSizeBall(), this.nLevel, this.score);
            this.gameloop = new GameLoop(PERIOD, this.model, this.output);
            startNewLevel();
        }else{
            // Delete the file save
            st.deleteState();

            // Show the result
            Platform.runLater(() ->
                this.output.showResult(gameStatus)
            );
                
            // Wait to leave time for the user to read the message 
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Exit from the game
            System.exit(0);
        }
    }

    /** Method that returns the Gameloop
     * @return the game loop of the game
     */
    public GameLoop getGameLoop(){
        return this.gameloop;
    }
}
