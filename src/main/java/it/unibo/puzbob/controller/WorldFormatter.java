package it.unibo.puzbob.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.puzbob.model.Ball;
import it.unibo.puzbob.model.Model;

/**
 * This class format in json the status of the world to pass at the view. This is done to separate the model and the view.
 */

public class WorldFormatter {

    // The model
    private Model world;
    // The main JSONObject
    private JSONObject jsonWorld;
    // Coordinates and color of the static balls
    private JSONArray xArray;
    private JSONArray yArray;
    private JSONArray colorArray;

    public WorldFormatter(Model world) {
        this.world = world;
        this.jsonWorld = new JSONObject();
        this.xArray = new JSONArray();
        this.yArray = new JSONArray();
        this.colorArray = new JSONArray();
    }

    public JSONObject getJSONWorld() {
        // Clear the json object
        this.xArray.clear();
        this.yArray.clear();
        this.colorArray.clear();
        this.jsonWorld.clear();

        // Put the main characteristics
        this.jsonWorld.put("score", this.world.getScore());
        this.buildMatrixArrays();
        this.jsonWorld.put("xIndexesStaticBalls",this.xArray);
        this.jsonWorld.put("yIndexesStaticBalls",this.yArray);
        this.jsonWorld.put("colorsStaticBalls",this.colorArray);
        this.jsonWorld.put("wallPosition",this.world.getWallHeigth());
        this.jsonWorld.put("cannonAngle",this.world.getCannonAngle());
        
        // Only if a Flying ball is present
        if (this.world.getFlyingBall() != null) {
            this.jsonWorld.put("xIndexFlyingBall",this.world.getFlyingBall().getPosition().getX());
            this.jsonWorld.put("yIndexFlyingBall",this.world.getFlyingBall().getPosition().getY());
            this.jsonWorld.put("colorFlyingBall",this.world.getFlyingBall().getColor());
        }

        return this.jsonWorld;
    }

    // This method put in the arrays the static balls
    private void buildMatrixArrays() {

        Ball[][] matrixBall = this.world.getMatrixBall();

        for (int i = 0; i < matrixBall.length; i++) {
            for (int j = 0; j < matrixBall[i].length; j++) {
                try {
                    this.colorArray.put(matrixBall[i][j].getColor());
                    this.xArray.put(i);
                    this.yArray.put(j);
                    
                } catch (Exception e) {}
                
            }
        }
    }
    
}
