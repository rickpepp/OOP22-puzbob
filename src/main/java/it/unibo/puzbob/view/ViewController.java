package it.unibo.puzbob.view;

import org.json.JSONObject;

import it.unibo.puzbob.model.Pair;

public class ViewController implements Output {

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


    public ViewController(FXMLController fxmlcontroller, double width, double height) {
        this.fxmlcontroller = fxmlcontroller;
        this.widthStage = width / 1.5;
        this.heightStage = height/ 1.5;
    }

    @Override
    public void displayGame(JSONObject world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayGame'");
    }

    public void controllerScale() {
        Pair<Double,Double> outRectDimension = new Pair<Double,Double>(this.heightStage / 1.4, this.heightStage / 1.15);
        Pair<Double,Double> inRectDimension = new Pair<Double,Double>(outRectDimension.getX() - 25, outRectDimension.getY() - 25);
        this.inRectHeight = inRectDimension.getY();
        this.inRectWidth = inRectDimension.getX();
        this.distanceCannon = this.inRectWidth / 6;
        this.lengthCannon = this.inRectWidth / 6;
        this.cannonOffset = new Pair<Double,Double>((this.widthStage / 2), 
            ((this.heightStage - this.inRectHeight) / 2) + this.inRectHeight);
        this.fxmlcontroller.scale(outRectDimension, inRectDimension);
    }

    public void controllerStartPositioning() {
        Pair<Double,Double> outRectPosition = new Pair<Double,Double>((this.widthStage - this.inRectWidth - 25) / 2, 
            (this.heightStage - this.inRectHeight - 25) / 2);

        Pair<Double,Double> inRectPosition = new Pair<Double,Double>((this.widthStage - this.inRectWidth) / 2, 
            (this.heightStage - this.inRectHeight) / 2);

        Pair<Double,Double> textPosition = new Pair<Double,Double>(30.0 , 
            30.0);
        
        this.fxmlcontroller.startPosition(outRectPosition, inRectPosition, textPosition, this.cannonOffset);
        this.controllerCannon(90);
        this.ballRadius = this.inRectWidth / 16;
        this.drawStaticBall(1, 0, "#1fff31");
        this.drawStaticBall(0, 0, "#1fff31");
        this.drawStaticBall(1, 1, "#1fff31");
        this.drawStaticBall(0, 1, "#1fff31");
    }

    private void controllerCannon(int angle) {
        Pair<Double, Double> startLine = new Pair<Double,Double>((this.distanceCannon * Math.cos(Math.toRadians( 180 - angle))),
            -(this.distanceCannon * Math.sin(Math.toRadians(180 - angle))));
        Pair<Double, Double> finishLine = new Pair<Double,Double>(((this.distanceCannon + this.lengthCannon) * Math.cos(Math.toRadians( 180 - angle))),
            -((this.distanceCannon + this.lengthCannon) * Math.sin(Math.toRadians(180 - angle))));

        this.fxmlcontroller.cannonPosition(startLine, finishLine);
    }

    private void drawStaticBall(int row, int column, String color) {
        double x;
        double y = ((this.heightStage - this.inRectHeight) / 2) + this.ballRadius + (this.ballRadius * 2 * row) + this.wallHeight;
        if (row % 2 == 0) {
            x = ((this.widthStage - this.inRectWidth) / 2) + this.ballRadius + (this.ballRadius * 2 * column);
        } else {
            x = ((this.widthStage - this.inRectWidth) / 2) + (this.ballRadius * 2) + (this.ballRadius * 2 * column);
        }
        this.fxmlcontroller.drawBall(new Pair<Double,Double>(x, y), color, this.ballRadius);
    }


    
}
