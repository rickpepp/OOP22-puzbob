package it.unibo.puzbob.model;

import java.util.List;
import java.util.Map;

public interface Level {

    public Ball[][] getStartBalls(Map<String, List<Pair<Integer, Integer>>> ballsPosition);

}
