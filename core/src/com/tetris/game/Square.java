package com.tetris.game;

/**
 * Class that represents a square in the game
 */
public class Square
{
    private Position position;

    /**
     * Instantiates a new Square.
     *
     * @param x the x
     * @param y the y
     */
    public Square(int x, int y)
    {
        position = new Position(x, y);
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Position getPosition()
    {
        return position;
    }
}
