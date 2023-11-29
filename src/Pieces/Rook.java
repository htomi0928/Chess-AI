package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/rook_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.ROOK);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        return getLinearMovePos(board, pos);
    }
}
