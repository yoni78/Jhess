package org.jhess.core.moves;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

public class MoveValidationBuilder {

    private boolean isValid = false;
    private boolean isCastlingMove = false;
    private Square rookToCastleSquare = null;
    private boolean isPromotionMove = false;
    private Square promotionSquare = null;
    private boolean isEnPassant;
    private Piece capturedPawn;

    public MoveValidationBuilder setIsValid(boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public MoveValidationBuilder setIsCastlingMove(boolean isCastlingMove) {
        this.isCastlingMove = isCastlingMove;
        return this;
    }

    public MoveValidationBuilder setRookToCastleSquare(Square rookToCastleSquare) {
        this.rookToCastleSquare = rookToCastleSquare;
        return this;
    }

    public MoveValidationBuilder setIsPromotionMove(boolean isPromotionMove) {
        this.isPromotionMove = isPromotionMove;
        return this;
    }

    public MoveValidationBuilder setPromotionSquare(Square promotionSquare) {
        this.promotionSquare = promotionSquare;
        return this;
    }

    public MoveValidationBuilder setIsEnPassant(boolean isEnPassant) {
        this.isEnPassant = isEnPassant;
        return this;
    }

    public MoveValidationBuilder setCapturedPawn(Piece capturedPawn) {
        this.capturedPawn = capturedPawn;
        return this;
    }

    MoveValidation createMoveValidation() {
        return new MoveValidation(isValid, isCastlingMove, rookToCastleSquare, isPromotionMove, promotionSquare, isEnPassant, capturedPawn);
    }
}