package com.tetris.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tetris.score.DatabaseManager;

/**
 * The Tetirs Game main.
 */
public class GameTetris extends Game
{

    /**
     * The constant WORLD_WIDTH.
     */
    public static int WORLD_WIDTH;
    /**
     * The constant WORLD_HEIGHT.
     */
    public static int WORLD_HEIGHT;
    /**
     * The constant VIEWPORT_WIDTH.
     */
    public static int VIEWPORT_WIDTH;
    /**
     * The constant VIEWPORT_HEIGHT.
     */
    public static int VIEWPORT_HEIGHT;
    /**
     * The Ratio.
     */
    float ratio;

    private DatabaseManager databaseManager;
    private static GameTetris gt;

    /**
     * Instantiates a new Game of Tetris.
     *
     * @param dM the Database Manager
     */
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

    /**
     * Returns the current game class.
     *
     * @return the tetris game
     */
    public static GameTetris get()
    {
        return gt;
    }

    /**
     * Returns the database manager.
     *
     * @return the database manager
     */
    public DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }

}
