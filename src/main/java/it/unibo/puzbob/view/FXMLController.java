package it.unibo.puzbob.view;

import java.awt.Dimension;

import it.unibo.puzbob.model.Pair;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    private static final double DIFFUSE_VALUE = 1.3;
    private static final double OFFSET_SCORE_FROM_LABEL = 20;
    private static final double FONT_SIZE_MESSAGE = 25;
    private static final String WALL_ID = "wall";
    private static final String ID_SELECTOR = "#";

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

    // The line that show the boundary to  declare game-over
    @FXML 
    private Line gameOver;

    /**
     * Default constructor
     */
    public FXMLController() {}

    /**
     * Initialize the controller and pass it the dimension of the screen
     */
    @FXML
    public void initialize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth();
        this.screenHeight = screenSize.getHeight();
        this.output = new ViewController(this, this.screenWidth, this.screenHeight);
        this.output.controllerScale();
        this.output.controllerStartPositioning();
    }

    /**
     * Set the dimension of the rectangles
     * @param outRectDimension the outer edge of the board
     * @param inRectDimension the inner edge of the board
     */
    @FXML
    public void scale(Pair<Double, Double> outRectDimension, Pair<Double, Double> inRectDimension) {
        outRect.setWidth(outRectDimension.getX());
        outRect.setHeight(outRectDimension.getY());
        inRect.setWidth(inRectDimension.getX());
        inRect.setHeight(inRectDimension.getY());
        gameOver.setEndX(inRectDimension.getX());
    }

    /**
     * Set the position of the object in the screen
     * @param outRectPosition position of the outer edge of the board
     * @param inRectPosition position of the inner edge of the board
     * @param textPosition position of the text score
     * @param linePosition starting position of the cannon (center)
     * @param gameOverLayout is the position of the final line for game-over
     */
    @FXML
    public void startPosition(Pair<Double, Double> outRectPosition, Pair<Double, Double> inRectPosition, Pair<Double, Double> textPosition, Pair<Double, Double> linePosition, Pair<Double,Double> gameOverLayout) {
        outRect.setLayoutX(outRectPosition.getX());
        outRect.setLayoutY(outRectPosition.getY());
        inRect.setLayoutX(inRectPosition.getX());
        inRect.setLayoutY(inRectPosition.getY());
        labelScore.setLayoutX(textPosition.getX());
        labelScore.setLayoutY(textPosition.getY());
        valueScore.setLayoutX(textPosition.getX());
        valueScore.setLayoutY(textPosition.getY() + OFFSET_SCORE_FROM_LABEL);
        cannon.setLayoutX(linePosition.getX());
        cannon.setLayoutY(linePosition.getY());
        gameOver.setLayoutX(gameOverLayout.getX());
        gameOver.setLayoutY(gameOverLayout.getY());
    }

    /**
     * Set the cannon position
     * @param startingLine relative position of the point at the start of the line
     * @param finishingLine relative position of the point at the end of the line
     */
    @FXML
    public void cannonPosition(Pair<Double,Double> startingLine, Pair<Double,Double> finishingLine) {
        cannon.setStartX(startingLine.getX());
        cannon.setStartY(startingLine.getY());
        cannon.setEndX(finishingLine.getX());
        cannon.setEndY(finishingLine.getY());
    }

    /**
     * Draw the ball
     * @param coordinates absolute position of the ball
     * @param color hexadecimal of the color
     * @param measureBall diametre of the color
     * @param id id of the ball in the pane
     */
    @FXML
    public void drawBall(Pair<Double,Double> coordinates, String color, double measureBall, String id) {
        Node ball = pane.lookup(ID_SELECTOR + id);

        if (ball instanceof Circle) {
            ball.setLayoutX(coordinates.getX());
            ball.setLayoutY(coordinates.getY());
            ((Circle)ball).setFill(Color.web(color));
        } else {
            Circle newBall = new Circle(measureBall, Color.web(color));
            newBall.setLayoutX(coordinates.getX());
            newBall.setLayoutY(coordinates.getY());
            newBall.setId(id);
            Lighting effect = new Lighting();
            effect.setDiffuseConstant(DIFFUSE_VALUE);
            newBall.setEffect(effect);
            pane.getChildren().add(newBall);
        }
    }

    /**
     * Remove a ball
     * @param id id of the object to delete
     */
    @FXML
    public void removeBall(String id) {
        Node ball = pane.lookup(ID_SELECTOR + id);
        if (ball instanceof Circle) {
            pane.getChildren().removeAll(ball);
        }
    }

    /**
     * Getter of the output
     * @return the output
     */
    public Output getOutput() {
        return this.output;
    }

    /** Draws the wall 
     * @param heightWall is the position of the height of the wall
     * @param color is the color of the wall in hexadecimal
     * @param startingCoordinates are the coordinates a which the wall is to be draw
    */
    public void createWall(double heightWall, String color, Pair<Double, Double> startingCoordinates){
        Rectangle wall = new Rectangle(startingCoordinates.getX(), startingCoordinates.getY(),inRect.getWidth(), heightWall);
        wall.setId(WALL_ID);
        wall.setFill(Color.web(color));
        pane.getChildren().add(wall);
    }

    /** Destroy Wall */
    public void removeWall(){
        Node wall = pane.lookup(ID_SELECTOR + WALL_ID);
        pane.getChildren().removeAll(wall);
    }

    /** Change the value of the score 
     * @param score is a number to represent the score
    */
    public void changeScore(int score){
        this.valueScore.setText("" + score);
    }    

    /**
     * This is a method that shows a message during the game
     * @param message a String that contain the message
     * @param dimensionRectangle a Pair for the dimension of the rectangle that contain the message
     */
    public void showMessage(String message, Pair<Double, Double> dimensionRectangle) {
        Rectangle rectangleBase = new Rectangle((pane.getWidth() - dimensionRectangle.getX()) / 2, 
            (pane.getHeight() - dimensionRectangle.getY()) / 2, 
            dimensionRectangle.getX(), dimensionRectangle.getY());

        pane.getChildren().add(rectangleBase);

        rectangleBase.toFront();
        Text messageText = new Text(message);

        pane.getChildren().add(messageText);

        messageText.toFront();

        messageText.setFill(Color.WHITE);
        messageText.setFont(new Font(FONT_SIZE_MESSAGE));

        messageText.setLayoutX((pane.getWidth() - messageText.getBoundsInLocal().getWidth()) / 2);
        messageText.setLayoutY((pane.getHeight() - messageText.getBoundsInLocal().getHeight()) / 2);
    }
} 

