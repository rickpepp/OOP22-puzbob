package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

/**
 * This is a Command that execute a move left input. The effect is to move the cannon on the left
 */
public class MoveLeft implements Command {

    private final static int DEFAULT_ANGLE = 5;

    /**
     * This is a default constructor for MoveLeft
     */
    public MoveLeft() {}

    /**
     * This method move the cannon to the left.
     */
    public void execute(Model world) {
        world.changeCannonAngle(- DEFAULT_ANGLE);
    }
    
}
