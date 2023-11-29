package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/bishop_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.BISHOP);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        return getDiagonalMovePos(board, pos);
    }
}
