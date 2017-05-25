package com.tetris.game;

import java.util.Arrays;

/**
 * Created by Pipas_ on 18/05/2017.
 */

public class GameState
{
    private char[][] board;
    private Piece fallingPiece;
    Boolean d = false;
    private static GameState gs;

    public static GameState get(){
        if (gs == null)
            gs = new GameState();
        return gs;
    }

    public GameState()
    {
        board = new char[15][10];
        for(int i = 0; i < 15; i++)
            Arrays.fill(board[i], ' ');

        fallingPiece = new Piece(4, 14, this);
    }

    public char[][] getDynamicBoard()
    {
        if(d)
            d = false;

        clearTemporaryBoard();

        for(int i = 0; i < 4; i++)
        {
            if(fallingPiece.getSquarePos(i).getX() < 10 && fallingPiece.getSquarePos(i).getX() >= 0 && fallingPiece.getSquarePos(i).getY() < 15 && fallingPiece.getSquarePos(i).getY() >= 0)
                board[fallingPiece.getSquarePos(i).getY()][fallingPiece.getSquarePos(i).getX()] = fallingPiece.getTemporaryChar();
        }
        return board;
    }

    private void clearTemporaryBoard()
    {
        for(int y = 0; y < 15; y++)
        {
            for(int x = 0; x < 10; x ++)
            {
                if(board[y][x] >= 'a' && board[y][x] <= 'z')
                    board[y][x] = ' ';
            }
        }
    }

    public char[][] getRegularBoard()
    {
        clearTemporaryBoard();
        return board;
    }

    public Piece getFallingPiece()
    {
        return fallingPiece;
    }

    public void advance()
    {
        fallingPiece.advance();
        if(fallingPiece.isDone())
        {
            for(int i = 0; i < 4; i++)
            {
                if(fallingPiece.getSquarePos(i).getX() < 10 && fallingPiece.getSquarePos(i).getX() >= 0 && fallingPiece.getSquarePos(i).getY() < 15 && fallingPiece.getSquarePos(i).getY() >= 0)
                    board[fallingPiece.getSquarePos(i).getY()][fallingPiece.getSquarePos(i).getX()] = fallingPiece.getPermanentChar();
            }
            clearLines();
            generateNewPiece();
        }
    }

    public void rotate()
    {
        fallingPiece.rotate();
    }

    public void strafeLeft()
    {
        fallingPiece.strafeLeft();
    }

    public void strafeRight()
    {
        fallingPiece.strafeRight();
    }

    private void generateNewPiece()
    {
        fallingPiece = new Piece(4, 14, this);
    }

    private void clearLines()
    {
        Boolean deleteLine;
        clearTemporaryBoard();
        for(int y = 0; y < 15; y++)
        {
            deleteLine = true;
            for(int x = 0; x < 10; x++)
            {
                if(board[y][x] == ' ')
                    deleteLine = false;
            }
            if(deleteLine)
            {
                for(int i = y; i < 14; i++)
                {
                    board[i] = board[i+1].clone();
                }
                Arrays.fill(board[14], ' ');
                y--;
            }
        }
    }

    public void debug()
    {
        if(d == false)
            d = true;
        else
            d = false;
    }
}
