package org.jhess.logic.moves;


import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Piece;
import org.jhess.logic.pieces.PieceUtils;

import static org.jhess.core.moves.MoveVector.*;

public final class MoveUtils {
    private MoveUtils() {
    }


    // TODO: 2018-06-09 Should return a new board

    /**
     * Moves a piece from the source square to the destination square.
     *
     * @param srcSquare  The source square.
     * @param destSquare The destination square.
     */
    public static Board movePiece(Board board, Square srcSquare, Square destSquare) {
        Board newBoard = new Board(board);

        Square newSrc = newBoard.getSquares()[srcSquare.getRank()][srcSquare.getFile()];
        Square newDest = newBoard.getSquares()[destSquare.getRank()][destSquare.getFile()];

        Piece pieceToMove = newSrc.getPiece();

        newDest.setPiece(pieceToMove);
        newSrc.setPiece(null);

        pieceToMove.setSquare(newDest); // TODO: 2018-06-09 Create a new piece

        return newBoard;
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

    public static boolean isPawnMove(MoveVector moveVector) {
        return PieceUtils.getPawnMoves().contains(moveVector);
    }

    public static boolean isPawnCaptureMove(MoveVector moveVector) {
        return (moveVector.equals(FORWARD_RIGHT) || moveVector.equals(FORWARD_LEFT)) ||
                (moveVector.equals(BACKWARD_RIGHT) || moveVector.equals(BACKWARD_LEFT));
    }
}
