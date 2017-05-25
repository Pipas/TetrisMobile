package com.lpoo.tetris;

/**
 * Created by Pipas_ on 18/05/2017.
 */

public class Position
{
    private int x;
    private int y;

    public Position(int posX, int posY)
    {
        x = posX;
        y = posY;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int posX)
    {
        x = posX;
    }

    public void setY(int posY)
    {
        y = posY;
    }

    public void decreaseY()
    {
        y--;
    }
}
