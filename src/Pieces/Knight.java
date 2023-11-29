package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/knight_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.KNIGHT);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        int[] moves = {-17, -15, -10, -6, 6, 10, 15, 17};
        for (int move : moves) {
            int column = (pos + move) % 8;
            if (pos + move >= 0 && pos + move < 64) {
                if (board[pos + move] != null) {
                    if (board[pos + move].isWhite() != board[pos].isWhite()) {
                        if (pos % 8 > 5) {
                            if (column % 8 > 1) {
                                positions.add(pos + move);
                            }
                        } else if (pos % 8 < 2) {
                            if (column % 8 < 6) {
                                positions.add(pos + move);
                            }
                        } else {
                            positions.add(pos + move);
                        }
                    }
                } else {
                    if (pos % 8 > 5) {
                        if (column % 8 > 1) {
                            positions.add(pos + move);
                        }
                    } else if (pos % 8 < 2) {
                        if (column % 8 < 6) {
                            positions.add(pos + move);
                        }
                    } else {
                        positions.add(pos + move);
                    }
                }
            }
        }
        return positions;
    }
}
