package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

public interface Command {
    
    public void execute(Model world);

}
