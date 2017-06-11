package com.tetris.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexandre on 10-06-2017.
 */
public class PieceTest extends GameStateTest {
    Piece testPiece;
    int bottomLeft;

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

    @After
    public void tearDown() throws Exception {

    }

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

    @Test
    public void testRotate() throws Exception {
        //int rotateType[] = ;
    }

    @Test
    public void testDone() throws Exception {
        for (int i = 0; i < 20; i++)
            testPiece.advance();
        assertTrue(testPiece.isDone());
    }

    @Test
    public void rotate() throws Exception {

    }

    @Override
    public void testGameOver() throws Exception {
    }

    @Override
    public void testLine() throws Exception {
    }

    @Test
    public void strafeLeft() throws Exception {

    }

    @Test
    public void strafeRight() throws Exception {

    }

    @Test
    public void isDone() throws Exception {

    }

    @Test
    public void getTemporaryChar() throws Exception {

    }

    @Test
    public void getPermanentChar() throws Exception {

    }

}