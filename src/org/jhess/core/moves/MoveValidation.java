package org.jhess.core.moves;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;


public class MoveValidation {

    private final boolean isValid;

    private final boolean isCastlingMove;
    private final Square rookToCastleSquare;

    private final boolean isPromotionMove;
    private final Square promotionSquare;

    private final boolean isEnPassant;
    private final Piece capturedPawn;

    MoveValidation(boolean isValid,
                   boolean isCastlingMove, Square rookToCastleSquare,
                   boolean isPromotionMove, Square promotionSquare,
                   boolean isEnPassant, Piece capturedPawn) {
        this.isValid = isValid;
        this.isCastlingMove = isCastlingMove;
        this.rookToCastleSquare = rookToCastleSquare;
        this.isPromotionMove = isPromotionMove;
        this.promotionSquare = promotionSquare;
        this.isEnPassant = isEnPassant;
        this.capturedPawn = capturedPawn;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isCastlingMove() {
        return isCastlingMove;
    }

    public Square getRookToCastleSquare() {
        return rookToCastleSquare;
    }

    public boolean isPromotionMove() {
        return isPromotionMove;
    }

    public Square getPromotionSquare() {
        return promotionSquare;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public Piece getCapturedPawn() {
        return capturedPawn;
    }
}
