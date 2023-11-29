package Pieces;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    private final Image img;
    private final PieceType type;
    private boolean isWhite;
    private boolean hasMoved = false;

    public Piece(boolean isWhite, Image img, PieceType type) {
        this.isWhite = isWhite;
        this.img = img;
        this.type = type;
    }

    public static ArrayList<Integer> getLinearMovePos(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        int rightMovePos = pos, downMovePos = pos, leftMovePos = pos, upMovePos = pos;

        while (rightMovePos % 8 != 7) {
            rightMovePos++;
            if (rightMovePos < 64 && rightMovePos >= 0) {
                if (board[rightMovePos] != null) {
                    if (board[pos].isWhite != board[rightMovePos].isWhite) {
                        positions.add(rightMovePos);
                    }
                    break;
                } else {
                    positions.add(rightMovePos);
                }
            }
        }

        while (upMovePos > 0) {
            upMovePos -= 8;
            if (upMovePos < 64 && upMovePos >= 0) {
                if (board[upMovePos] != null) {
                    if (board[pos].isWhite != board[upMovePos].isWhite) {
                        positions.add(upMovePos);
                    }
                    break;
                } else {
                    positions.add(upMovePos);
                }
            }

        }

        while (downMovePos < 64) {
            downMovePos += 8;
            if (downMovePos < 64 && downMovePos >= 0) {
                if (board[downMovePos] != null) {
                    if (board[pos].isWhite != board[downMovePos].isWhite) {
                        positions.add(downMovePos);
                    }
                    break;
                } else {
                    positions.add(downMovePos);
                }
            }
        }

        while (leftMovePos % 8 != 0) {
            leftMovePos--;
            if (leftMovePos < 64 && leftMovePos >= 0) {
                if (board[leftMovePos] != null) {
                    if (board[pos].isWhite != board[leftMovePos].isWhite) {
                        positions.add(leftMovePos);
                    }
                    break;
                } else {
                    positions.add(leftMovePos);
                }
            }
        }
        return positions;

    }

    public static ArrayList<Integer> getDiagonalMovePos(Piece[] board, int pos) {
        ArrayList<Integer> positions = new ArrayList<>();
        int upRightMovePos = pos, upLeftMovePos = pos, downRightMovePos = pos, downLeftMovePos = pos;
        if (pos > 7) {
            while (upRightMovePos % 8 != 7) {
                upRightMovePos -= 7;
                if (upRightMovePos < 64 && upRightMovePos >= 0) {
                    if (board[upRightMovePos] != null) {
                        if (board[pos].isWhite != board[upRightMovePos].isWhite) {
                            positions.add(upRightMovePos);
                        }
                        break;
                    } else {
                        positions.add(upRightMovePos);
                    }
                } else {
                    break;
                }
            }


            while (upLeftMovePos % 8 != 0) {
                upLeftMovePos -= 9;
                if (upLeftMovePos < 64 && upLeftMovePos >= 0) {
                    if (board[upLeftMovePos] != null) {
                        if (board[pos].isWhite != board[upLeftMovePos].isWhite) {
                            positions.add(upLeftMovePos);
                        }
                        break;
                    } else {
                        positions.add(upLeftMovePos);
                    }
                }
            }
        }

        while (downRightMovePos % 8 != 7) {
            downRightMovePos += 9;
            if (downRightMovePos < 64 && downRightMovePos >= 0) {
                if (board[downRightMovePos] != null) {
                    if (board[pos].isWhite != board[downRightMovePos].isWhite) {
                        positions.add(downRightMovePos);
                    }
                    break;
                } else {
                    positions.add(downRightMovePos);
                }
            }
        }

        while (downLeftMovePos % 8 != 0) {
            downLeftMovePos += 7;
            if (downLeftMovePos < 64 && downLeftMovePos >= 0) {
                if (board[downLeftMovePos] != null) {
                    if (board[pos].isWhite != board[downLeftMovePos].isWhite) {
                        positions.add(downLeftMovePos);
                    }
                    break;
                } else {
                    positions.add(downLeftMovePos);
                }
            }
        }
        return positions;
    }

    public abstract ArrayList<Integer> getMoves(Piece[] board, int pos);

    public boolean isWhite() {
        return isWhite;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Image getImg() {
        return this.img;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public PieceType getType() {
        return this.type;
    }

}