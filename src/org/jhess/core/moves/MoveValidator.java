package org.jhess.core.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import static org.jhess.core.moves.Move.*;

public class MoveValidator {
    private MoveValidator() {
    }

    // TODO: 2018-04-29 En-Passant
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
    public static MoveValidation validateMove(Board board, Square srcSquare, Square destSquare) {

        MoveValidation validation = new MoveValidation();
        Piece piece = srcSquare.getPiece();
        Move move = new Move(srcSquare, destSquare);

        if (hasNoPieceInHisWay(board, piece, move, srcSquare) && canLandOnSquare(piece, destSquare)) {

            if (piece instanceof King) {
                validation = validateKingMove(board, piece, move, destSquare);

            } else if (piece instanceof Pawn) {
                validation = validatePawnMove(piece, move, destSquare);

            } else if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop || piece instanceof Knight) {
                validation = validateRegularPieceMove(piece, move);
            }
        }

        return validation;
    }

    /**
     * Validates pawn moves.
     *
     * @param pawn       The pawn to be played.
     * @param move       The move to be played.
     * @param destSquare The destination square of the move.
     * @return A validation of the move.
     */
    private static MoveValidation validatePawnMove(Piece pawn, Move move, Square destSquare) {

        MoveValidation validation = new MoveValidation();

        // Reverse the move for black pawns
//        if (pawn.getAlliance() == Alliance.BLACK){
//            move = new Move(-move.getRankToAdvance(), move.getFileToAdvance());
//        }

        if (isSpecialPawnMove(pawn, move, destSquare)) {
            validation.setValid(true);

        } else if (!destSquare.isOccupied()) {
            validation.setValid(true);
        }

        return validation;
    }

    /**
     * Validates regular pieces moves (queens, rooks, bishops and knights)
     *
     * @param piece The piece to be played.
     * @param move  The move to be played
     * @return A validation of the move.
     */
    private static MoveValidation validateRegularPieceMove(Piece piece, Move move) {
        MoveValidation validation = new MoveValidation();
        validation.setValid(piece.getMoveList().contains(move));

        return validation;
    }

    /**
     * Validates king moves.
     *
     * @param board      The game board.
     * @param king       The king to be played.
     * @param move       The move to be played.
     * @param destSquare The destination square of the move.
     * @return A validation of the move.
     */
    private static MoveValidation validateKingMove(Board board, Piece king, Move move, Square destSquare) {

        MoveValidation validation = new MoveValidation();

        if (isCastlingMove(move)) {
            Square rookSquare = getCastlingRookSquare(board, destSquare, move);
            Piece rook = rookSquare.getPiece();

            if (king.isFirstMove() && rook != null && rook.isFirstMove()) {

                king.setFirstMove(false);
                rook.setFirstMove(false);

                validation.setValid(true);
                validation.setCastlingMove(true);
                validation.setRookToCastleSquare(rookSquare);
            }

        } else {
            validation.setValid(king.getMoveList().contains(move));
        }

        return validation;
    }

    /**
     * Checks special cases for pawn like captures or double moves at the start.
     *
     * @param piece      The piece to be played.
     * @param move       The Move to be played.
     * @param destSquare The destination square of the move.
     * @return If there was a valid pawn capture or double move at the start.
     */
    private static boolean isSpecialPawnMove(Piece piece, Move move, Square destSquare) {

        // TODO: Check for En-Passant

        // If it's a capture move
        if (destSquare.isOccupied()) {

            if (piece.getAlliance() == Alliance.WHITE && (move.equals(FORWARD_RIGHT) || move.equals(FORWARD_LEFT))) {
                return true;
            }

            if (piece.getAlliance() == Alliance.BLACK && (move.equals(BACKWARD_RIGHT) || move.equals(BACKWARD_LEFT))) {
                return true;
            }
        }

        // If it's the pawn's first move
        Move possibleMove;
        if (piece.getAlliance() == Alliance.WHITE) {
            possibleMove = FORWARD.extend(1);

        } else {
            possibleMove = BACKWARD.extend(1);
        }

        if (piece.isFirstMove() && move.equals(possibleMove)) {
            piece.setFirstMove(false);
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
     * Gets the rook that should participate in the castling.
     *
     * @param board      The game board.
     * @param destSquare The destination square of the move.
     * @param move       The move to be played.
     * @return The rook that should participate in the castling.
     */
    private static Square getCastlingRookSquare(Board board, Square destSquare, Move move) {

        Square rookSquare = null;

        // Short
        if (move.equals(RIGHT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() + 1];

            // Long
        } else if (move.equals(LEFT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() - 2];
        }

        return rookSquare;
    }

    /**
     * Checks if the move is a castling move.
     *
     * @param move The move to check.
     * @return If it's a castling move.
     */
    private static boolean isCastlingMove(Move move) {
        return move.equals(LEFT.extend(1)) || move.equals(RIGHT.extend(1));
    }

    /**
     * Checks if the piece attempts to jump over another piece in it's move.
     *
     * @param board     The game board;
     * @param piece     The piece to be played.
     * @param move      The Move to be played.
     * @param srcSquare The source square of the move.
     * @return If the path for the move is clear.
     */
    private static boolean hasNoPieceInHisWay(Board board, Piece piece, Move move, Square srcSquare) {

        // If the piece is skipping over another piece
        if (piece instanceof Knight) {
            return true;
        }

        int distance = Math.max(Math.abs(move.getRankToAdvance()), Math.abs(move.getFileToAdvance()));

        Move tempMove;
        Square tempSquare;
        for (int i = 1; i < distance; i++) {
            tempMove = move.extend(-i);
            tempSquare = board.addMoveToSquare(srcSquare, tempMove);

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
