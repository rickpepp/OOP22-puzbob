package it.unibo.puzbob.controller;

import java.util.LinkedList;
import java.util.Queue;

import it.unibo.puzbob.controller.Commands.Command;
import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;

public class GameLoop {

    private long period;
    private Model world;

    private Queue<Command> inputQueue;
    
    public GameLoop(long period, Model world) {
        this.period = period;
        this.world = world;
        this.inputQueue = new LinkedList<>();
    }

    public void mainLoop() {

        while(world.getGameStatus() != GameStatus.LOST || world.getGameStatus() != GameStatus.WIN) {
            long actualTime = System.currentTimeMillis();
            this.processInput();
            this.world.updateTime(actualTime);
            this.render();
            this.waitForNextFrame(actualTime);
        }
    }

    private void waitForNextFrame(long startingTime) {
        long dt = System.currentTimeMillis() - startingTime;

        if (dt < this.period) {
            try {
                Thread.sleep(this.period - dt);
            } catch (Exception e) {}
        }
    }

    private void processInput() {
        this.inputQueue.remove().execute(this.world);
    }

    private void render() {

    }

    public void notifyInput(Command cmd) {
        this.inputQueue.add(cmd);
    }

}
