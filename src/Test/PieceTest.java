package Test;

import Interface.Board;
import Interface.GameLogic;
import Pieces.Piece;
import Pieces.PieceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    public void PiecePossibleMovesTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("8/8/8/3Q4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(28, 21, 14, 7, 26, 17, 8, 44, 53, 62, 42, 49, 56, 36, 37, 38, 39, 27, 19, 11, 3, 43, 51, 59, 34, 33, 32)), board.getSquares()[35].getMoves(board.getSquares(), 35));

        board = new Board("8/8/8/3R4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(36, 37, 38, 39, 27, 19, 11, 3, 43, 51, 59, 34, 33, 32)), board.getSquares()[35].getMoves(board.getSquares(), 35));

        board = new Board("8/8/8/3B4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(28, 21, 14, 7, 26, 17, 8, 44, 53, 62, 42, 49, 56)), board.getSquares()[35].getMoves(board.getSquares(), 35));

        board = new Board("8/8/8/3K4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(26, 27, 28, 34, 36, 42, 43, 44)), board.getSquares()[35].getMoves(board.getSquares(), 35));

        board = new Board("8/8/8/3N4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(18, 20, 25, 29, 41, 45, 50, 52)), board.getSquares()[35].getMoves(board.getSquares(), 35));

        board = new Board("p7/8/8/8/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(40, 48)), board.getSquares()[56].getMoves(board.getSquares(), 56));
    }

    @Test
    public void CastleTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("r3k2r/8/8/8/8/8/8/R3K2R", gl);
        int blackKingPos = 4;
        Piece blackKing = board.getSquares()[blackKingPos];
        assertEquals(blackKing.getMoves(board.getSquares(), blackKingPos), Arrays.asList(3, 5, 11, 12, 13, 2, 6));
        gl.makeMove(board.getSquares(), 4, 2);
        assertEquals(board.getSquares()[2].getType(), PieceType.KING);
        assertEquals(board.getSquares()[3].getType(), PieceType.ROOK);

        gl.makeMove(board.getSquares(), 60, 58);
        assertEquals(board.getSquares()[58].getType(), PieceType.KING);
        assertEquals(board.getSquares()[59].getType(), PieceType.ROOK);


        board = new Board("r3k2r/8/8/8/8/8/8/R3K2R", gl);
        gl.makeMove(board.getSquares(), 4, 6);
        assertEquals(board.getSquares()[6].getType(), PieceType.KING);
        assertEquals(board.getSquares()[5].getType(), PieceType.ROOK);

        gl.makeMove(board.getSquares(), 60, 62);
        assertEquals(board.getSquares()[62].getType(), PieceType.KING);
        assertEquals(board.getSquares()[61].getType(), PieceType.ROOK);
    }

    @Test
    public void enPassantTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("8/p7/8/1P6/1p6/8/P7/8", gl);
        assertFalse(board.getSquares()[33].getMoves(board.getSquares(), 33).contains(40));
        gl.makeMove(board.getSquares(), 48, 32);
        assertTrue(board.getSquares()[33].getMoves(board.getSquares(), 33).contains(40));
        gl.makeMove(board.getSquares(), 33, 40);
        assertNull(board.getSquares()[32]);

        assertFalse(board.getSquares()[25].getMoves(board.getSquares(), 25).contains(16));
        gl.makeMove(board.getSquares(), 8, 24);
        assertTrue(board.getSquares()[25].getMoves(board.getSquares(), 25).contains(16));
        gl.makeMove(board.getSquares(), 25, 16);
        assertNull(board.getSquares()[32]);
    }

    @Test
    public void LinearDiagonalMovesTest() {
        GameLogic gl = new GameLogic(false);
        Board board = new Board("8/8/8/3Q4/8/8/8/8", gl);
        assertEquals(new ArrayList<>(Arrays.asList(28, 21, 14, 7, 26, 17, 8, 44, 53, 62, 42, 49, 56)), Piece.getDiagonalMovePos(board.getSquares(), 35));
        assertEquals(new ArrayList<>(Arrays.asList(36, 37, 38, 39, 27, 19, 11, 3, 43, 51, 59, 34, 33, 32)), Piece.getLinearMovePos(board.getSquares(), 35));
    }
}