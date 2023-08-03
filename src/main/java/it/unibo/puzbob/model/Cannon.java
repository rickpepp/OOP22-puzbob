package it.unibo.puzbob.model;

import java.util.ArrayList;

/** This is an interface for a Cannon  */
public interface Cannon {

    
    void changeAngle(int angle);

    int getAngle();

    Ball getCurrentBall(ArrayList<String> colors);

    void shot();
}
