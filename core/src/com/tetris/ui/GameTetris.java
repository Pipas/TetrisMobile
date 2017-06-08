package com.tetris.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tetris.score.DatabaseManager;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameTetris extends Game
{

    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;
    public static int VIEWPORT_WIDTH;
    public static int VIEWPORT_HEIGHT;
    float ratio;

    private DatabaseManager databaseManager;
    private static GameTetris gt;

    public GameTetris(DatabaseManager dM)
    {
        databaseManager = dM;
    }

    public void create()
    {
        WORLD_WIDTH = Gdx.graphics.getWidth();
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
        ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

        Gdx.input.setCatchBackKey(true);

        databaseManager.connect();
        databaseManager.getScores();

        gt = this;

        this.setScreen(new MenuScreen());
    }

    public static GameTetris get()
    {
        return gt;
    }

    public DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }

}
