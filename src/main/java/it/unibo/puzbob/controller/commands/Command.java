package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

/**
 * This is a generic interface for an input request. Execute contain the action to do if request.
 */
public interface Command {
    
    /**
     * This is a generic method that tell what to do at the model in input.
     * @param world the actual model of the game
     */
    public void execute(Model world);

}
