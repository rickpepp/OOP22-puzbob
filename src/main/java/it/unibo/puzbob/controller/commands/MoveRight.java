package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

/**
 * This is a Command that execute a move right input. The effect is to move the cannon on the right
 */
public class MoveRight implements Command {

    private final static int DEFAULT_ANGLE = 3;

    /**
     * This is a default constructor for MoveRight
     */
    public MoveRight() {}

    /**
     * This method move the cannon to the right
     */
    public void execute(Model world) {
        world.changeCannonAngle(DEFAULT_ANGLE);
    }
    
}
