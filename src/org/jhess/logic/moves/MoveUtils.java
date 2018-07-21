package org.jhess.logic.moves;


import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Piece;
import org.jhess.logic.pieces.PieceUtils;

import static org.jhess.core.moves.MoveVector.*;

// TODO: 2018-07-07 Better define class
public final class MoveUtils {
    private MoveUtils() {
    }

    // TODO: 2018-06-08 Make methods generic

    public static boolean isRookMove(MoveVector moveVector) {
        return PieceUtils.getRookMoves().contains(moveVector);
    }

    public static boolean isBishopMove(MoveVector moveVector) {
        return PieceUtils.getBishopMoves().contains(moveVector);
    }

    public static boolean isKnightMove(MoveVector moveVector) {
        return PieceUtils.getKnightMoves().contains(moveVector);
    }

    public static boolean isPawnCaptureMove(MoveVector moveVector) {
        return (moveVector.equals(FORWARD_RIGHT) || moveVector.equals(FORWARD_LEFT)) ||
                (moveVector.equals(BACKWARD_RIGHT) || moveVector.equals(BACKWARD_LEFT));
    }

    /**
     * Checks if its a pawn double moveVector.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The MoveVector to be played.
     * @return if the moveVector is a pawn double moveVector.
     */
    public static boolean isPawnDoubleMove(Square srcSquare, Piece pawn, MoveVector moveVector) {

        MoveVector possibleMoveVector;
        int startRank;
        if (pawn.getAlliance() == Alliance.WHITE) {
            possibleMoveVector = FORWARD.extend(1);
            startRank = 1;

        } else {
            possibleMoveVector = BACKWARD.extend(1);
            startRank = 6;
        }

        return srcSquare.getRank() == startRank && moveVector.equals(possibleMoveVector);

    }

    /**
     * Check if the given moveVector is a regular pawn move.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is a regular pawn move.
     */
    public static boolean isRegularPawnMove(Piece pawn, MoveVector moveVector, Square destSquare) {

        if (destSquare.isOccupied()) {
            return false;
        }

        if (pawn.getAlliance() == Alliance.WHITE) {
            return moveVector.equals(FORWARD);

        } else {
            return moveVector.equals(BACKWARD);
        }
    }

    /**
     * Checks if the moveVector is a castling moveVector.
     *
     * @param moveVector The moveVector to check.
     * @return If it's a castling moveVector.
     */
    public static boolean isCastlingMove(MoveVector moveVector) {
        return moveVector.equals(LEFT.extend(1)) || moveVector.equals(RIGHT.extend(1));
    }
}
