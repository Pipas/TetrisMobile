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
     * Sets up the tests.
     *
     * @throws Exception handled exeption
     */
    @Before
    public void setUp() throws Exception {
        gameTest = new GameState();
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test game over.
     *
     * @throws Exception the exception
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
     * Test line.
     *
     * @throws Exception the exception
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
    }

    /**
     * Test score level.
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

    /**
     * Advance.
     *
     * @throws Exception the exception
     */
    @Test
    public void advance() throws Exception {

    }

    /**
     * Rotate.
     *
     * @throws Exception the exception
     */
    @Test
    public void rotate() throws Exception {

    }

    /**
     * Strafe left.
     *
     * @throws Exception the exception
     */
    @Test
    public void strafeLeft() throws Exception {

    }

    /**
     * Strafe right.
     *
     * @throws Exception the exception
     */
    @Test
    public void strafeRight() throws Exception {

    }

    /**
     * Gets score.
     *
     * @throws Exception the exception
     */
    @Test
    public void getScore() throws Exception {

    }

    /**
     * Gets level.
     *
     * @throws Exception the exception
     */
    @Test
    public void getLevel() throws Exception {

    }

    /**
     * Check game over.
     *
     * @throws Exception the exception
     */
    @Test
    public void checkGameOver() throws Exception {

    }

    /**
     * Was line deleted.
     *
     * @throws Exception the exception
     */
    @Test
    public void wasLineDeleted() throws Exception {

    }

    /**
     * Is piece locked.
     *
     * @throws Exception the exception
     */
    @Test
    public void isPieceLocked() throws Exception {

    }

}