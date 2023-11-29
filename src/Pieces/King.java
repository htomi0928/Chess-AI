package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/king_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.KING);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            int first_of_next_row = pos / 8 * 8 + (i + 1) * 8;
            int last_of_prev_row = pos / 8 * 8 + i * 8 - 1;
            for (int j = -1; j <= 1; j++) {
                int possibleMove = pos + i * 8 + j;
                if (possibleMove != pos && possibleMove >= 0 && possibleMove < 64 && possibleMove < first_of_next_row && possibleMove > last_of_prev_row) {
                    if (board[possibleMove] == null || board[possibleMove].isWhite() != board[pos].isWhite()) {
                        positions.add(possibleMove);
                    }
                }
            }
        }
        if (pos >= 4 && board[pos - 4] != null && !this.hasMoved() && board[pos - 4].getType() == PieceType.ROOK && !board[pos - 4].hasMoved() && board[pos - 3] == null && board[pos - 2] == null && board[pos - 1] == null) {//&& !Interface.MainWindow.gl.isInCheck(board, this.isWhite())) {
            positions.add(pos - 2);
        }
        if (pos <= 60 && board[pos + 3] != null && !this.hasMoved() && board[pos + 3].getType() == PieceType.ROOK && !board[pos + 3].hasMoved() && board[pos + 2] == null && board[pos + 1] == null) {//&& !Interface.MainWindow.gl.isInCheck(board, this.isWhite())) {
            positions.add(pos + 2);
        }
        return positions;
    }
}
