package com.tetris.game;

/**
 * Created by Pipas_ on 18/05/2017.
 */

public class Square
{
    private Position position;

    public Square(int x, int y)
    {
        position = new Position(x, y);
    }

    public Position getPosition()
    {
        return position;
    }
}
