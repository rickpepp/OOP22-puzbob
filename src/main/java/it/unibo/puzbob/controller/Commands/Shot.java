package it.unibo.puzbob.controller.commands;

import it.unibo.puzbob.model.Model;

public class Shot implements Command {

    public void execute(Model world) {
        world.shot();
    }
    
}
