package com.tetris.game;

import java.util.Arrays;

import static com.badlogic.gdx.Input.Keys.M;

/**
 * Created by Pipas_ on 18/05/2017.
 */

public class GameState
{
    private char[][] board;
    private Piece fallingPiece;
    Boolean d = false;
    private static GameState gs;
    private int totalLinesDeleted = 0;
    private int linesDeletedRound = 0;
    private int score = 0;
    private int level = 6;

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

    public void instantFall()
    {
        while(!fallingPiece.isDone())
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
                linesDeletedRound++;
                totalLinesDeleted++;
                level = (totalLinesDeleted / 10) + 1;
            }
        }

        if(linesDeletedRound == 1)
            score += 40 * level;
        else if(linesDeletedRound == 2)
            score += 100 * level;
        else if(linesDeletedRound == 3)
            score += 300 * level;
        else
            score += 1200 * level;

        linesDeletedRound = 0;
    }

    public void debug()
    {
        if(d == false)
            d = true;
        else
            d = false;
    }

    public int getScore()
    {
        return score;
    }

    public int getLevel()
    {
        return level;
    }
}
