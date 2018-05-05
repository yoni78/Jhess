package org.jhess.core.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import static org.jhess.core.moves.MoveVector.*;

public class MoveValidator {
    private MoveValidator() {
    }

    // TODO: 2018-04-29 Promotion
    // TODO: 2018-04-29 Check, Mate

    /**
     * Determines if the given move is a legal one.
     *
     * @param board      The game board.
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static MoveValidation validateMove(Board board, Square srcSquare, Square destSquare,
                                              Piece lastMovedPiece, MoveVector lastMoveVector) {

        MoveValidation validation = new MoveValidationBuilder().createMoveValidation();
        Piece piece = srcSquare.getPiece();
        MoveVector moveVector = new MoveVector(srcSquare, destSquare);

        if (hasNoPieceInHisWay(board, piece, moveVector, srcSquare) && canLandOnSquare(piece, destSquare)) {

            if (piece instanceof King) {
                validation = validateKingMove(board, piece, moveVector, destSquare);

            } else if (piece instanceof Pawn) {
                validation = validatePawnMove(piece, moveVector, destSquare, lastMovedPiece, lastMoveVector);

            } else if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop || piece instanceof Knight) {
                validation = validateRegularPieceMove(piece, moveVector);
            }
        }

        return validation;
    }

    /**
     * Validates pawn moves.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return A validation of the moveVector.
     */
    private static MoveValidation validatePawnMove(Piece pawn, MoveVector moveVector, Square destSquare,
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

        if (isPawnOnLastRank(destSquare, pawn.getAlliance())){
            isPromotionMove = true;
            promotionSquare = destSquare;
        }

        return new MoveValidationBuilder().setIsValid(isValid)
                .setIsEnPassant(isPawnEnPassantMove).setCapturedPawn(capturedPawn)
                .setIsPromotionMove(isPromotionMove).setPromotionSquare(promotionSquare)
                .createMoveValidation();
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
    private static MoveValidation validateRegularPieceMove(Piece piece, MoveVector moveVector) {
        return new MoveValidationBuilder().setIsValid(piece.getMoveList().contains(moveVector)).createMoveValidation();
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
    private static MoveValidation validateKingMove(Board board, Piece king, MoveVector moveVector, Square destSquare) {

        MoveValidationBuilder validationBuilder = new MoveValidationBuilder();
        MoveValidation validation = validationBuilder.setIsValid(king.getMoveList().contains(moveVector)).createMoveValidation();

        if (isCastlingMove(moveVector)) {
            Square rookSquare = getCastlingRookSquare(board, destSquare, moveVector);
            Piece rook = rookSquare.getPiece();

            if (king.isFirstMove() && rook != null && rook.isFirstMove()) {

                king.setFirstMove(false);
                rook.setFirstMove(false);

                validation = validationBuilder.setIsValid(true).setIsCastlingMove(true)
                        .setRookToCastleSquare(rookSquare).createMoveValidation();
            }

        }

        return validation;
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
            tempSquare = board.addMoveToSquare(srcSquare, tempMoveVector);

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
}
