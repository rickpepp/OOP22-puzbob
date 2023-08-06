package it.unibo.puzbob.controller.commands;

/**
 * This is a generic interface for a class that can receive input request.
 */

public interface Controller {
    
    public void notifyInput(Command cmd);

}
