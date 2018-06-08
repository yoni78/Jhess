package org.jhess.logics.Board;

import org.jhess.core.board.Board;
import org.jhess.core.board.Square;

public final class BoardUtils {

    private BoardUtils() {
    }

    /**
     * Gets the square to which the rook should move to in a castling move.
     * @param board The game board.
     * @param rookSquare The square the rook is currently on.
     * @return The square to which the rook should move to in a castling move.
     */
    public static Square getCastlingRookSquare(Board board, Square rookSquare){
        if (rookSquare.getFile() == 0){
            return board.getSquares()[rookSquare.getRank()][3];

        } else if (rookSquare.getFile() == 7){
            return board.getSquares()[rookSquare.getRank()][5];

        } else {
            return null;
        }
    }
}
