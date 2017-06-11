package com.tetris.game;

/**
 * Class that represents a position
 */
public class Position
{
    private int x;
    private int y;

    /**
     * Instantiates a new Position.
     *
     * @param posX the x coordinate
     * @param posY the y coordinate
     */
    public Position(int posX, int posY)
    {
        x = posX;
        y = posY;
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Returns the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * Sets the x coordinate.
     *
     * @param posX the x coordinate
     */
    public void setX(int posX)
    {
        x = posX;
    }

    /**
     * Sets the y coordinate.
     *
     * @param posY the y coordinate
     */
    public void setY(int posY)
    {
        y = posY;
    }

    /**
     * Decreases the y coordinate
     */
    public void decreaseY()
    {
        y--;
    }
}
