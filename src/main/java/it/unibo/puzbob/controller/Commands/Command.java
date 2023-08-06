package it.unibo.puzbob.controller.Commands;

import it.unibo.puzbob.model.Model;

public interface Command {
    
    public void execute(Model world);

}
