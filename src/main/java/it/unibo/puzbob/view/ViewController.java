package it.unibo.puzbob.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import it.unibo.puzbob.model.JSONParserImpl;
import it.unibo.puzbob.model.JSONReaderImpl;
import it.unibo.puzbob.model.Pair;

/**
 * This class tell to fxmlController what to draw and where.
 */

public class ViewController implements Output {

    private final double N_BALL_FIRST_ROW = 8;
    private final int STARTING_CANNON_ANGLE = 90;
    private final double STARTING_POSITION_TEXT = 30;
    private final double BOARD_EDGE_DIMENSION = 25;
    private final int FLAT_ANGLE = 180;

    // Proportion as compared to stage dimension
    private final double OUTER_BOARD_PROPORTION_X = 0.7;
    private final double OUTER_BOARD_PROPORTION_Y = 0.87;

    // Proportion as compared to inner edge board dimension
    private final double DISTANCE_CANNON_PROPORTION = 0.1;
    private final double LENGTH_CANNON_PROPORTION = 0.17;

    private final String ID_SEPARATOR = "-";
    private final String ID_FLYING_BALL = "flyingBall";

    private final String COLORS_VIEW_PATH = "view" + View.FILE_SEPARATOR + "colorsView.json";

    private FXMLController fxmlcontroller;
    private double widthStage;
    private double heightStage;

    private double inRectWidth;
    private double inRectHeight;

    private Pair<Double,Double> cannonOffset;
    private double distanceCannon;
    private double lengthCannon;

    private double ballRadius;

    private double wallHeight = 0;

    private Pair<Double, Double> flyingBallOffset;

    private JSONParserImpl parser;
    private JSONReaderImpl reader;

    private Map<String, String> colorsCodes;

    // Those list are for check if some ball need to be removed
    private List<String> lastCycleList;
    private List<String> newCycleList;


    // Constructor
    public ViewController(FXMLController fxmlcontroller, double width, double height) {
        this.fxmlcontroller = fxmlcontroller;

        // Save the screen dimension
        this.widthStage = width / View.WINDOW_PROPORTION;
        this.heightStage = height/ View.WINDOW_PROPORTION;

        // Initialize lists
        this.lastCycleList = new LinkedList<String>();
        this.newCycleList = new LinkedList<String>();

        this.reader = new JSONReaderImpl();
        this.parser = new JSONParserImpl();
        JSONObject jsonObject = this.reader.readJSONFromFile(COLORS_VIEW_PATH);
        this.colorsCodes = this.parser.parserColorsView(jsonObject);
    }

    // This is the method that display the actual state of the world
    @Override
    public void displayGame(JSONObject world) {
        // Invertire le due liste

        // Aggiornare score (campo json "score") (da fare)

        // I campi "xIndexesStaticBalls", "yIndexesStaticBalls", "colorsStaticBalls" sono un elenco
        // delle palline statiche, quindi per ognuna di queste richiamare drawStaticBall

        // Finite di aggiungere eseguire un foreach di lastCycleList ed eliminare tutte le palline elencate con l'id
        // (l'id è 'row + "-" + column')

        // Posizionare il muro (campo json "wallPosition") (da fare)

        // Posizionare il cannone e richiamare controllerCannon (campo json "cannonAngle")

        // Disegnare con drawFlyingBall(), solo se presente, la pallina volante (campi json "xIndexFlyingBall", 
        // "yIndexFlyingBall", "colorFlyingBall")
    }

    // Scale objects in the view in base at the
    public void controllerScale() {

        // Scale rectangles dimension
        Pair<Double,Double> outRectDimension = new Pair<Double,Double>(this.heightStage * OUTER_BOARD_PROPORTION_X, 
            this.heightStage * OUTER_BOARD_PROPORTION_Y);

        Pair<Double,Double> inRectDimension = new Pair<Double,Double>(outRectDimension.getX() - BOARD_EDGE_DIMENSION,
            outRectDimension.getY() - BOARD_EDGE_DIMENSION);

        this.inRectHeight = inRectDimension.getY();
        this.inRectWidth = inRectDimension.getX();

        // Cannon proportion
        this.distanceCannon = this.inRectWidth * DISTANCE_CANNON_PROPORTION;
        this.lengthCannon = this.inRectWidth * LENGTH_CANNON_PROPORTION;
        this.cannonOffset = new Pair<Double,Double>((this.widthStage / 2), 
            ((this.heightStage - this.inRectHeight) / 2) + this.inRectHeight);

        // Tell to fxmlcontroller to scale
        this.fxmlcontroller.scale(outRectDimension, inRectDimension);
    }

    // Set the initial position of the objects in the view
    public void controllerStartPositioning() {

        // Outer edge of the board
        Pair<Double,Double> outRectPosition = new Pair<Double,Double>((this.widthStage - this.inRectWidth - BOARD_EDGE_DIMENSION) / 2, 
            (this.heightStage - this.inRectHeight - BOARD_EDGE_DIMENSION) / 2);

        // Inner edge of the board
        Pair<Double,Double> inRectPosition = new Pair<Double,Double>((this.widthStage - this.inRectWidth) / 2, 
            (this.heightStage - this.inRectHeight) / 2);

        this.flyingBallOffset = new Pair<Double,Double>(inRectPosition.getX(), inRectPosition.getY() + this.inRectHeight);

        // Text Position
        Pair<Double,Double> textPosition = new Pair<Double,Double>(STARTING_POSITION_TEXT , 
            STARTING_POSITION_TEXT);
        
        // Tell to move those in the view
        this.fxmlcontroller.startPosition(outRectPosition, inRectPosition, textPosition, this.cannonOffset);

        this.controllerCannon(STARTING_CANNON_ANGLE);
        this.ballRadius = this.inRectWidth / (N_BALL_FIRST_ROW * 2);
    }

    // Draw cannon from the angle in input (degree)
    private void controllerCannon(int angle) {
        Pair<Double, Double> startLine = new Pair<Double,Double>((this.distanceCannon * Math.cos(Math.toRadians(FLAT_ANGLE - angle))),
            -(this.distanceCannon * Math.sin(Math.toRadians(FLAT_ANGLE - angle))));
        Pair<Double, Double> finishLine = new Pair<Double,Double>(((this.distanceCannon + this.lengthCannon) * Math.cos(Math.toRadians(FLAT_ANGLE - angle))),
            -((this.distanceCannon + this.lengthCannon) * Math.sin(Math.toRadians(FLAT_ANGLE - angle))));

        this.fxmlcontroller.cannonPosition(startLine, finishLine);
    }

    // Draw the static ball
    private void drawStaticBall(int row, int column, String color) {

        double x;
        // Calc the y position
        double y = ((this.heightStage - this.inRectHeight) / 2) + this.ballRadius + (this.ballRadius * 2 * row) + this.wallHeight;

        // Calc the x position if the row is odd or even
        if (row % 2 == 0) {
            x = ((this.widthStage - this.inRectWidth) / 2) + this.ballRadius + (this.ballRadius * 2 * column);
        } else {
            x = ((this.widthStage - this.inRectWidth) / 2) + (this.ballRadius * 2) + (this.ballRadius * 2 * column);
        }

        String id = Integer.toString(row) + ID_SEPARATOR + Integer.toString(column);

        // Draw the ball
        try {
            this.fxmlcontroller.drawBall(new Pair<Double,Double>(x, y), this.colorsCodes.get(color), this.ballRadius, id);
        } catch (NullPointerException e) {
            throw new NullPointerException("The color selected is not present in ColorsView.json");
        }
        

        // Add to the new list, and remove from old list (so at the end of the cycle remain all the ball that need to be deleted)
        this.newCycleList.add(id);
        this.lastCycleList.remove(id);
    }

    // Draw the flying ball
    private void drawFlyingBall(Pair<Double, Double> coordinates, String color) {
        try {
            this.fxmlcontroller.drawBall(new Pair<Double,Double>(this.flyingBallOffset.getX() + coordinates.getX(), 
                this.flyingBallOffset.getY() - coordinates.getY()), 
                this.colorsCodes.get(color), ballRadius, ID_FLYING_BALL);
        } catch (NullPointerException e) {
            throw new NullPointerException("The color selected is not present in ColorsView.json");
        }   
    }

    // Remove balls
    private void removeBall(String id) {
        this.fxmlcontroller.removeBall(id);
        this.lastCycleList.remove(id);
    }

    // Aggiungere metodo sia qui che in FXMLController per Wall e score

    /** Method that return the dimension of the Board */
    public Pair<Double,Double> getBoardDimension(){
        return new Pair<Double,Double>(this.inRectHeight, this.inRectWidth);
    }

    /** Method that return the size of the Ball */
    public double getSizeBall(){
        return this.ballRadius * 2;
    }
}
