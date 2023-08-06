package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

/**
 * This is a generic interface for an input request. Execute contain the action to do if request.
 */

public interface Command {
    
    public void execute(Model world);

}
