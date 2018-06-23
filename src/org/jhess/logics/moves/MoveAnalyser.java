package org.jhess.logics.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.*;
import org.jhess.core.moves.MoveAnalysisBuilder;
import org.jhess.logics.Board.BoardUtils;
import org.jhess.logics.pieces.PieceUtils;

import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class MoveAnalyser {
    private MoveAnalyser() {
    }

    // TODO: 2018-04-29 Check, Mate

    /**
     * Determines if the given move is a legal one.
     *
     * @param board      The game board.
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static MoveAnalysis analyseMove(Board board, Square srcSquare, Square destSquare,
                                           Piece lastMovedPiece, MoveVector lastMoveVector) {

        MoveAnalysisBuilder validationBuilder = new MoveAnalysisBuilder();
        Piece piece = srcSquare.getPiece();
        MoveVector moveVector = new MoveVector(srcSquare, destSquare);

        if (hasNoPieceInHisWay(board, piece, moveVector, srcSquare) && canLandOnSquare(piece, destSquare)) {

            if (piece instanceof King) {
                validationBuilder = validateKingMove(board, piece, moveVector, destSquare);

            } else if (piece instanceof Pawn) {
                validationBuilder = validatePawnMove(piece, moveVector, destSquare, lastMovedPiece, lastMoveVector);

            } else if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop || piece instanceof Knight) {
                validationBuilder = validateRegularPieceMove(piece, moveVector);
            }
        }

        // TODO: 2018-05-11 Player should get out of check
        // TODO: 2018-06-16 Check for check at the beginning of the turn, before the player makes a move.
        if (isCheck(board, piece.getAlliance())) {
            validationBuilder.setIsCheck(true);

            Board newBoard = MoveUtils.movePiece(board, srcSquare, destSquare);

            if (isCheck(newBoard, piece.getAlliance())){
                validationBuilder.setIsLegal(false);
            }
        }

        return validationBuilder.createMoveValidation();
    }

    /**
     * Validates pawn moves.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return A validation of the moveVector.
     */
    private static MoveAnalysisBuilder validatePawnMove(Piece pawn, MoveVector moveVector, Square destSquare,
                                                        Piece lastMovedPiece, MoveVector lastMoveVector) {

        boolean isValid = false;

        boolean isPawnEnPassantMove = false;
        Pawn capturedPawn = null;

        boolean isPromotionMove = false;
        Square promotionSquare = null;

        if (isPawnCaptureMove(pawn, moveVector, destSquare, false)) {
            isValid = true;

        } else if (isPawnDoubleMove(pawn, moveVector, false)) {
            isValid = true;

        } else if (isEnPassantMove(pawn, moveVector, destSquare, lastMovedPiece, lastMoveVector)) {
            isValid = true;
            isPawnEnPassantMove = true;
            capturedPawn = (Pawn) lastMovedPiece;

        } else if (isRegularPawnMove(pawn, moveVector, destSquare)) {
            isValid = true;
            pawn.setFirstMove(false);
        }

        if (isPawnOnLastRank(destSquare, pawn.getAlliance())) {
            isPromotionMove = true;
            promotionSquare = destSquare;
        }

        return new MoveAnalysisBuilder().setIsLegal(isValid)
                .setIsEnPassant(isPawnEnPassantMove).setCapturedPawn(capturedPawn)
                .setIsPromotionMove(isPromotionMove).setPromotionSquare(promotionSquare);
    }

    /**
     * Check if the given moveVector is a regular pawn move.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is a regular pawn move.
     */
    private static boolean isRegularPawnMove(Piece pawn, MoveVector moveVector, Square destSquare) {

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
     * Check if the given moveVector is an En Passant move.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is an En Passant.
     */
    private static boolean isEnPassantMove(Piece pawn, MoveVector moveVector, Square destSquare, Piece lastMovedPiece, MoveVector lastMoveVector) {

        return lastMoveVector != null &&
                lastMovedPiece instanceof Pawn && isPawnDoubleMove(lastMovedPiece, lastMoveVector, true)
                && isPawnCaptureMove(pawn, moveVector, destSquare, true);

    }

    /**
     * Check if the given moveVector is a pawn's capture moveVector.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is a pawn capture moveVector.
     */
    private static boolean isPawnCaptureMove(Piece pawn, MoveVector moveVector, Square destSquare, boolean checkEnPassant) {

        if (checkEnPassant || destSquare.isOccupied()) {

            if (pawn.getAlliance() == Alliance.WHITE && (moveVector.equals(FORWARD_RIGHT) || moveVector.equals(FORWARD_LEFT))) {
                return true;
            }

            return pawn.getAlliance() == Alliance.BLACK && (moveVector.equals(BACKWARD_RIGHT) || moveVector.equals(BACKWARD_LEFT));
        }

        return false;
    }

    /**
     * Checks if its a pawn double moveVector.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The MoveVector to be played.
     * @return if the moveVector is a pawn double moveVector.
     */
    private static boolean isPawnDoubleMove(Piece pawn, MoveVector moveVector, boolean forEnPassant) {

        MoveVector possibleMoveVector;
        if (pawn.getAlliance() == Alliance.WHITE) {
            possibleMoveVector = FORWARD.extend(1);

        } else {
            possibleMoveVector = BACKWARD.extend(1);
        }

        if (forEnPassant || pawn.isFirstMove() && moveVector.equals(possibleMoveVector)) {
            pawn.setFirstMove(false); // TODO: 2018-05-04 Not here?
            return true;
        }

        return false;
    }

    /**
     * Checks if the pawn got to the last rank (and should subsequently be promoted)
     *
     * @param destSquare   The destination square of the move.
     * @param pawnAlliance The alliance of the pawn.
     * @return If the pawn got to the last rank.
     */
    private static boolean isPawnOnLastRank(Square destSquare, Alliance pawnAlliance) {
        return (pawnAlliance == Alliance.WHITE && destSquare.getRank() == 7) ||
                (pawnAlliance == Alliance.BLACK && destSquare.getRank() == 0);
    }

    /**
     * Validates regular pieces moves (queens, rooks, bishops and knights)
     *
     * @param piece      The piece to be played.
     * @param moveVector The moveVector to be played
     * @return A validation of the moveVector.
     */
    private static MoveAnalysisBuilder validateRegularPieceMove(Piece piece, MoveVector moveVector) {
        return new MoveAnalysisBuilder().setIsLegal(piece.getMoveList().contains(moveVector));
    }

    /**
     * Validates king moves.
     *
     * @param board      The game board.
     * @param king       The king to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return A validation of the moveVector.
     */
    private static MoveAnalysisBuilder validateKingMove(Board board, Piece king, MoveVector moveVector, Square destSquare) {

        MoveAnalysisBuilder validationBuilder = new MoveAnalysisBuilder();
        validationBuilder.setIsLegal(king.getMoveList().contains(moveVector));

        if (isCastlingMove(moveVector)) {
            Square rookSquare = getCastlingRookSquare(board, destSquare, moveVector);
            Piece rook = rookSquare.getPiece();

            if (king.isFirstMove() && rook != null && rook.isFirstMove()) {

                king.setFirstMove(false);
                rook.setFirstMove(false);

                validationBuilder.setIsLegal(true).setIsCastlingMove(true).setRookToCastleSquare(rookSquare);
            }
        }

        return validationBuilder;
    }

    /**
     * Gets the rook that should participate in the castling.
     *
     * @param board      The game board.
     * @param destSquare The destination square of the moveVector.
     * @param moveVector The moveVector to be played.
     * @return The rook that should participate in the castling.
     */
    private static Square getCastlingRookSquare(Board board, Square destSquare, MoveVector moveVector) {

        Square rookSquare = null;

        // Short
        if (moveVector.equals(RIGHT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() + 1];

            // Long
        } else if (moveVector.equals(LEFT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() - 2];
        }

        return rookSquare;
    }

    /**
     * Checks if the moveVector is a castling moveVector.
     *
     * @param moveVector The moveVector to check.
     * @return If it's a castling moveVector.
     */
    private static boolean isCastlingMove(MoveVector moveVector) {
        return moveVector.equals(LEFT.extend(1)) || moveVector.equals(RIGHT.extend(1));
    }

    /**
     * Checks if the piece attempts to jump over another piece in it's moveVector.
     *
     * @param board      The game board;
     * @param piece      The piece to be played.
     * @param moveVector The MoveVector to be played.
     * @param srcSquare  The source square of the moveVector.
     * @return If the path for the moveVector is clear.
     */
    private static boolean hasNoPieceInHisWay(Board board, Piece piece, MoveVector moveVector, Square srcSquare) {

        // If the piece is skipping over another piece
        if (piece instanceof Knight) {
            return true;
        }

        int distance = Math.max(Math.abs(moveVector.getRankToAdvance()), Math.abs(moveVector.getFileToAdvance()));

        MoveVector tempMoveVector;
        Square tempSquare;
        for (int i = 1; i < distance; i++) {
            tempMoveVector = moveVector.extend(-i);
            tempSquare = BoardUtils.addMoveToSquare(board, srcSquare, tempMoveVector);

            if (tempSquare == null) {
                return true;
            }

            if (tempSquare.isOccupied()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks that the destination square isn't occupied, and if so then if it's occupied by an enemy piece.
     *
     * @param piece      The piece to be played.
     * @param destSquare The destination square of the move.
     * @return If the piece can land on the square.
     */
    private static boolean canLandOnSquare(Piece piece, Square destSquare) {
        return !destSquare.isOccupied() || destSquare.getPiece().getAlliance() != piece.getAlliance();
    }

    private static boolean isCheck(Board board, Alliance playerAlliance) {

        King king = BoardUtils.getKing(board, playerAlliance);

        List<MoveVector> possibleMoves = PieceUtils.getRookMoves();
        possibleMoves.addAll(PieceUtils.getBishopMoves());
        possibleMoves.addAll(PieceUtils.getKnightMoves());

        for (MoveVector possibleMove : possibleMoves) {
            Square destSquare = BoardUtils.addMoveToSquare(board, king.getSquare(), possibleMove);

            if (destSquare == null || destSquare.getPiece() == null || destSquare.getPiece().getAlliance() == playerAlliance) {
                continue;
            }

            Piece otherPiece = destSquare.getPiece();
            if (otherPiece == null) {
                continue;
            }

            boolean isRookOrQueenCheck = (otherPiece instanceof Rook || otherPiece instanceof Queen) && MoveUtils.isRookMove(possibleMove);
            boolean isBishopOrQueenCheck = (otherPiece instanceof Bishop || otherPiece instanceof Queen) && MoveUtils.isBishopMove(possibleMove);
            boolean isKnightCheck = otherPiece instanceof Knight && MoveUtils.isKnightMove(possibleMove);
            boolean isPawnCheck = otherPiece instanceof Pawn && MoveUtils.isPawnMove(possibleMove);

            if (hasNoPieceInHisWay(board, king, possibleMove, king.getSquare()) &&
                    (isRookOrQueenCheck || isBishopOrQueenCheck || isKnightCheck || isPawnCheck)) {
                return true;
            }
        }

        return false;
    }
}
