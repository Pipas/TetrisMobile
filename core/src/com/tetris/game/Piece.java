package com.tetris.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pipas_ on 18/05/2017.
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

    public Piece(int x, int y, GameState g)
    {
        gameState = g;
        position = new Position(x, y);
        Random rand = new Random();
        newPiece(rand.nextInt(7));
    }

    private void newPiece(int ID)
    {
        switch(ID)
        {
            case 0:
                squares.add(new Square(0 ,0));
                squares.add(new Square(1 ,0));
                squares.add(new Square(0 ,1));
                squares.add(new Square(1 ,1));
                temporaryChar = 's';
                permanentChar = 'S';
                break;
            case 1:
                squares.add(new Square(0 ,0));
                squares.add(new Square(1 ,0));
                squares.add(new Square(2 ,0));
                squares.add(new Square(0 ,1));
                temporaryChar = 'k';
                permanentChar = 'K';
                break;
            case 2:
                squares.add(new Square(0 ,0));
                squares.add(new Square(1 ,0));
                squares.add(new Square(2 ,0));
                squares.add(new Square(2 ,1));
                temporaryChar = 'l';
                permanentChar = 'L';
                break;
            case 3:
                squares.add(new Square(0 ,0));
                squares.add(new Square(1 ,0));
                squares.add(new Square(2 ,0));
                squares.add(new Square(1 ,1));
                temporaryChar = 't';
                permanentChar = 'T';
                break;
            case 4:
                squares.add(new Square(0 ,0));
                squares.add(new Square(1 ,0));
                squares.add(new Square(1 ,1));
                squares.add(new Square(2 ,1));
                temporaryChar = 'v';
                permanentChar = 'V';
                break;
            case 5:
                squares.add(new Square(1 ,0));
                squares.add(new Square(2 ,0));
                squares.add(new Square(0 ,1));
                squares.add(new Square(1 ,1));
                temporaryChar = 'a';
                permanentChar = 'A';
                break;
            case 6:
                squares.add(new Square(1 ,0));
                squares.add(new Square(2 ,0));
                squares.add(new Square(3 ,0));
                squares.add(new Square(4 ,0));
                temporaryChar = 'i';
                permanentChar = 'I';
                break;
        }
    }

    public Position getPos()
    {
        return position;
    }

    public Position getSquarePos(int index)
    {
        Position squarePosition = new Position(squares.get(index).getPosition().getX(), squares.get(index).getPosition().getY());

        squarePosition.setX(squarePosition.getX() + position.getX());
        squarePosition.setY(squarePosition.getY() + position.getY());

        return squarePosition;
    }

    public void advance()
    {
        if(canFall())
        {
            position.decreaseY();
        }
        else
        {
            endState = true;
        }
    }

    public void rotate()
    {
        Position[] newPositions = {new Position(0, 0), new Position(0, 0), new Position(0, 0), new Position(0, 0)};
        if(temporaryChar == 'k' || temporaryChar == 'l' || temporaryChar == 't' || temporaryChar == 'v' || temporaryChar == 'a')
        {
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
                if ((position.getY() + newPositions[i].getY()) < 14 && (position.getY() + newPositions[i].getY()) > 0)
                {
                    if ((position.getX() + newPositions[i].getX()) > 9 || (position.getX() + newPositions[i].getX()) < 0 || gameState.getRegularBoard()[position.getY() + newPositions[i].getY()][position.getX() + newPositions[i].getX()] != ' ')
                        return;
                }
            }
            for(int i = 0; i < 4; i++)
            {
                squares.get(i).getPosition().setX(newPositions[i].getX());
                squares.get(i).getPosition().setY(newPositions[i].getY());
            }
        }
        else if(temporaryChar == 'i')
        {
            for(int i = 0; i < 4; i++)
            {
                if (isHorizontal)
                {
                    newPositions[i].setY(squares.get(i).getPosition().getX());
                    newPositions[i].setX(1);
                }
                else
                {
                    newPositions[i].setY(0);
                    newPositions[i].setX(squares.get(i).getPosition().getY());
                }
                if ((position.getY() + newPositions[i].getY()) < 14 && (position.getY() + newPositions[i].getY()) > 0)
                {
                    if ((position.getX() + newPositions[i].getX()) > 9 || (position.getX() + newPositions[i].getX()) < 0 || gameState.getRegularBoard()[position.getY() + newPositions[i].getY()][position.getX() + newPositions[i].getX()] != ' ')
                        return;
                }
            }
            for(int i = 0; i < 4; i++)
            {
                squares.get(i).getPosition().setX(newPositions[i].getX());
                squares.get(i).getPosition().setY(newPositions[i].getY());
            }
        }
        if(isHorizontal)
            isHorizontal = false;
        else
            isHorizontal = true;
    }

    public void strafeLeft()
    {
        for(int i = 0; i < 4; i++)
        {
            if(getSquarePos(i).getX() < 10 && getSquarePos(i).getX() >= 0 && getSquarePos(i).getY() < 15 && getSquarePos(i).getY() >= 0)
            {
                if(getSquarePos(i).getX() - 1 < 0)
                    return;
                if(gameState.getRegularBoard()[getSquarePos(i).getY()][getSquarePos(i).getX() - 1] != ' ')
                    return;
            }
        }
        position.setX(position.getX() - 1);
    }

    public void strafeRight()
    {
        for(int i = 0; i < 4; i++)
        {
            if(getSquarePos(i).getX() < 10 && getSquarePos(i).getX() >= 0 && getSquarePos(i).getY() < 15 && getSquarePos(i).getY() >= 0)
            {
                if(getSquarePos(i).getX() + 1 > 9)
                    return;
                if(gameState.getRegularBoard()[getSquarePos(i).getY()][getSquarePos(i).getX() + 1] != ' ')
                    return;
            }
        }
        position.setX(position.getX() + 1);
    }

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

    public char getTemporaryChar()
    {
        return temporaryChar;
    }

    public char getPermanentChar()
    {
        return permanentChar;
    }
}
