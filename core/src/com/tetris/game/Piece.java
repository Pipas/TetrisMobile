package com.tetris.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents a piece in the game.
 */
public class Piece
{
    private ArrayList<Square> squares = new ArrayList<Square>();
    private Position position;
    private Boolean endState = false;
    private Boolean isHorizontal = true;
    private GameState gameState;
    private char temporaryChar;
    private char permanentChar;

    /**
     * Instantiates a new Piece.
     *
     * @param x x position of the piece on the play area
     * @param y y position of the piece on the play area
     * @param g the game state where the piece is instanciated
     */
    public Piece(int x, int y, GameState g)
    {
        gameState = g;
        position = new Position(x, y);
        Random rand = new Random();
        newPiece(rand.nextInt(7));
    }

    /**
     * Instantiates a new Piece.
     *
     * @param x x position of the piece on the play area
     * @param y y position of the piece on the play area
     * @param g the game state where the piece is instanciated
     * @param type the type of piece to be instantiated
     */
    public Piece(int x, int y, GameState g, int type)
    {
        gameState = g;
        position = new Position(x, y);
        Random rand = new Random();
        newPiece(type);
    }

    private void newPiece(int ID)
    {
        switch(ID)
        {
            case 0:
                initiateSquarePosition(0, 1, 0, 1, 0, 0, 1, 1,'o','O');
                break;
            case 1:
                initiateSquarePosition(0, 1, 2, 0, 0, 0, 0, 1,'j','J');
                break;
            case 2:
                initiateSquarePosition(0, 1, 2, 2, 0, 0, 0, 1,'l','L');
                break;
            case 3:
                initiateSquarePosition(0, 1, 2, 1, 0, 0, 0, 1,'t','T');
                break;
            case 4:
                initiateSquarePosition(0, 1, 1, 2, 0, 0, 1, 1,'s','S');
                break;
            case 5:
                initiateSquarePosition(1, 2, 0, 1, 0, 0, 1, 1,'z','Z');
                break;
            case 6:
                initiateSquarePosition(0, 1, 2, 3, 0, 0, 0, 0,'i','I');
                break;
        }
    }

    private void initiateSquarePosition(int x1, int x2, int x3, int x4, int y1, int y2, int y3, int y4, char temporary, char permanent)
    {
        squares.add(new Square(x1 ,y1));
        squares.add(new Square(x2 ,y2));
        squares.add(new Square(x3 ,y3));
        squares.add(new Square(x4 ,y4));
        temporaryChar = temporary;
        permanentChar = permanent;
    }

    /**
     * Returns the current position of the piece
     *
     * @return the position of the piece
     */
    public Position getPos()
    {
        return position;
    }

    /**
     * Returns a square from the piece with the index 'index'
     *
     * @param index the index of the square
     * @return the square
     */
    public Position getSquare(int index)
    {
        return squares.get(index).getPosition();
    }

    /**
     * Returns the position of a square from the piece relative to the play area with the index 'index'
     *
     * @param index the index of the square
     * @return the square position
     */
    public Position getSquarePos(int index)
    {
        Position squarePosition = new Position(squares.get(index).getPosition().getX(), squares.get(index).getPosition().getY());

        squarePosition.setX(squarePosition.getX() + position.getX());
        squarePosition.setY(squarePosition.getY() + position.getY());

        return squarePosition;
    }

    /**
     * Advances the state of the piece. It decreases it's Y position and if it reaches the bottom sets the piece endState as true
     */
    public void advance()
    {
        if(canFall())
        {
            position.decreaseY();
            endState = false;
        }
        else
        {
            endState = true;
        }
    }

    /**
     * Rotates the piece if it's possible
     *
     * @return if the piece rotates
     */
    public Boolean rotate()
    {
        if(temporaryChar == 'j' || temporaryChar == 'l' || temporaryChar == 't' || temporaryChar == 's' || temporaryChar == 'z')
            return rotateSmall();
        else if(temporaryChar == 'i')
            return rotateLong();
        else
            return false;
    }

    private boolean rotateSmall()
    {
        Position[] newPositions = getNewPositionsSmall();

        if(position.getX() == 8)
        {
            if(!checkValidNewPositions(7, position.getY(), newPositions))
                return false;

            position.setX(7);
        }
        else
        {
            if(!checkValidNewPositions(position.getX(), position.getY(), newPositions))
                return false;
        }

        setNewPositions(newPositions);

        isHorizontal = !isHorizontal;

        return true;
    }

    private boolean rotateLong()
    {
        Position[] newPositions = getNewPositionsLong();

        if(position.getX() >= 7)
        {
            if(!checkValidNewPositions(6, position.getY(), newPositions))
                return false;

            position.setX(6);
        }
        else
        {
            if(!checkValidNewPositions(position.getX(), position.getY(), newPositions))
                return false;
        }

        setNewPositions(newPositions);

        isHorizontal = !isHorizontal;

        return true;
    }

    private Position[] getNewPositionsSmall()
    {
        Position[] newPositions = {new Position(0, 0), new Position(0, 0), new Position(0, 0), new Position(0, 0)};
        for(int i = 0; i < 4; i++)
        {
            newPositions[i].setY(squares.get(i).getPosition().getX());
            if (isHorizontal)
            {
                if (squares.get(i).getPosition().getY() == 0)
                    newPositions[i].setX(1);
                else
                    newPositions[i].setX(0);
            }
            else
            {
                if (squares.get(i).getPosition().getY() == 0)
                    newPositions[i].setX(2);
                else if (squares.get(i).getPosition().getY() == 2)
                    newPositions[i].setX(0);
                else
                    newPositions[i].setX(1);
            }
        }
        return newPositions;
    }

    private Position[] getNewPositionsLong()
    {
        Position[] newPositions = {new Position(0, 0), new Position(0, 0), new Position(0, 0), new Position(0, 0)};
        for(int i = 0; i < 4; i++)
        {
            if(isHorizontal)
            {
                newPositions[i].setY(squares.get(i).getPosition().getX());
                newPositions[i].setX(1);
            }
            else
            {
                newPositions[i].setY(0);
                newPositions[i].setX(squares.get(i).getPosition().getY());
            }
        }
        return newPositions;
    }

    private boolean checkValidNewPositions(int X, int Y, Position[] newPositions)
    {
        for(int i = 0; i < 4; i++)
        {
            if ((Y + newPositions[i].getY()) < 14 && (Y + newPositions[i].getY()) > 0)
            {
                if ((X + newPositions[i].getX()) > 9 || (X + newPositions[i].getX()) < 0 || gameState.getRegularBoard()[Y + newPositions[i].getY()][X + newPositions[i].getX()] != ' ')
                    return false;
            }
        }

        return true;
    }

    private void setNewPositions(Position[] newPositions)
    {
        for(int i = 0; i < 4; i++)
        {
            squares.get(i).getPosition().setX(newPositions[i].getX());
            squares.get(i).getPosition().setY(newPositions[i].getY());
        }
    }

    /**
     * Strafes the piece left if possible
     *
     * @return if the piece moves left
     */
    public Boolean strafeLeft()
    {
        for(int i = 0; i < 4; i++)
        {
            if(getSquarePos(i).getX() < 10 && getSquarePos(i).getX() >= 0 && getSquarePos(i).getY() < 15 && getSquarePos(i).getY() >= 0)
            {
                if(getSquarePos(i).getX() - 1 < 0)
                    return false;
                if(gameState.getRegularBoard()[getSquarePos(i).getY()][getSquarePos(i).getX() - 1] != ' ')
                    return false;
            }
        }
        position.setX(position.getX() - 1);

        return true;
    }

    /**
     * Strafes the piece right if possible
     *
     * @return if the piece moves eight
     */
    public Boolean strafeRight()
    {
        for(int i = 0; i < 4; i++)
        {
            if(getSquarePos(i).getX() < 10 && getSquarePos(i).getX() >= 0 && getSquarePos(i).getY() < 15 && getSquarePos(i).getY() >= 0)
            {
                if(getSquarePos(i).getX() + 1 > 9)
                    return false;
                if(gameState.getRegularBoard()[getSquarePos(i).getY()][getSquarePos(i).getX() + 1] != ' ')
                    return false;
            }
        }
        position.setX(position.getX() + 1);

        return true;
    }

    /**
     * Returns if the piece reaches it's end state
     *
     * @return if the piece reached it's end state
     */
    public Boolean isDone()
    {
        return endState;
    }

    private Boolean canFall()
    {
        for(int i = 0; i < 4; i++)
        {
            if(getSquarePos(i).getX() < 10 && getSquarePos(i).getX() >= 0 && getSquarePos(i).getY() < 15 && getSquarePos(i).getY() >= 0)
            {
                if(getSquarePos(i).getY() <= 0 || gameState.getRegularBoard()[getSquarePos(i).getY() - 1][getSquarePos(i).getX()] != ' ')
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns the representation of the piece in the game state while it's still falling
     *
     * @return the temporary representation
     */
    public char getTemporaryChar()
    {
        return temporaryChar;
    }

    /**
     * Returns the representation of the piece in the game state when it locks to the floor
     *
     * @return the permanent representation
     */
    public char getPermanentChar()
    {
        return permanentChar;
    }
}
