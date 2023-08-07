package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

public class MoveLeft implements Command {

    public final static int DEFAULT_ANGLE = 5;

    public void execute(Model world) {
        world.changeCannonAngle(- DEFAULT_ANGLE);
    }
    
}
