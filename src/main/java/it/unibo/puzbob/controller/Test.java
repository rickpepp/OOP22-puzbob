package it.unibo.puzbob.controller;

import it.unibo.puzbob.controller.commands.MoveLeft;
import it.unibo.puzbob.controller.commands.MoveRight;
import it.unibo.puzbob.controller.commands.Shot;

public class Test {
    public static void main(String[] args) {
        WorldTest world = new WorldTest();
        GameLoop level = new GameLoop(20, world);

        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveRight());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        level.notifyInput(new MoveLeft());
        
        level.notifyInput(new Shot());
        
        level.mainLoop();
    }
}
