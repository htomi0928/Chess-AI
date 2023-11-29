package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean hasDoubleMoved = false;

    public Pawn(boolean isWhite) {
        super(isWhite, new ImageIcon("src/images/pawn_" + (isWhite ? "white" : "black") + ".png").getImage(), PieceType.PAWN);
    }

    @Override
    public ArrayList<Integer> getMoves(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        if (board[pos].isWhite()) {
            if (pos - 8 >= 0) {
                if (board[pos - 8] == null) {
                    if (!this.hasMoved()) {
                        if (pos - 16 > 0 && board[pos - 16] == null) {
                            positions.add(pos - 16);
                        }
                    }
                    positions.add(pos - 8);
                }
                if (pos - 9 >= 0 && board[pos - 9] == null && board[pos - 1] != null && board[pos - 1].getType() == PieceType.PAWN && ((Pawn) board[pos - 1]).hasDoubleMoved && board[pos - 1].isWhite() != board[pos].isWhite()) {
                    if ((pos - 8) / 8 == (pos - 9) / 8) {
                        positions.add(pos - 9);
                    }
                }
                if (board[pos - 7] == null && board[pos + 1] != null && board[pos + 1].getType() == PieceType.PAWN && ((Pawn) board[pos + 1]).hasDoubleMoved && board[pos + 1].isWhite() != board[pos].isWhite()) {
                    if ((pos - 8) / 8 == (pos - 7) / 8) {
                        positions.add(pos - 7);
                    }
                }
                if (pos - 9 >= 0 && board[pos - 9] != null && board[pos - 9].isWhite() != board[pos].isWhite()) {
                    if ((pos - 8) / 8 == (pos - 9) / 8) {
                        positions.add(pos - 9);
                    }
                }
                if (board[pos - 7] != null && board[pos - 7].isWhite() != board[pos].isWhite()) {
                    if ((pos - 8) / 8 == (pos - 7) / 8) {
                        positions.add(pos - 7);
                    }
                }
            }

        } else {
            if (pos + 8 < 64) {
                if (board[pos + 8] == null) {

                    if (!this.hasMoved()) {
                        if (pos + 16 < 64 && board[pos + 16] == null) {
                            positions.add(pos + 16);
                        }
                    }
                    positions.add(pos + 8);
                }
                if (pos + 9 < 64 && board[pos + 9] == null && board[pos + 1] != null && board[pos + 1].getType() == PieceType.PAWN && ((Pawn) board[pos + 1]).hasDoubleMoved && board[pos + 1].isWhite() != board[pos].isWhite()) {
                    if ((pos + 8) / 8 == (pos + 9) / 8) {
                        positions.add(pos + 9);
                    }
                }
                if (board[pos + 7] == null && board[pos - 1] != null && board[pos - 1].getType() == PieceType.PAWN && ((Pawn) board[pos - 1]).hasDoubleMoved && board[pos - 1].isWhite() != board[pos].isWhite()) {
                    if ((pos + 8) / 8 == (pos + 7) / 8) {
                        positions.add(pos + 7);
                    }
                }
                if (pos + 9 < 64 && board[pos + 9] != null && board[pos + 9].isWhite() != board[pos].isWhite()) {
                    if ((pos + 8) / 8 == (pos + 9) / 8) {
                        positions.add(pos + 9);
                    }
                }
                if (board[pos + 7] != null && board[pos + 7].isWhite() != board[pos].isWhite()) {
                    if ((pos + 8) / 8 == (pos + 7) / 8) {
                        positions.add(pos + 7);
                    }
                }
            }
        }
        return positions;
    }

    public boolean hasDoubleMoved() {
        return this.hasDoubleMoved;
    }

    public void setHasDoubleMoved(boolean hasDoubleMoved) {
        this.hasDoubleMoved = hasDoubleMoved;
    }
}
