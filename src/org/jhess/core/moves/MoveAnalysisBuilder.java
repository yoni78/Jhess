package org.jhess.core.moves;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

public class MoveAnalysisBuilder {

    private boolean isValid = false;
    private boolean isCastlingMove = false;
    private Square rookToCastleSquare = null;
    private boolean isPromotionMove = false;
    private Square promotionSquare = null;
    private boolean isEnPassant;
    private Piece capturedPawn;
    private boolean isCheck;

    public MoveAnalysisBuilder setIsValid(boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public MoveAnalysisBuilder setIsCastlingMove(boolean isCastlingMove) {
        this.isCastlingMove = isCastlingMove;
        return this;
    }

    public MoveAnalysisBuilder setRookToCastleSquare(Square rookToCastleSquare) {
        this.rookToCastleSquare = rookToCastleSquare;
        return this;
    }

    public MoveAnalysisBuilder setIsPromotionMove(boolean isPromotionMove) {
        this.isPromotionMove = isPromotionMove;
        return this;
    }

    public MoveAnalysisBuilder setPromotionSquare(Square promotionSquare) {
        this.promotionSquare = promotionSquare;
        return this;
    }

    public MoveAnalysisBuilder setIsEnPassant(boolean isEnPassant) {
        this.isEnPassant = isEnPassant;
        return this;
    }

    public MoveAnalysisBuilder setCapturedPawn(Piece capturedPawn) {
        this.capturedPawn = capturedPawn;
        return this;
    }

    public MoveAnalysisBuilder setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        return this;
    }

    MoveAnalysis createMoveValidation() {
        return new MoveAnalysis(isValid, isCastlingMove, rookToCastleSquare, isPromotionMove, promotionSquare, isEnPassant, capturedPawn, isCheck);
    }
}