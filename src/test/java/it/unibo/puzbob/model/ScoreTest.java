package it.unibo.puzbob.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ScoreTest {

    Score score = new ScoreImpl(0);
    
    @Test 
    void scoreTest() {
        assertEquals(0, score.getScore(), "Score need to be 0");
        score.incScore(50);
        assertEquals(50, score.getScore(), "Score need to be 50");
        score.incScore(-20);
        assertEquals(50, score.getScore(), "Score need to be 50, because incScore can't be negative");
    }

}
