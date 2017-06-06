package com.tetris.score;

/**
 * Created by Pipas_ on 06/06/2017.
 */

public interface DatabaseManager
{
    public boolean connect();
    public boolean addScore(int score, String name);
}
