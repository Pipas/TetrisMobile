package com.tetris.score;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.M;

/**
 * Class that represents a highscore in the game
 */
public class Highscore implements Comparable<Highscore>{
    private String name;
    private int score;

    private static ArrayList<Highscore> scoreList = new ArrayList<Highscore>();

    /**
     * Returns the list of high scores.
     *
     * @return the list of scores
     */
    public static ArrayList<Highscore> getScoreList()
    {
        return scoreList;
    }

    /**
     * Sets the list of high scores
     *
     * @param scoreList the high score list
     */
    public static void setScoreList(ArrayList<Highscore> scoreList) {
        Highscore.scoreList = scoreList;
    }

    /**
     * Instantiates a default Highscore.
     */
    public Highscore() {
        this.name = "";
        this.score = 100;
    }

    /**
     * Instantiates a new Highscore.
     *
     * @param name  the name of the player
     * @param score the score
     */
    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the score
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Highscore highscore)
    {
        return Integer.valueOf(highscore.score).compareTo(Integer.valueOf(score));
    }

    @Override
    public int hashCode()
    {
        return name.hashCode() + Integer.toString(score).hashCode();
    }
}
