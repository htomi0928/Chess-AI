package Interface;

import Pieces.*;

import java.util.ArrayList;

public class GameLogic {
    private final boolean isBlackAI;
    private final Clock whiteClock;
    private final Clock blackClock;
    private boolean whiteToMove;

    public GameLogic(boolean isBlackAi) {
        whiteClock = new Clock(10, 0);
        blackClock = new Clock(10, 0);
        this.isBlackAI = isBlackAi;
        this.whiteToMove = true;
    }

    public void makeMove(Piece[] board, int from, int to) {
        if (board[from].getType() == PieceType.PAWN) {
            if ((to < 8 || to > 55)) {
                board[to] = new Queen(board[from].isWhite());
                board[from] = null;
            } else if (Math.abs(from - to) == 7 || Math.abs(from - to) == 9) {
                if (board[to + 8] != null && board[to + 8].getType() == PieceType.PAWN && ((Pawn) board[to + 8]).hasDoubleMoved() && board[to + 8].isWhite() != board[from].isWhite()) {
                    board[to] = board[from];
                    board[from] = null;
                    board[to + 8] = null;
                } else if (board[to - 8] != null && board[to - 8].getType() == PieceType.PAWN && ((Pawn) board[to - 8]).hasDoubleMoved() && board[to - 8].isWhite() != board[from].isWhite()) {
                    board[to] = board[from];
                    board[from] = null;
                    board[to - 8] = null;
                } else {
                    board[to] = board[from];
                    board[from] = null;
                    board[to].setHasMoved(true);
                }
            } else {
                board[to] = board[from];
                board[from] = null;
                board[to].setHasMoved(true);
            }
            if (board[to].getType() == PieceType.PAWN) {
                ((Pawn) board[to]).setHasDoubleMoved(Math.abs(from - to) == 16);
            }
        } else if (board[from].getType() == PieceType.KING && Math.abs(from - to) == 2) {
            board[to] = board[from];
            board[from] = null;
            board[to].setHasMoved(true);
            if (from > to) {
                board[to + 1] = board[from - 4];
                board[from - 4] = null;
                board[to + 1].setHasMoved(true);
            } else {
                board[to - 1] = board[from + 3];
                board[from + 3] = null;
                board[to - 1].setHasMoved(true);
            }
        } else {
            board[to] = board[from];
            board[from] = null;
            board[to].setHasMoved(true);
        }
    }

    public void unMakeMove(Piece[] board, int from, int to, boolean hasMoved, Piece toPiece) {
        if (board[to].getType() == PieceType.KING && Math.abs(from - to) == 2) {
            board[from] = board[to];
            board[from].setHasMoved(false);
            if (from > to) {
                board[to - 2] = board[to + 1];
                board[to - 2].setHasMoved(false);
                board[to + 1] = null;
            } else {
                board[to + 1] = board[to - 1];
                board[to + 1].setHasMoved(false);
                board[to - 1] = null;
            }
            board[to] = null;
        } else if (board[to].getType() == PieceType.QUEEN && (to < 8 || to > 55) && !board[to].hasMoved()) {
            board[from] = new Pawn(board[to].isWhite());
            board[from].setHasMoved(hasMoved);
            board[to] = toPiece;
        } else if (board[to].getType() == PieceType.PAWN && toPiece == null && (Math.abs(from - to) == 7 || Math.abs(from - to) == 9)) {
            board[from] = board[to];
            board[from].setHasMoved(false);
            board[to] = null;
            if (from > to) {
                board[to + 8] = new Pawn(!board[from].isWhite());
                board[to + 8].setHasMoved(true);
                ((Pawn) board[to + 8]).setHasDoubleMoved(true);
            } else {
                board[to - 8] = new Pawn(!board[from].isWhite());
                board[to - 8].setHasMoved(true);
                ((Pawn) board[to - 8]).setHasDoubleMoved(true);
            }
        } else {
            board[from] = board[to];
            board[from].setHasMoved(hasMoved);
            board[to] = toPiece;
        }
    }

    public int kingPos(Piece[] board, boolean isWhite) {
        for (int i = 0; i < 64; i++) {
            if (board[i] != null && board[i].getType() == PieceType.KING && board[i].isWhite() == isWhite) {
                return i;
            }
        }
        return -1;
    }

    public boolean testMove(Piece[] board, int from, int to) {
        Piece toPiece = board[to];
        boolean hasMoved = board[from].hasMoved();
        makeMove(board, from, to);
        if (isInCheck(board, board[to].isWhite())) {
            unMakeMove(board, from, to, hasMoved, toPiece);
            return false;
        }
        unMakeMove(board, from, to, hasMoved, toPiece);
        return true;
    }

    public boolean isInCheck(Piece[] board, boolean isWhite) {
        int kingPos = kingPos(board, isWhite);
        for (int i = 0; i < 64; i++) {
            if (board[i] != null && board[i].isWhite() != isWhite && board[i].getMoves(board, i).contains(kingPos)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMate(Piece[] board, boolean isWhite) {
        if (isInCheck(board, isWhite)) {
            for (int i = 0; i < 64; i++) {
                if (board[i] != null && board[i].isWhite() == isWhite) {
                    Piece piece = board[i];
                    ArrayList<Integer> possibleMoves = piece.getMoves(board, i);
                    for (Integer possibleMove : possibleMoves) {
                        if (testMove(board, i, possibleMove)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean hasValidMove(Piece[] board, int pos) {
        ArrayList<Integer> moves = board[pos].getMoves(board, pos);
        for (Integer move : moves) {
            if (testMove(board, pos, move)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStaleMate(Piece[] board, boolean isWhite) {
        if (!isInCheck(board, isWhite)) {
            for (int i = 0; i < 64; i++) {
                if (board[i] != null && board[i].isWhite() == isWhite && hasValidMove(board, i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int evaluate(Piece[] board) {
        int sum = 0;
        for (int i = 0; i < 64; i++) {
            if (board[i] != null) {
                if (board[i].isWhite()) {
                    sum += board[i].getType().getValue();
                } else {
                    sum -= board[i].getType().getValue();
                }
            }
        }
        return sum;
    }

    public boolean gameOver(Piece[] board, boolean isWhite) {
        return checkMate(board, isWhite) || isStaleMate(board, isWhite);
    }

    public ArrayList<int[]> getPossibleMoves(Piece[] board, boolean isWhite) {
        ArrayList<int[]> ret = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (board[i] != null && board[i].isWhite() == isWhite && hasValidMove(board, i)) {
                ArrayList<Integer> possibleMoves = board[i].getMoves(board, i);
                for (Integer move : possibleMoves) {
                    if (testMove(board, i, move)) {
                        ret.add(new int[]{i, move});
                    }
                }
            }
        }
        return ret;
    }

    public Piece[] copyBoard(Piece[] board) {
        Piece[] ret = new Piece[64];
        for (int i = 0; i < 64; i++) {
            if (board[i] != null) {
                Piece p = null;
                if (board[i] instanceof Pawn) p = new Pawn(board[i].isWhite());
                else if (board[i] instanceof Bishop) p = new Bishop(board[i].isWhite());
                else if (board[i] instanceof King) p = new King(board[i].isWhite());
                else if (board[i] instanceof Knight) p = new Knight(board[i].isWhite());
                else if (board[i] instanceof Queen) p = new Queen(board[i].isWhite());
                else if (board[i] instanceof Rook) p = new Rook(board[i].isWhite());
                if (p != null) {
                    p.setIsWhite(board[i].isWhite());
                }
                ret[i] = p;
            }
        }
        return ret;
    }

    public int[] minimax(Piece[] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || checkMate(board, !maximizingPlayer)) {
            return new int[]{-1, -1, evaluate(board)};
        }

        int[] bestMove;
        if (maximizingPlayer) {
            bestMove = new int[]{-1, -1, Integer.MIN_VALUE};
            for (int[] move : getPossibleMoves(board, true)) {
                Piece[] newBoard = copyBoard(board);
                makeMove(newBoard, move[0], move[1]);
                int[] score = minimax(newBoard, depth - 1, alpha, beta, false);
                if (score[2] > bestMove[2]) {
                    bestMove = new int[]{move[0], move[1], score[2]};
                }
                alpha = Math.max(alpha, score[2]);
                if (beta <= alpha) {
                    break;
                }
            }
        } else {
            bestMove = new int[]{-1, -1, Integer.MAX_VALUE};
            for (int[] move : getPossibleMoves(board, false)) {
                Piece[] newBoard = copyBoard(board);
                makeMove(newBoard, move[0], move[1]);
                int[] score = minimax(newBoard, depth - 1, alpha, beta, true);
                if (score[2] < bestMove[2]) {
                    bestMove = new int[]{move[0], move[1], score[2]};
                }
                beta = Math.min(beta, score[2]);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return bestMove;
    }

    public void makeAiMove(Piece[] board) {
        if (!gameOver(board, false)) {
            int depth = 5;
            int[] bestMove = minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (bestMove[0] != -1 && bestMove[1] != -1) {
                makeMove(board, bestMove[0], bestMove[1]);
                makeTurn();
                Board.setFromMoveSquare(bestMove[0]);
                Board.setToMoveSquare(bestMove[1]);
            } else {
                int[] move = getPossibleMoves(board, false).get(0);
                makeMove(board, move[0], move[1]);
                makeTurn();
            }
        }
    }

    public void restartGame() {
        this.whiteToMove = true;
        this.whiteClock.restart();
        this.blackClock.restart();
    }

    public void makeTurn() {
        this.whiteToMove = !this.whiteToMove;
    }

    public boolean isBlackAI() {
        return this.isBlackAI;
    }

    public boolean whiteToMove() {
        return this.whiteToMove;
    }

    public Clock getWhiteClock() {
        return this.whiteClock;
    }

    public Clock getBlackClock() {
        return this.blackClock;
    }

}
