package it.unibo.puzbob.controller;

import java.util.LinkedList;
import java.util.Queue;

import it.unibo.puzbob.controller.commands.Command;
import it.unibo.puzbob.controller.commands.Controller;
import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.view.Output;

/**
 * This class coordinate the elements of the game. Link the model with the view.
 * Can be specified a period, higher is the period, more fluid is the game but 
 * require more resource.
 */

public class GameLoop implements Controller {

    private long period;
    private Model world;
    private WorldFormatter formatter;
    private Queue<Command> inputQueue;
    private Output view;
    
    /**
     * This is the constructor for gameloop. The constructor will not start the mainloop
     * @param period a time to wait to redo the loop 
     * @param world  the model to use
     * @param view the view use
     */
    public GameLoop(long period, Model world, Output view) {
        this.period = period;
        this.world = world;
        this.inputQueue = new LinkedList<>();
        this.formatter = new WorldFormatter(world);
        this.view = view;
    }

    /**
     * This is the loop that run until the win or gameover.
     * @return GameStatus (WIN, LOST)
     */
    public GameStatus mainLoop() {

        while(world.getGameStatus() != GameStatus.LOST && world.getGameStatus() != GameStatus.WIN) {
            long actualTime = System.currentTimeMillis();
            this.processInput();
            this.world.updateTime(actualTime);
            this.render();
            this.waitForNextFrame(actualTime);
        }
        return world.getGameStatus();
    }

    // Wait until the period pass.
    private void waitForNextFrame(long startingTime) {
        long dt = System.currentTimeMillis() - startingTime;

        if (dt < this.period) {
            try {
                Thread.sleep(this.period - dt);
            } catch (Exception e) {}
        }
    }

    // Process the input in the queue.
    private void processInput() {
        Command cmd = this.inputQueue.poll();

        if (cmd != null) {
            cmd.execute(this.world);
        }
    }

    // Pass at the view the status of the game at the moment
    private void render() {
        this.view.displayGame(this.formatter.getJSONWorld());
    }

    /**
     * This is the method of the interface Controller. Here the view can notify the input
     */
    public void notifyInput(Command cmd) {
        this.inputQueue.add(cmd);
    }

}
