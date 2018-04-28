package org.jhess.core.moves;

import org.jhess.core.board.Square;

// TODO: 2018-04-28 Make class immutable, and create MoveValidationBuilder class
public class MoveValidation {

    private boolean isValid = false;

    private boolean isCastlingMove = false;
    private Square rookToCastleSquare = null;

    private boolean isPromotionMove = false;
    private Square promotionSquare = null;

    public boolean isValid() {
        return isValid;
    }

    void setValid(boolean valid) {
        isValid = valid;
    }

    boolean isCastlingMove() {
        return isCastlingMove;
    }

    void setCastlingMove(boolean castlingMove) {
        isCastlingMove = castlingMove;
    }

    Square getRookToCastleSquare() {
        return rookToCastleSquare;
    }

    void setRookToCastleSquare(Square rookToCastleSquare) {
        this.rookToCastleSquare = rookToCastleSquare;
    }

    boolean isPromotionMove() {
        return isPromotionMove;
    }

    void setPromotionMove(boolean promotionMove) {
        isPromotionMove = promotionMove;
    }

    Square getPromotionSquare() {
        return promotionSquare;
    }

    void setPromotionSquare(Square promotionSquare) {
        this.promotionSquare = promotionSquare;
    }
}
