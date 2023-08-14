package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

/**
 * This is a command class that tell to the cannon in the model to shot
 */
public class Shot implements Command {

    /**
     * Default constructor
     */
    public Shot() {}

    /**
     * This method tell the cannon to shot
     */
    public void execute(Model world) {
        world.shot();
    }
    
}
