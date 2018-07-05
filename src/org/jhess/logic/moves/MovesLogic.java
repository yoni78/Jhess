package org.jhess.logic.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.pieces.*;
import org.jhess.logic.Board.BoardUtils;

import java.util.Objects;

public final class MovesLogic {

    private MovesLogic() {
    }
    /**
     * Performs the necessary actions for a castling move.
     *
     * @param moveAnalysis The analysis of the move.
     * @param board The game board.
     */
    public static Board castlingMove(Board board, MoveAnalysis moveAnalysis){
        Square rookSquare = moveAnalysis.getRookToCastleSquare();
        Square newRookSquare = BoardUtils.getCastlingRookSquare(board, rookSquare);

        return MoveUtils.movePiece(board, rookSquare, Objects.requireNonNull(newRookSquare));
    }

    /**
     * Performs the necessary actions for an en passant move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    public static Board enPassantMove(Board board, MoveAnalysis moveAnalysis) {
        Board newBoard = new Board(board);
        Square capturePawnSquare = moveAnalysis.getCapturedPawn().getSquare();
        Square newSquare = newBoard.getSquares()[capturePawnSquare.getRank()][capturePawnSquare.getFile()];

        newSquare.setPiece(null);

        return newBoard;
    }

    /**
     * Performs the necessary actions for a promotion move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    public static Board promotionMove(Board board, MoveAnalysis moveAnalysis, Alliance currentPlayer, PieceType pieceType) {
        Piece newPiece = null;

        switch (pieceType){

            case BISHOP:
                newPiece = new Bishop(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case KNIGHT:
                newPiece = new Knight(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case ROOK:
                newPiece = new Rook(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case QUEEN:
                newPiece = new Queen(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
        }

        Board newBoard = new Board(board);
        Square promotionSquare = moveAnalysis.getPromotionSquare();
        Square newSquare = newBoard.getSquares()[promotionSquare.getRank()][promotionSquare.getFile()];

        newSquare.setPiece(newPiece);

        return newBoard;
    }

}
