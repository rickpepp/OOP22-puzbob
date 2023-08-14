package it.unibo.puzbob.controller.commands;

/**
 * This is a generic interface for a class that can receive input request.
 */

public interface Controller {
    
    /**
     * This is a generic method to notify an input
     * @param cmd this is the input that contain the stuff to do
     */
    public void notifyInput(Command cmd);

}
