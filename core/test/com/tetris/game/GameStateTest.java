package com.tetris.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The class that handles unit tests for the game
 */
public class GameStateTest {
    /**
     * The Game state to be tested
     */
    GameState gameTest;

    /**
     * Sets up the tests, initializes GameState.
     *
     * @throws Exception handled exeption
     */
    @Before
    public void setUp() throws Exception {
        gameTest = new GameState();
    }

    /**
     * Tests if game is over when a piece reaches the top of the play area
     */
    @Test
    public void testGameOver() throws Exception {
        for (int i = 0; i < 14; i++){
            gameTest.getDynamicBoard()[i][4] = 'T';
            gameTest.getDynamicBoard()[i][5] = 'T';
        }
        assertFalse(gameTest.checkGameOver());
        gameTest.advance();
        gameTest.advance();
        assertTrue(gameTest.checkGameOver());
    }

    /**
     * Tests when a line is formed, if that line is deleted (emptied) and if all the squares are pulled down 1 position
     *
     */
    @Test
    public void testLine() throws Exception {
        for (int i = 0; i < 8; i++)
            gameTest.getDynamicBoard()[0][i] = 'T';
        Piece pTest = new Piece(7, 1, gameTest, 5);
        gameTest.setFallingPiece(pTest);
        assertEquals(' ', gameTest.getDynamicBoard()[0][8]);
        assertNotEquals(' ', gameTest.getDynamicBoard()[0][0]);
        gameTest.advance();
        gameTest.advance();
        for (int c = 0; c < 8; c++)
            assertNotEquals(' ', gameTest.getDynamicBoard()[0][c]);
        gameTest.advance();
        for (int a = 0 ; a < 7; a++)
            assertEquals(' ', gameTest.getDynamicBoard()[0][a]);
        assertEquals('Z', gameTest.getDynamicBoard()[0][7]);
        assertEquals('Z', gameTest.getDynamicBoard()[0][8]);
        assertEquals(' ', gameTest.getDynamicBoard()[0][9]);
        assertEquals(' ', gameTest.getDynamicBoard()[1][7]);
        assertEquals(' ', gameTest.getDynamicBoard()[1][8]);
    }

    /**
     * Test after a number of pieces played and lines formed, if total score and current level are correctly calculated
     *
     * @throws Exception the exception
     */
    @Test
    public void testScoreLevel() throws Exception {
        for (int i = 0; i < 8; i++)
            gameTest.getDynamicBoard()[0][i] = 'T';
        Piece pTest = new Piece(7, 1, gameTest, 5);
        gameTest.setFallingPiece(pTest);
        gameTest.advance();
        gameTest.advance();
        gameTest.advance();
        gameTest.getDynamicBoard()[0][8] = ' ';
        for (int b = 0; b < 2; b++)
            for (int i = 0; i < 8; i++)
                gameTest.getDynamicBoard()[b][i] = 'T';
        pTest = new Piece(8, 2, gameTest, 0);
        gameTest.setFallingPiece(pTest);
        gameTest.advance();
        while(!gameTest.isPieceLocked())
            gameTest.advance();
        for (int b = 0; b < 3; b++)
            for (int i = 0; i < 9; i++)
                gameTest.getDynamicBoard()[b][i] = 'T';
        gameTest.getDynamicBoard()[2][8] = ' ';
        pTest = new Piece(8, 8, gameTest, 2);
        pTest.rotate();
        gameTest.setFallingPiece(pTest);
        gameTest.strafeRight();
        gameTest.advance();
        while(!gameTest.isPieceLocked())
            gameTest.advance();
        for (int c = 0; c < 5; c++){
            for (int b = 0; b < 4; b++)
                for (int i = 0; i < 9; i++)
                    gameTest.getDynamicBoard()[b][i] = 'T';
            pTest = new Piece(9, 8, gameTest, 6);
            pTest.rotate();
            gameTest.setFallingPiece(pTest);
            gameTest.advance();
            for (int n = 0; n < 5; n++)
                gameTest.strafeRight();
            while(!gameTest.isPieceLocked())
                gameTest.advance();
        }
        assertEquals(14840, gameTest.getScore());
        assertEquals(3, gameTest.getLevel());

    }
}