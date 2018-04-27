package org.jhess.core.moves;

import org.jhess.core.board.Square;

public class MoveValidation {

    private final boolean isValid;
    private final boolean isCastlingMove;
    private final Square rookToCastleSquare;

    MoveValidation(boolean isValid, boolean isCastlingMove, Square rookToCastleSquare) {
        this.isValid = isValid;
        this.isCastlingMove = isCastlingMove;
        this.rookToCastleSquare = rookToCastleSquare;
    }

    MoveValidation(boolean isValid) {
        this(isValid, false, null);
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
}
