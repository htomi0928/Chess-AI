package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/queen_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.QUEEN);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        positions.addAll(getDiagonalMovePos(board, pos));
        positions.addAll(getLinearMovePos(board, pos));
        return positions;
    }
}
