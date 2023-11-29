package Pieces;

public enum PieceType {
    KING(100),
    PAWN(1),
    BISHOP(3),
    KNIGHT(3),
    ROOK(5),
    QUEEN(9);
    private final int value;

    PieceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
