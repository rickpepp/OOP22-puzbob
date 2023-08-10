package it.unibo.puzbob.controller;

import it.unibo.puzbob.controller.commands.Controller;
import it.unibo.puzbob.model.Model;
import it.unibo.puzbob.model.ModelImpl;
import it.unibo.puzbob.view.FXMLController;
import it.unibo.puzbob.view.Output;
import it.unibo.puzbob.view.View;
import it.unibo.puzbob.view.ViewController;
import javafx.util.converter.PercentageStringConverter;

public class GameState {
    private final long PERIOD = 15;

    private Model model;
    private GameLoop gameloop;
    private Output output;
    //private Input input;

    private int nLevel;

    public GameState(int nLevel, Output output){
        this.nLevel = nLevel;
        this.output = output;
        this.model = new ModelImpl(this.output.getBoardDimension().getX(), this.output.getBoardDimension().getY(), this.output.getSizeBall(), nLevel, this.output.getCannonPosition());
        this.gameloop = new GameLoop(PERIOD, this.model, this.output);
    }
}
