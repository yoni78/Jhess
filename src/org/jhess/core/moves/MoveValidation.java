package org.jhess.core.moves;

import org.jhess.core.board.Square;


public class MoveValidation {

    private final boolean isValid;

    private final boolean isCastlingMove;
    private final Square rookToCastleSquare;

    private final boolean isPromotionMove;
    private final Square promotionSquare;

    MoveValidation(boolean isValid, boolean isCastlingMove, Square rookToCastleSquare, boolean isPromotionMove, Square promotionSquare) {
        this.isValid = isValid;
        this.isCastlingMove = isCastlingMove;
        this.rookToCastleSquare = rookToCastleSquare;
        this.isPromotionMove = isPromotionMove;
        this.promotionSquare = promotionSquare;
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
}
