package org.jhess.core.moves;


import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

public final class MoveUtils {
    private MoveUtils() {
    }

    /**
     * Moves a piece from the source square to the destination square.
     * @param srcSquare The source square.
     * @param destSquare The destination square.
     */
    public static void movePiece(Square srcSquare, Square destSquare){
        Piece pieceToMove = srcSquare.getPiece();

        destSquare.setPiece(pieceToMove);
        srcSquare.setPiece(null);

        pieceToMove.setSquare(destSquare);
    }
}
