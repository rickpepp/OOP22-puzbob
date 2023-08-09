package it.unibo.puzbob.view;

import java.awt.Dimension;

import it.unibo.puzbob.model.Pair;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class FXMLController {

    private double screenWidth;
    private double screenHeight;
    private ViewController output;

    @FXML
    private AnchorPane pane;

    @FXML
    private Rectangle outRect;

    @FXML
    private Rectangle inRect;

    @FXML
    private Text labelScore;

    @FXML
    private Text valueScore;

    @FXML
    private Line cannon;

    @FXML
    public void initialize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth();
        this.screenHeight = screenSize.getHeight();
        this.output = new ViewController(this, this.screenWidth, this.screenHeight);
        this.output.controllerScale();
        this.output.controllerStartPositioning();
    }

    @FXML
    public void scale(Pair<Double, Double> outRectDimension, Pair<Double, Double> inRectDimension) {
        outRect.setWidth(outRectDimension.getX());
        outRect.setHeight(outRectDimension.getY());
        inRect.setWidth(inRectDimension.getX());
        inRect.setHeight(inRectDimension.getY());
    }

    @FXML
    public void startPosition(Pair<Double, Double> outRectPosition, Pair<Double, Double> inRectPosition, Pair<Double, Double> textPosition, Pair<Double, Double> linePosition) {
        outRect.setLayoutX(outRectPosition.getX());
        outRect.setLayoutY(outRectPosition.getY());
        inRect.setLayoutX(inRectPosition.getX());
        inRect.setLayoutY(inRectPosition.getY());
        labelScore.setLayoutX(textPosition.getX());
        labelScore.setLayoutY(textPosition.getY());
        valueScore.setLayoutX(textPosition.getX());
        valueScore.setLayoutY(textPosition.getY() + 20);
        cannon.setLayoutX(linePosition.getX());
        cannon.setLayoutY(linePosition.getY());
    }

    @FXML
    public void cannonPosition(Pair<Double,Double> startingLine, Pair<Double,Double> finishingLine) {
        cannon.setStartX(startingLine.getX());
        cannon.setStartY(startingLine.getY());
        cannon.setEndX(finishingLine.getX());
        cannon.setEndY(finishingLine.getY());
    }

    @FXML
    public void drawBall(Pair<Double,Double> coordinates, String color, double measureBall) {
        Circle newBall = new Circle(measureBall, Color.web(color));
        newBall.setLayoutX(coordinates.getX());
        newBall.setLayoutY(coordinates.getY());
        Lighting effect = new Lighting();
        effect.setDiffuseConstant(1.3);
        newBall.setEffect(effect);
        pane.getChildren().add(newBall);
    }
    
} 

