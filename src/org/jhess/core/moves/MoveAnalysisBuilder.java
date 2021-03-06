package org.jhess.core.moves;

import org.jhess.core.board.Square;

public class MoveAnalysisBuilder {

    private boolean isLegal = false;
    private boolean isCaptureMove = false;
    private boolean isCastlingMove = false;
    private Square rookToCastleSquare = null;
    private boolean isPromotionMove = false;
    private Square promotionSquare = null;
    private boolean isEnPassant;
    private Square enPassantSquare;
    private boolean isCheck;

    public MoveAnalysisBuilder setIsLegal(boolean isLegal) {
        this.isLegal = isLegal;
        return this;
    }

    public MoveAnalysisBuilder setCaptureMove(boolean captureMove) {
        this.isCaptureMove = captureMove;
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

    public MoveAnalysisBuilder setEnPassantSquare(Square enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
        return this;
    }

    public MoveAnalysisBuilder setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        return this;
    }

    public MoveAnalysis createMoveValidation() {
        return new MoveAnalysis(isLegal, isCaptureMove, isCastlingMove, rookToCastleSquare, isPromotionMove, promotionSquare, isEnPassant, enPassantSquare, isCheck);
    }
}