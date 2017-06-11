package com.tetris.game;

import java.util.Arrays;

import static com.badlogic.gdx.Input.Keys.M;
import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.line;

/**
 * Class that handles the state of the game being played
 */
public class GameState
{
    private char[][] board;

    private Piece fallingPiece;
    private Piece nextPiece;
    private int totalLinesDeleted = 0;
    private int linesDeletedRound = 0;
    private int score = 0;
    private int level = 1;
    private boolean hoverPeriod = false;
    private boolean gameOver = false;
    private boolean lineWasDeleted = false;
    private boolean pieceLocked = false;

    /**
     * Instantiates a new Game state.
     */
    public GameState()
    {
        board = new char[15][10];
        for(int i = 0; i < 15; i++)
            Arrays.fill(board[i], ' ');

        fallingPiece = new Piece(4, 14, this);
        nextPiece = new Piece(4, 14, this);
    }

    /**
     * Returns the state of the board with the falling piece.
     *
     * @return the state of the board
     */
    public char[][] getDynamicBoard()
    {
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

    /**
     * Returns the state of the board without the currently falling piece.
     *
     * @return the state of the board
     */
    public char[][] getRegularBoard()
    {
        clearTemporaryBoard();
        return board;
    }

    /**
     * Returns the next piece to fall after the one in play reaches the bottom
     *
     * @return the next piece
     */
    public Piece getNextPiece()
    {
        return nextPiece;
    }

    /**
     * Returns the piece currently falling
     *
     * @return the falling piece
     */
    public Piece getFallingPiece() {
        return fallingPiece;
    }

    /**
     * Sets the falling piece
     *
     * @param p the piece to set as falling
     */
    public void setFallingPiece(Piece p) {
        fallingPiece = p;
    }

    /**
     * Advances the game state. Makes the piece fall and if it reaches the floor handles replacing it with the next piece.
     */
    public void advance()
    {
        lineWasDeleted = false;
        fallingPiece.advance();
        if(!fallingPiece.isDone())
        {
            hoverPeriod = false;
            pieceLocked = false;
        }
        if(hoverPeriod)
        {
            for(int i = 0; i < 4; i++)
            {
                if(fallingPiece.getSquarePos(i).getX() < 10 && fallingPiece.getSquarePos(i).getX() >= 0 && fallingPiece.getSquarePos(i).getY() < 15 && fallingPiece.getSquarePos(i).getY() >= 0)
                    board[fallingPiece.getSquarePos(i).getY()][fallingPiece.getSquarePos(i).getX()] = fallingPiece.getPermanentChar();
            }
            hoverPeriod = false;
            pieceLocked = true;
            clearLines();
            generateNewPiece();
        }

        if(fallingPiece.isDone())
            hoverPeriod = true;
    }

    /**
     * Rotates the currently falling piece
     *
     * @return if the piece rotated
     */
    public Boolean rotate()
    {
        return fallingPiece.rotate();
    }

    /**
     * Strafes the falling piece left
     *
     * @return if the piece moved left
     */
    public Boolean strafeLeft()
    {
        return fallingPiece.strafeLeft();
    }

    /**
     * Strafes the falling piece right
     *
     * @return if the piece moved left
     */
    public Boolean strafeRight()
    {
        return fallingPiece.strafeRight();
    }

    private void generateNewPiece()
    {
        fallingPiece = nextPiece;
        nextPiece = new Piece(4, 14, this);
        if(getRegularBoard()[14][4] >= 'A' && getRegularBoard()[14][4] <= 'Z' || (getRegularBoard()[14][5] >= 'A' && getRegularBoard()[14][5] <= 'Z'))
            gameOver = true;
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
                lineWasDeleted = true;
            }
        }

        addScore();

        linesDeletedRound = 0;
    }

    private void addScore()
    {
        if(linesDeletedRound == 1)
            score += 40 * level;
        else if(linesDeletedRound == 2)
            score += 100 * level;
        else if(linesDeletedRound == 3)
            score += 300 * level;
        else if(linesDeletedRound == 4)
            score += 1200 * level;
    }

    /**
     * Returns the current score of the game.
     *
     * @return the current score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Returns the current level of the game.
     *
     * @return the current level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Checks if the game is over
     *
     * @return if the game is over
     */
    public boolean checkGameOver()
    {
        return gameOver;
    }

    /**
     * Checks if one or more lines were deleted
     *
     * @return if it deletes
     */
    public boolean wasLineDeleted()
    {
        return lineWasDeleted;
    }

    /**
     * Checks if the currently falling piece locks in the play area
     *
     * @return if it locks
     */
    public boolean isPieceLocked()
    {
        return pieceLocked;
    }
}
