package org.jhess.logics.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.logics.Board.BoardUtils;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.pieces.Piece;
import org.jhess.core.pieces.Queen;

import java.util.Objects;

import static org.jhess.logics.moves.MoveUtils.movePiece;

public final class MovesLogic {

    private MovesLogic() {
    }
    /**
     * Performs the necessary actions for a castling move.
     *
     * @param moveAnalysis The analysis of the move.
     * @param board The game board.
     */
    public static void castlingMove(Board board, MoveAnalysis moveAnalysis){
        Square rookSquare = moveAnalysis.getRookToCastleSquare();
        Square newRookSquare = BoardUtils.getCastlingRookSquare(board, rookSquare);

        movePiece(rookSquare, Objects.requireNonNull(newRookSquare));
    }

    /**
     * Performs the necessary actions for an en passant move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    public static void enPassantMove(MoveAnalysis moveAnalysis) {
        moveAnalysis.getCapturedPawn().getSquare().setPiece(null);
    }

    /**
     * Performs the necessary actions for a promotion move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    public static void promotionMove(MoveAnalysis moveAnalysis) {
        Alliance pawnToPromoteAlliance = moveAnalysis.getPromotionSquare().getPiece().getAlliance();
        Piece newPiece = new Queen(pawnToPromoteAlliance, moveAnalysis.getPromotionSquare()); // TODO: 2018-05-05 Should be selected by the user

        moveAnalysis.getPromotionSquare().setPiece(newPiece);
    }

}

