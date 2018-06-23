package org.jhess.core.moves;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;


public class MoveAnalysis {

    private final boolean isLegal;

    private final boolean isCastlingMove;
    private final Square rookToCastleSquare; // TODO: 2018-05-05 Change to the actual rook

    private final boolean isPromotionMove;
    private final Square promotionSquare;

    private final boolean isEnPassant;
    private final Piece capturedPawn;

    private final boolean isCheck;

    public MoveAnalysis(boolean isValid,
                 boolean isCastlingMove, Square rookToCastleSquare,
                 boolean isPromotionMove, Square promotionSquare,
                 boolean isEnPassant, Piece capturedPawn,
                 boolean isCheck) {

        this.isLegal = isValid;
        this.isCastlingMove = isCastlingMove;
        this.rookToCastleSquare = rookToCastleSquare;
        this.isPromotionMove = isPromotionMove;
        this.promotionSquare = promotionSquare;
        this.isEnPassant = isEnPassant;
        this.capturedPawn = capturedPawn;
        this.isCheck = isCheck;
    }

    public boolean isLegal() {
        return isLegal;
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

    public boolean isCheck() {
        return isCheck;
    }
}
