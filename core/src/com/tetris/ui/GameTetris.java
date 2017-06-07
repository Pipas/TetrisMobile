package com.tetris.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tetris.score.DatabaseManager;

/**
 * Created by Alexandre on 04-05-2017.
 */

public class GameTetris extends Game {

    public static int WORLD_WIDTH; // Arbitrary world size (e.g. meters)
    public static int WORLD_HEIGHT;
    // The ammount of world we want to show in our screen
    public static int VIEWPORT_WIDTH;
    public static int VIEWPORT_HEIGHT; // Can be calculated from screen ratio
    // How to transform from pixels to our unit
    public static int PIXEL_TO_METER = 1;
    float ratio;

    private DatabaseManager databaseManager;
    private static GameTetris gt;

    public GameTetris(DatabaseManager dM)
    {
        databaseManager = dM;
    }

    public void create(){
        //Initialize static values
        WORLD_WIDTH = Gdx.graphics.getWidth();
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
        ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

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
