package it.unibo.puzbob.view;

import java.awt.Dimension;

import it.unibo.puzbob.model.Pair;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * This class draw object in the screen whit FXML.
 */

public class FXMLController {

    private double screenWidth;
    private double screenHeight;
    private ViewController output;

    // Main pane
    @FXML
    private AnchorPane pane;

    // Outer edge of the rectangle
    @FXML
    private Rectangle outRect;

    // Inner edge of the rectangle
    @FXML
    private Rectangle inRect;

    // The text "score"
    @FXML
    private Text labelScore;

    // The value of the score
    @FXML
    private Text valueScore;

    // The line that show cannon
    @FXML
    private Line cannon;

    // Initialize the controller and pass it the dimension of the screen
    @FXML
    public void initialize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth();
        this.screenHeight = screenSize.getHeight();
        this.output = new ViewController(this, this.screenWidth, this.screenHeight);
        this.output.controllerScale();
        this.output.controllerStartPositioning();
    }

    // Set the dimension of the rectangle
    @FXML
    public void scale(Pair<Double, Double> outRectDimension, Pair<Double, Double> inRectDimension) {
        outRect.setWidth(outRectDimension.getX());
        outRect.setHeight(outRectDimension.getY());
        inRect.setWidth(inRectDimension.getX());
        inRect.setHeight(inRectDimension.getY());
    }

    // Set the position of the object in the screen
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

    // Set the cannon position
    @FXML
    public void cannonPosition(Pair<Double,Double> startingLine, Pair<Double,Double> finishingLine) {
        cannon.setStartX(startingLine.getX());
        cannon.setStartY(startingLine.getY());
        cannon.setEndX(finishingLine.getX());
        cannon.setEndY(finishingLine.getY());
    }

    // Draw the ball
    @FXML
    public void drawBall(Pair<Double,Double> coordinates, String color, double measureBall, String id) {
        Node ball = pane.lookup("#" + id);

        if (ball instanceof Circle) {
            ball.setLayoutX(coordinates.getX());
            ball.setLayoutY(coordinates.getY());
        } else {
            Circle newBall = new Circle(measureBall, Color.web(color));
            newBall.setLayoutX(coordinates.getX());
            newBall.setLayoutY(coordinates.getY());
            newBall.setId(id);
            Lighting effect = new Lighting();
            effect.setDiffuseConstant(1.3);
            newBall.setEffect(effect);
            pane.getChildren().add(newBall);
        }
    }

    // Remove a ball
    @FXML
    public void removeBall(String id) {
        Node ball = pane.lookup("#" + id);
        if (ball instanceof Circle) {
            pane.getChildren().removeAll(ball);
        }
    }

    // Get the Output object
    public Output getOutput() {
        return this.output;
    }

    // Add input methods
    
} 

