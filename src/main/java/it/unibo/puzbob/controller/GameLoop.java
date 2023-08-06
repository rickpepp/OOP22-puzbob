package it.unibo.puzbob.controller;

import java.util.LinkedList;
import java.util.Queue;

import it.unibo.puzbob.controller.commands.Command;
import it.unibo.puzbob.controller.commands.Controller;
import it.unibo.puzbob.model.GameStatus;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.view.Output;

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

    public void mainLoop() {

        while(world.getGameStatus() != GameStatus.LOST && world.getGameStatus() != GameStatus.WIN) {
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
        Command cmd = this.inputQueue.poll();

        if (cmd != null) {
            cmd.execute(this.world);
        }
    }

    private void render() {
        this.view.displayGame(this.formatter.getJSONWorld());
    }

    public void notifyInput(Command cmd) {
        this.inputQueue.add(cmd);
    }

}
