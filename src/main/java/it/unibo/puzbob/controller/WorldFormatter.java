package it.unibo.puzbob.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.puzbob.model.Ball;
import it.unibo.puzbob.model.Model;

public class WorldFormatter {

    private Model world;
    private JSONObject jsonWorld;
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
        this.xArray.clear();
        this.yArray.clear();
        this.colorArray.clear();
        this.jsonWorld.clear();

        this.jsonWorld.put("score", this.world.getScore());
        this.buildMatrixArrays();
        this.jsonWorld.put("xIndexesStaticBalls",this.xArray);
        this.jsonWorld.put("yIndexesStaticBalls",this.yArray);
        this.jsonWorld.put("colorsStaticBalls",this.colorArray);
        this.jsonWorld.put("wallPosition",this.world.getWallHeigth());
        
        if (this.world.getFlyingBall() != null) {
            this.jsonWorld.put("xIndexFlyingBall",this.world.getFlyingBall().getPosition().getX());
            this.jsonWorld.put("yIndexFlyingBall",this.world.getFlyingBall().getPosition().getY());
            this.jsonWorld.put("colorFlyingBall",this.world.getFlyingBall().getColor());
        }

        this.jsonWorld.put("cannonAngle",this.world.getCannonAngle());

        return this.jsonWorld;
    }

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
