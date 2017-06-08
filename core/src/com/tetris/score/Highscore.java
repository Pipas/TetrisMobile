package com.tetris.score;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.M;

/**
 * Created by Alexandre on 07-06-2017.
 */

public class Highscore implements Comparable<Highscore>{
    private String name;
    private int score;

    private static ArrayList<Highscore> scoreList = new ArrayList<Highscore>();

    public static ArrayList<Highscore> getScoreList()
    {
        return scoreList;
    }

    public static void setScoreList(ArrayList<Highscore> scoreList) {
        Highscore.scoreList = scoreList;
    }

    public Highscore() {
        this.name = "";
        this.score = 100;
    }

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

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
