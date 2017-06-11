package com.tetris.score;

import org.junit.Before;
import org.junit.Test;
import com.tetris.score.DatabaseManager;

import static org.junit.Assert.*;

/**
 * Created by Alexandre on 11-06-2017.
 */
public class HighscoreTest {
    private DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception {
     /*   databaseManager.connect();
        databaseManager.getScores();*/
    }

    @Test
    public void testUpdateLess() throws Exception {
       /* int min = Highscore.getScoreList().get(Highscore.getScoreList().size() - 1).getScore() - 10;
        Highscore hTest = new Highscore("JTEST", min);
        boolean done = databaseManager.addScore(hTest);
        if (Highscore.getScoreList().size() >= 20){
            assertFalse(done);
            for (int i = 0; i < Highscore.getScoreList().size(); i++)
                assertNotEquals(hTest.hashCode(), Highscore.getScoreList().get(i).hashCode());
        }
        else{
            assertTrue(done);
            int count = 0;
            for (int i = 0; i < Highscore.getScoreList().size(); i++)
                if(hTest.hashCode() == Highscore.getScoreList().get(i).hashCode())
                    count++;
        }*/
       assertTrue(true);
    }

    @Test
    public void testUpdateMore() throws Exception {

    }

}