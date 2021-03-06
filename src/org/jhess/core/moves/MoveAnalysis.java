package org.jhess.core.moves;

import org.jhess.core.board.Square;


public class MoveAnalysis {

    private final boolean isLegal;

    private final boolean isCaptureMove;

    private final boolean isCastlingMove;
    private final Square rookToCastleSquare;

    private final boolean isPromotionMove;
    private final Square promotionSquare;

    private final boolean isEnPassant;
    private final Square enPassantSquare;

    private final boolean isCheck;

    public MoveAnalysis(boolean isValid,
                        boolean isCaptureMove, boolean isCastlingMove, Square rookToCastleSquare,
                        boolean isPromotionMove, Square promotionSquare,
                        boolean isEnPassant, Square enPassantSquare,
                        boolean isCheck) {

        this.isLegal = isValid;
        this.isCaptureMove = isCaptureMove;
        this.isCastlingMove = isCastlingMove;
        this.rookToCastleSquare = rookToCastleSquare;
        this.isPromotionMove = isPromotionMove;
        this.promotionSquare = promotionSquare;
        this.isEnPassant = isEnPassant;
        this.enPassantSquare = enPassantSquare;
        this.isCheck = isCheck;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public boolean isCaptureMove() {
        return isCaptureMove;
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

    public Square getEnPassantSquare() {
        return enPassantSquare;
    }

    public boolean isCheck() {
        return isCheck;
    }
}
