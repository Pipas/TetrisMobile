package com.tetris.score;

/**
 * The interface Database Manager that handles all the accesses to the realtime database from the game.
 */
public interface DatabaseManager
{
    /**
     *  Connects to the realtime database
     *
     * @return if the connection is established
     */
    public boolean connect();

    /**
     * Adds a score to the database. Database only keeps the top 20 scores
     *
     * @param newHighscore the score to add to the database
     * @return if the score is added to the database
     */
    public boolean addScore(Highscore newHighscore);

    /**
     * Updates the Highscore class with the current highscores
     *
     * @return if the update is successful
     */
    public boolean getScores();
}
