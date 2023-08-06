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
    
    public GameLoop(long period, Model world, Output view) {
        this.period = period;
        this.world = world;
        this.inputQueue = new LinkedList<>();
        this.formatter = new WorldFormatter(world);
        this.view = view;
    }

    // This is the loop that run until the win or gameover.
    public void mainLoop() {

        while(world.getGameStatus() != GameStatus.LOST && world.getGameStatus() != GameStatus.WIN) {
            long actualTime = System.currentTimeMillis();
            this.processInput();
            this.world.updateTime(actualTime);
            this.render();
            this.waitForNextFrame(actualTime);
        }
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

    // This is part of the controller interface and add a input at the queue
    public void notifyInput(Command cmd) {
        this.inputQueue.add(cmd);
    }

}
