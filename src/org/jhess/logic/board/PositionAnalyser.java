package org.jhess.logic.board;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Piece;
import org.jhess.logic.moves.MoveAnalyser;
import org.jhess.logic.moves.MovePerformer;
import org.jhess.logic.moves.MoveUtils;
import org.jhess.logic.pieces.PieceUtils;

import java.util.List;

public class PositionAnalyser {

    private final Board position;

    public PositionAnalyser(Board position) {
        this.position = position;
    }

    /**
     * Checks if the current player is in check.
     *
     * @return Whether the player is in check or not.
     */
    public boolean isCheck() {
        return isCheck(position.getPlayerToMove());
    }

    /**
     * Checks if the given player is in check regardless if it's his move or not.
     * @param playerToCheck The player to check.
     * @return Whether the player is in check or not.
     */
    public boolean isCheck(Alliance playerToCheck) {

        MoveAnalyser moveAnalyser = new MoveAnalyser(position);
        Square kingSquare = BoardUtils.getKing(position, playerToCheck);

        List<MoveVector> possibleMoves = PieceUtils.getRookMoves();
        possibleMoves.addAll(PieceUtils.getBishopMoves());
        possibleMoves.addAll(PieceUtils.getKnightMoves());

        for (MoveVector possibleMove : possibleMoves) {
            Square destSquare = BoardUtils.addMoveToSquare(position, kingSquare, possibleMove);

            if (destSquare == null || destSquare.getPiece() == null || destSquare.getPiece().getAlliance() == playerToCheck) {
                continue;
            }

            Piece otherPiece = destSquare.getPiece();

            boolean isRookOrQueenCheck = (PieceUtils.isRook(otherPiece) || PieceUtils.isQueen(otherPiece)) && MoveUtils.isRookMove(possibleMove);
            boolean isBishopOrQueenCheck = (PieceUtils.isBishop(otherPiece) || PieceUtils.isQueen(otherPiece)) && MoveUtils.isBishopMove(possibleMove);
            boolean isKnightCheck = PieceUtils.isKnight(otherPiece) && MoveUtils.isKnightMove(possibleMove);
            boolean isPawnCheck = PieceUtils.isPawn(otherPiece) && MoveUtils.isPawnCaptureMove(possibleMove);

            if (moveAnalyser.hasNoPieceInHisWay(otherPiece, possibleMove.reverse(), destSquare) &&
                    (isRookOrQueenCheck || isBishopOrQueenCheck || isKnightCheck || isPawnCheck)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the given player is mated.
     *
     * @return Whether the player is mated or not.
     */
    public boolean isMate() {

        if (!isCheck()) {
            return false;
        }

        MoveAnalyser moveAnalyser = new MoveAnalyser(position);

        // Get all the pieces of the player
        List<Square> pieceSquares = BoardUtils.getPieces(position, position.getPlayerToMove());

        // If moving any of them leads to a position without check, then it's not mate
        for (Square square: pieceSquares) {
            Piece piece = square.getPiece();

            for (MoveVector moveVector : piece.getMoveList()) {

                Square destSquare = BoardUtils.addMoveToSquare(position, square, moveVector);
                if (destSquare == null || (destSquare.isOccupied() && destSquare.getPiece().getAlliance() == piece.getAlliance())) {
                    continue;
                }

                MoveAnalysis moveAnalysis = moveAnalyser.analyseMove(square, destSquare);
                if (!moveAnalysis.isLegal()) {
                    continue;
                }

                Board newPosition = new MovePerformer(position).makeMove(square, destSquare);

                if (!new PositionAnalyser(newPosition).isCheck()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the given board position is a stalemate.
     *
     * @return Whether the position is a stalemate or not.
     */
    public boolean isStaleMate() {
        if (isCheck()) {
            return false;
        }

        MoveAnalyser moveAnalyser = new MoveAnalyser(position);

        // Get all the pieces of the player
        List<Square> pieceSquares = BoardUtils.getPieces(position, position.getPlayerToMove());

        // If there is a legal move for the player, the it's not stalemate
        for (Square square : pieceSquares) {
            Piece piece = square.getPiece();

            for (MoveVector moveVector : piece.getMoveList()) {
                Square destSquare = BoardUtils.addMoveToSquare(position, square, moveVector);
                if (destSquare == null) {
                    continue;
                }

                MoveAnalysis moveAnalysis = moveAnalyser.analyseMove(square, destSquare);
                if (moveAnalysis.isLegal()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the current is position is a draw by the fify move rule.
     * @return If the position is a draw.
     */
    public boolean isFiftyMoveDraw(){
        return position.getHalfMoveClock() >= 100;
    }
}
