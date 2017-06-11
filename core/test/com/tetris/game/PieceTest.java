package com.tetris.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Piece test.
 */
public class PieceTest extends GameStateTest {
    /**
     * The Test piece.
     */
    Piece testPiece;
    /**
     * The Bottom left.
     */
    int bottomLeft;

    /**
     * Sets up the tests, initializes GameState (super) and Piece
     *
     * @throws Exception handled exeption
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testPiece = new Piece(5, 5, gameTest);
        gameTest.setFallingPiece(testPiece);
        if (testPiece.getTemporaryChar() == 'z')
            bottomLeft = 1;
        else
            bottomLeft = 0;
    }

    /**
     * Test falling of a piece (position) and if it stops when it reaches floor or other pieces
     *
     */
    @Test
    public void testAdvance() throws Exception {
        assertEquals(5 + bottomLeft, testPiece.getSquarePos(0).getX());
        assertEquals(5, testPiece.getSquarePos(0).getY());
        testPiece.advance();
        assertEquals(4, testPiece.getSquarePos(0).getY());
        for (int i = 0; i < 20; i++)
            testPiece.advance();
        assertEquals(0, testPiece.getSquarePos(0).getY());
    }

    /**
     * Test moving left of the piece and if it stops when it reaches side or other pieces.
     *
     * @throws Exception the exception
     */
    @Test
    public void testStrafeLeft() throws Exception {
        assertEquals(5 + bottomLeft, testPiece.getSquarePos(0).getX());
        assertEquals(5, testPiece.getSquarePos(0).getY());
        testPiece.strafeLeft();
        assertEquals(4 + bottomLeft, testPiece.getSquarePos(0).getX());
        for (int i = 0; i < 20; i++)
            testPiece.strafeLeft();
        assertEquals(0 + bottomLeft, testPiece.getSquarePos(0).getX());
    }

    /**
     * Test moving right of the piece and if it stops when it reaches side or other pieces.
     *
     * @throws Exception the exception
     */
    @Test
    public void testStrafeRight() throws Exception {
        assertEquals(5 + bottomLeft, testPiece.getSquarePos(0).getX());
        assertEquals(5, testPiece.getSquarePos(0).getY());
        testPiece.strafeRight();
        assertEquals(6 + bottomLeft, testPiece.getSquarePos(0).getX());
        for (int i = 0; i < 20; i++)
            testPiece.strafeRight();

        int rightEdge = -1;
        for (int a = 0; a < 4; a++)
            if (testPiece.getSquarePos(a).getX() > rightEdge)
                rightEdge = testPiece.getSquarePos(a).getX();
        assertEquals(9, rightEdge);
    }

    /**
     * Test rotation of the piece.
     *
     * @throws Exception the exception
     */
    @Test
    public void testRotate() throws Exception {
        //int rotateType[] = ;
    }

    /**
     * Test if piece is stopped because it reached the floor or other pieces
     *
     * @throws Exception the exception
     */
    @Test
    public void testDone() throws Exception {
        for (int i = 0; i < 20; i++)
            testPiece.advance();
        assertTrue(testPiece.isDone());
    }

    /*
    *Override functions (PieceTest extends GameStateTest)
    *
    */
    @Override
    public void testGameOver() throws Exception {
    }

    /*
    *Override functions (PieceTest extends GameStateTest)
    *
    */
    @Override
    public void testLine() throws Exception {
    }
}