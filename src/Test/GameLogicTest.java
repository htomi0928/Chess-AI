package Test;

import Interface.Board;
import Interface.GameLogic;
import Pieces.Piece;
import Pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {
    @Test
    public void MoveTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);
        assertNull(board.getSquares()[16]);
        Piece movePawn = board.getSquares()[8];
        assertEquals(board.getSquares()[8].getType(), PieceType.PAWN);
        gl.makeMove(board.getSquares(), 8, 16);
        assertEquals(board.getSquares()[16], movePawn);
        assertNull(board.getSquares()[8]);

        Piece moveRook = board.getSquares()[0];
        assertEquals(board.getSquares()[0].getType(), PieceType.ROOK);
        gl.makeMove(board.getSquares(), 0, 8);
        assertEquals(board.getSquares()[8], moveRook);
        assertNull(board.getSquares()[0]);
    }

    @Test
    public void testMoveTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);
        assertTrue(gl.testMove(board.getSquares(), 8, 16));

        assertTrue(gl.testMove(board.getSquares(), 8, 24));

        board = new Board("3Qk3/p7/8/8/8/8/8/4K3", gl);

        assertFalse(gl.testMove(board.getSquares(), 48, 40));
    }

    @Test
    public void kingPosTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("4k3/8/8/8/8/8/8/4K3", gl);
        assertEquals(gl.kingPos(board.getSquares(), false), 4);
        assertEquals(gl.kingPos(board.getSquares(), true), 60);
        gl.makeMove(board.getSquares(), 4, 5);
        assertEquals(gl.kingPos(board.getSquares(), false), 5);

    }

    @Test
    public void checkTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);
        assertFalse(gl.isInCheck(board.getSquares(), true));

        board = new Board("3Qk3/p7/8/8/8/8/8/4K3", gl);
        assertTrue(gl.isInCheck(board.getSquares(), true));
        assertFalse(gl.checkMate(board.getSquares(), true));

        board = new Board("3QkQ2/p7/8/8/8/8/8/4K3", gl);
        assertTrue(gl.checkMate(board.getSquares(), true));
    }

    @Test
    public void evaluateTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);
        assertEquals(gl.evaluate(board.getSquares()), 0);
        board = new Board("1nbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);
        assertEquals(gl.evaluate(board.getSquares()), -5);
    }

    @Test
    public void AITest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("5k2/1R6/8/8/8/8/8/3RK3", gl);
        int[] aiMove = gl.minimax(board.getSquares(), 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        assertEquals(aiMove[0], 3);
        assertEquals(aiMove[1], 59);
    }

    @Test
    public void gameOverTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("4k3/8/3Q1Q2/8/8/8/8/4K3", gl);
        assertTrue(gl.gameOver(board.getSquares(), true));
        assertTrue(gl.isStaleMate(board.getSquares(), true));
        assertFalse(gl.isInCheck(board.getSquares(), true));
        board = new Board("k7/1R1RN3/p3p3/P3P2p/1PP4P/3K1PP1/8/8", gl);
        assertTrue(gl.isStaleMate(board.getSquares(), true));
        assertFalse(gl.isStaleMate(board.getSquares(), false));

    }
}
